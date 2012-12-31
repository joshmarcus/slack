package slack.examples

import slack.SimpleGame

import org.newdawn.slick.Animation
import org.newdawn.slick.AppGameContainer
import org.newdawn.slick.BasicGame
import org.newdawn.slick.GameContainer
import org.newdawn.slick.Graphics
import org.newdawn.slick.Input
import org.newdawn.slick.SlickException
import org.newdawn.slick.SpriteSheet
import org.newdawn.slick.tiled.TiledMap
import org.newdawn.slick.util.Log

/**
 * An example to show scrolling around a tilemap smoothly. 
 *
 * Currently a literal Scala port of kevin's Slick2D example.
 *
 * Next todo: encapsulate mutable state. 
 */
class Scroller extends SimpleGame("Scroller") {
	/** The size of the tank sprite - used for finding the centre */
	val TankSize = 32
	/** The size of the tiles - used to determine the amount to draw */
	val TileSize = 32
	/** The speed the tank moves at */
	val TankMoveSpeed:Float = 0.003f
	/** The speed the tank rotates at */
	val TankRotateSpeed:Float = 0.2f
	
	/** The player's x position in tiles */
	var playerX:Float = 15
	/** The player's y position in tiles */
	var playerY:Float = 16
	
	/** The width of the display in tiles */
	var widthInTiles = 0 
	/** The height of the display in tiles */
	var heightInTiles = 0
	
	/** The offset from the centre of the screen to the top edge in tiles */
	var topOffsetInTiles = 0
	/** The offset from the centre of the screen to the left edge in tiles */
	var leftOffsetInTiles = 0
	
	/** The map that we're going to drive around */
	var map:TiledMap = null
	
	/** The animation representing the player's tank */
	var player:Animation = null
	
	/** The angle the player is facing */
	var ang:Float = 0
	/** The x component of the movement vector */
	var dirX:Float = 0
	/** The y component of themovement vector */
	var dirY:Float = 0
	
	/** The collision map indicating which tiles block movement - generated based on tile properties */
	var blockedTiles:Array[Array[Boolean]] = null 
	
	def init(container:GameContainer) {
		// load the sprites and tiles, note that underneath the texture
		// will be shared between the sprite sheet and tilemap
		val sheet = new SpriteSheet("src/main/resources/scroller/sprites.png",32,32)

		// load the tilemap created the TileD tool 
		map = new TiledMap("src/main/resources/scroller/map.tmx")
		
		// build a collision map based on tile properties in the TileD map
		blockedTiles = Array.ofDim[Boolean](map.getWidth(),map.getHeight());
		for (x <- 0 until map.getWidth();
         y <- 0 until map.getHeight()) {
      val tileID = map.getTileId(x, y, 0);
      val value = map.getTileProperty(tileID, "blocked", "false")
      if (value == "true") {
        blockedTiles(x)(y) = true
      }
	  }
		
		// caculate some layout values for rendering the tilemap. How many tiles
		// do we need to render to fill the screen in each dimension and how far is
		// it from the centre of the screen
		widthInTiles = container.getWidth() / TileSize
		heightInTiles = container.getHeight() / TileSize
		topOffsetInTiles = heightInTiles / 2
		leftOffsetInTiles = widthInTiles / 2
		
		// create the player sprite based on a set of sprites from the sheet loaded
		// above (tank tracks moving)
		player = new Animation()
		for (frame <- 0 until 7) {
			player.addFrame(sheet.getSprite(frame,1), 150)
		}
		player.setAutoUpdate(false)

		// update the vector of movement based on the initial angle
		updateMovementVector()
		
		Log.info("Window Dimensions in Tiles: "+widthInTiles+"x"+heightInTiles)
	}

	def update(container:GameContainer, delta:Int) {
		// check the controls, left/right adjust the rotation of the tank, up/down 
		// move backwards and forwards
		if (container.getInput().isKeyDown(Input.KEY_LEFT)) {
			ang -= delta * TankRotateSpeed
			updateMovementVector()
		}
		if (container.getInput().isKeyDown(Input.KEY_RIGHT)) {
			ang += delta * TankRotateSpeed
			updateMovementVector()
		}
		if (container.getInput().isKeyDown(Input.KEY_UP)) {
			if (tryMove(dirX * delta * TankMoveSpeed, dirY * delta * TankMoveSpeed)) {
				// if we managed to move update the animation
				player.update(delta)
			}
		}
		if (container.getInput().isKeyDown(Input.KEY_DOWN)) {
			if (tryMove(-dirX * delta * TankMoveSpeed, -dirY * delta * TankMoveSpeed)) {
				// if we managed to move update the animation
				player.update(delta)
			}
		}
	}

	/**
	 * Check if a specific location of the tank would leave it 
	 * on a blocked tile
	 * 
	 * @param x The x coordinate of the tank's location
	 * @param y The y coordinate of the tank's location
	 * @return True if the location is blocked
	 */
	def blocked(x:Float, y:Float) = blockedTiles(x.asInstanceOf[Int])(y.asInstanceOf[Int])
	
	/**
	 * Try to move in the direction specified. If it's blocked, try sliding. If that
	 * doesn't work just don't bother
	 * 
	 * @param x The amount on the X axis to move
	 * @param y The amount on the Y axis to move
	 * @return True if we managed to move
	 */
	def tryMove(x:Float, y:Float) = {
		val newx:Float = playerX + x
		val newy:Float = playerY + y
		
		// first we try the real move, if that doesn't work
		// we try moving on just one of the axis (X and then Y) 
		// this allows us to slide against edges
		if (blocked(newx,newy)) {
			if (blocked(newx, playerY)) {
				if (blocked(playerX, newy)) {
					// can't move at all!
					false
				} else {
					playerY = newy;
					true
				}
			} else {
				playerX = newx
				true
			}
		} else {
			playerX = newx;
			playerY = newy;
			true;
		}
	}
	
	/**
	 * Update the direction that will be moved in based on the
	 * current angle of rotation
	 */
	def updateMovementVector() {
		dirX = Math.sin(Math.toRadians(ang)).asInstanceOf[Float]
		dirY = -Math.cos(Math.toRadians(ang)).asInstanceOf[Float]
	}
	
	def render(container:GameContainer, g:Graphics) { 
		// draw the appropriate section of the tilemap based on the centre (hence the -(TANK_SIZE/2)) of
		// the player
		val playerTileX = playerX.asInstanceOf[Int]
		val playerTileY = playerY.asInstanceOf[Int]
		
		// caculate the offset of the player from the edge of the tile. As the player moves around this
		// varies and this tells us how far to offset the tile based rendering to give the smooth
		// motion of scrolling
		val playerTileOffsetX = ((playerTileX - playerX) * TileSize).asInstanceOf[Int]
		val playerTileOffsetY = ((playerTileY - playerY) * TileSize).asInstanceOf[Int]
		
		// render the section of the map that should be visible. Notice the -1 and +3 which renders
		// a little extra map around the edge of the screen to cope with tiles scrolling on and off
		// the screen
		map.render(playerTileOffsetX - (TankSize / 2), playerTileOffsetY - (TankSize / 2), 
				   playerTileX - leftOffsetInTiles - 1, 
				   playerTileY - topOffsetInTiles - 1,
				   widthInTiles + 3, heightInTiles + 3)
		
		// draw entities relative to the player that must appear in the centre of the screen
		g.translate(400 - (playerX * 32).asInstanceOf[Int], 300 - (playerY * 32).asInstanceOf[Int]);
		
		drawTank(g, playerX, playerY, ang)
		// draw other entities here if there were any
		
		g.resetTransform()
	}

	/**
	 * Draw a single tank to the game
	 *  
	 * @param g The graphics context on which we're drawing
	 * @param xpos The x coordinate in tiles the tank is at
	 * @param ypos The y coordinate in tiles the tank is at
	 * @param rot The rotation of the tank
	 */
	def drawTank(g:Graphics, xpos:Float, ypos:Float, rot:Float) {
		// work out the centre of the tank in rendering coordinates and then
		// spit onto the screen
		val cx = (xpos * 32).asInstanceOf[Int]
		val cy = (ypos * 32).asInstanceOf[Int]
		g.rotate(cx,cy,rot)
		player.draw(cx-16,cy-16)
		g.rotate(cx,cy,-rot)
	}
}

object Scroller {	
	/**
	 * Entry point to the scroller example
	 * 
	 * @param argv The argument passed on the command line (if any)
	 */
	def main(argv:Array[String]) {
			// create a new container for our example game. This container
			// just creates a normal native window for rendering OpenGL accelerated
			// elements to
			val container = new AppGameContainer(new Scroller(), 800, 600, false)
			container.start()
  }
}
