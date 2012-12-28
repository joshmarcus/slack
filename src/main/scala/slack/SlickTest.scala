package slack

import scala.react._
import scala.react.Domain
import slackdom._

// Initial goal: set up a signal that reports the current time,
// and show it in a Slick2D app.

import org.newdawn.slick.AppGameContainer
import org.newdawn.slick.{BasicGame, GameContainer, Graphics, Color}

object SlackTest extends ReactiveApp {
  val container = new AppGameContainer(new SlackTest, 800, 600, false)

  container.setTargetFrameRate(20)
  container.start
}

case class Clock extends EventSource[Int](slackdom.owner) {
  val sig = Var[String]("")
  var turnNumber = 0
  def clockTick {
    val newDate:String = new java.util.Date().toString
    sig update newDate
    turnNumber += 1
  }
}

case class ConsoleObserver(dateSignal:Var[String]) extends Observing {
  var currentTime:String = ""
  schedule { obs } 
  def obs {
    observe(dateSignal)(timeSignal => currentTime = timeSignal)
  }
}


class SlackTest extends BasicGame("Slack Test") {
  var current = new java.util.Date()
  val clock = Clock()
  val observer = ConsoleObserver(clock.sig)

  override def init(gc: GameContainer) {
    println("Slack test started.")
  }

  // Execute a turn per update
  override def update(gc: GameContainer, delta: Int) {
    current = new java.util.Date()
    clock.clockTick
  }

  override def render(gc: GameContainer, g: Graphics) {
    g.setColor(Color.white)
    g.drawString("Time signal: " + observer.currentTime, 200, 10)
  }
}
