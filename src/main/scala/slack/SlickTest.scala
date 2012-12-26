package slack


// Initial goal: set up a signal that reports the current time,
// and show it in a Slick2D app.

import org.newdawn.slick.AppGameContainer
import org.newdawn.slick.{BasicGame, GameContainer, Graphics, Color}

object SlackTest {
  def main(args: Array[String]) = {
    val container = new AppGameContainer(new SlackTest, 800, 600, false)

    container.setTargetFrameRate(20)
    container.start
  }
}

class SlackTest extends BasicGame("Slack Test") {
  var current = new java.util.Date()

  override def init(gc: GameContainer) {
    println("Slack test started.")
  }

  // Execute a turn per update
  override def update(gc: GameContainer, delta: Int) {
    current = new java.util.Date()
  }

  override def render(gc: GameContainer, g: Graphics) {
    g.setColor(Color.white)
    g.drawString("Time signal: %s".format(current), 200, 10)
  }
}
