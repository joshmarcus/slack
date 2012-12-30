package slack

import slackdom._

import org.newdawn.slick.{Game,InputListener,Input => SlickInput,GameContainer}

class InputEvents extends EventSource[Input](slackdom.owner) {
  //val lastKeyPressed = Var[KeyPressed](null)
  //val keyPressed = lastKeyPressed changes

  val keyPressed = EventSource[KeyPressed]
  val keyReleased = EventSource[KeyReleased]
  val key = keyPressed merge keyReleased
  val all = key
}

/**
 * Basic game implementation
 */
abstract class ReactiveGame(title:String) extends Game with InputListener {

  // player input event sources
  val inputEvents = new InputEvents()
  val MaxControllers = 20  
  val MaxControllerButtons = 100 

  /** The state of the left control */
  val controllerLeft = Array.ofDim[Boolean](MaxControllers)

  /** The state of the right control */
  val controllerRight = Array.ofDim[Boolean](MaxControllers)

  /** The state of the up control */
  val controllerUp = Array.ofDim[Boolean](MaxControllers)

  /** The state of the down control */
  val controllerDown = Array.ofDim[Boolean](MaxControllers)

  /** The state of the button controls */
  val controllerButton = Array.ofDim[Boolean](MaxControllers,MaxControllerButtons)


	def setInput(input:SlickInput) {	}
	
	def closeRequested() = true
	
  def getTitle() = title
	
  def init(container:GameContainer):Unit 

	def keyPressed(key:Int, c:Char) { 
    println(s"keypressed: ${c}")
    inputEvents.keyPressed << KeyPressed(key, c)
  }

	/**
	 * @see org.newdawn.slick.InputListener#keyReleased(int, char)
	 */
	def keyReleased(key:Int, c:Char) {

  }

	/**
	 * @see org.newdawn.slick.InputListener#mouseMoved(int, int, int, int)
	 */
	def mouseMoved(oldx:Int, oldy:Int, newx:Int, newy:Int) { }

	/**
	 * @see org.newdawn.slick.InputListener#mouseDragged(int, int, int, int)
	 */
	def mouseDragged(oldx:Int, oldy:Int, newx:Int, newy:Int) { }
	
	/**
	 * @see org.newdawn.slick.InputListener#mouseClicked(int, int, int, int)
	 */
	def mouseClicked(button:Int, x:Int, y:Int, clickCount:Int) { }
	
	/**
	 * @see org.newdawn.slick.InputListener#mousePressed(int, int, int)
	 */
	def mousePressed(button:Int, x:Int, y:Int) { }
	
	/**
	 * @see org.newdawn.slick.InputListener#controllerButtonPressed(int, int)
	 */
  def controllerButtonPressed(controller:Int, button:Int) { 
    controllerButton(controller)(button) = true 
  } 

	/**
	 * @see org.newdawn.slick.InputListener#controllerButtonReleased(int, int)
	 */
  def controllerButtonReleased(controller:Int, button:Int) { 
    controllerButton(controller)(button) = false 
  }

	/**
	 * @see org.newdawn.slick.InputListener#controllerDownPressed(int)
	 */
	def controllerDownPressed(controller:Int) {
		controllerDown(controller) = true;
	}

	/**
	 * @see org.newdawn.slick.InputListener#controllerDownReleased(int)
	 */
	def controllerDownReleased(controller:Int) {
		controllerDown(controller) = false;
	}

	/**
	 * @see org.newdawn.slick.InputListener#controllerLeftPressed(int)
	 */
	def controllerLeftPressed(controller:Int) {
		controllerLeft(controller) = true;
	}

	/**
	 * @see org.newdawn.slick.InputListener#controllerLeftReleased(int)
	 */
	def controllerLeftReleased(controller:Int) {
		controllerLeft(controller) = false;
	}

	/**
	 * @see org.newdawn.slick.InputListener#controllerRightPressed(int)
	 */
	def controllerRightPressed(controller:Int) {
		controllerRight(controller) = true;
	}

	/**
	 * @see org.newdawn.slick.InputListener#controllerRightReleased(int)
	 */
	def controllerRightReleased(controller:Int) {
		controllerRight(controller) = false;
	}

	/**
	 * @see org.newdawn.slick.InputListener#controllerUpPressed(int)
	 */
	def controllerUpPressed(controller:Int) {
		controllerUp(controller) = true;
	}

	/**
	 * @see org.newdawn.slick.InputListener#controllerUpReleased(int)
	 */
	def controllerUpReleased(controller:Int) {
		controllerUp(controller) = false;
	}
	
	/**
	 * @see org.newdawn.slick.InputListener#mouseReleased(int, int, int)
	 */
	def mouseReleased(button:Int, x:Int, y:Int) {
	}

	/**
	 * @see org.newdawn.slick.Game#update(org.newdawn.slick.GameContainer, int)
	 */
	def update(container:GameContainer, delta:Int):Unit

	/**
	 * @see org.newdawn.slick.InputListener#mouseWheelMoved(int)
	 */
	def mouseWheelMoved(change: Int) {}

	/**
	 * @see org.newdawn.slick.InputListener#isAcceptingInput()
	 */
	def isAcceptingInput():Boolean = true
	
	/**
	 * @see org.newdawn.slick.InputListener#inputEnded()
	 */
	def inputEnded():Unit = { }
		
	/**
	 * @see org.newdawn.slick.ControlledInputReciever#inputStarted()
	 */
	def inputStarted():Unit = { }
}

