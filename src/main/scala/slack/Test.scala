package slack

import org.lwjgl.opengl.GL11._
import org.lwjgl.opengl.{
  Display, 
  DisplayMode
}
import org.lwjgl.input.Keyboard
import Keyboard._

object HelloWorld {
  def main(args:Array[String]) {
    val displayMode = new DisplayMode(640, 480)
    Display.setTitle("Hello World")
    Display.setDisplayMode(displayMode)
    Display.create()
    fader(0f) 
    Display.destroy()
    sys.exit(0)

    def fader(color: Float, d: Int = -1) {
      val mult = if(color > 1f) 1 else if(color < 0f) -1 else d
      val newColor = color - (mult * 0.05f) 

      glClearColor(newColor, newColor, newColor, 1f)
      glClear(GL_COLOR_BUFFER_BIT)

      Display.update()
      Thread.sleep(60)
      if(!Display.isCloseRequested) fader(newColor, mult)
    }
  }
}
