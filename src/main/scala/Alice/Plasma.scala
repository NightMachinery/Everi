package Alice


import java.awt.Font

import processing.core.PApplet._
import processing.core.PApplet.{map => pMap}
import processing.core._


import java.applet.Applet
import java.awt._

import UnbiasedColor._

import scala.util.Random

class Plasma extends ExtendedPApplet {
  var myRand = new Random()

  //Randomly displaces color value for midpoint depending on size
  //of grid piece.
  def Displace(num: Float): Float = {
    val max = num / (width + height).toFloat * 3
    (myRand.nextFloat() - 0.5f) * max
  }

  //Returns a color based on a color value, c.
  def ComputeColor(c: Float): Color = {
    var Red = 0f
    var Green = 0f
    var Blue = 0f
    if (c < 0.5f) Red = c * 2
    else Red = (1.0f - c) * 2
    if (c >= 0.3f && c < 0.8f) Green = (c - 0.3f) * 2
    else if (c < 0.3f) Green = (0.3f - c) * 2
    else Green = (1.3f - c) * 2
    if (c >= 0.5f) Blue = (c - 0.5f) * 2
    else Blue = (0.5f - c) * 2
    UnbiasedColor.generate((Color.decode("#160D27"), Math.min(Math.abs(Red * 1.0f - 0.0f), 1.0f)) :: (Color.decode("#FF4200"), Math.min(Green * 1.0f, 1.0f)) :: (Color.decode("#91BF2C"), Math.min(Blue * 1.0f, 1.0f)) :: Nil)
    //new Color(Math.min(Math.abs(Red * 1.9f - 0.0f), 1.0f), Math.min(Green * 1.2f, 1.0f), Math.min(Blue * 1.9f, 1.0f))
  }

  var mySeed = 0l

  //This is something of a "helper function" to create an initial grid
  //before the recursive function is called.
  def drawPlasma(g: PGraphics, width: Int, height: Int): Unit = {
    var c1 = .0f
    var c2 = .0f
    var c3 = .0f
    var c4 = .0f
    //Assign the four corners of the intial grid random color values
    //These will end up being the colors of the four corners of the applet.

    c1 = c1.getMe
    c2 = c2.getMe
    c3 = c3.getMe
    c4 = c4.getMe
    mySeed += 1
    myRand.setSeed(mySeed)
    DivideGrid(g, 0, 0, width, height, c1, c2, c3, c4)
  }

  //This is the recursive function that implements the random midpoint
  //displacement algorithm.  It will call itself until the grid pieces
  //become smaller than one pixel.
  def DivideGrid(g: PGraphics, x: Float, y: Float, width: Float, height: Float, c1: Float, c2: Float, c3: Float, c4: Float): Unit = {
    var Edge1 = .0f
    var Edge2 = .0f
    var Edge3 = .0f
    var Edge4 = .0f
    var Middle = .0f
    val newWidth = width / 2
    val newHeight = height / 2
    if (width > 2 || height > 2) {
      Middle = (c1 + c2 + c3 + c4) / 4 + Displace(newWidth + newHeight) //Randomly displace the midpoint!
      Edge1 = (c1 + c2) / 2 //Calculate the edges by averaging the two corners of each edge.
      Edge2 = (c2 + c3) / 2
      Edge3 = (c3 + c4) / 2
      Edge4 = (c4 + c1) / 2
      //Make sure that the midpoint doesn't accidentally "randomly displaced" past the boundaries!
      if (Middle < 0) Middle = 0
      else if (Middle > 1.0f) Middle = 1.0f
      //Do the operation over again for each of the four new grids.
      DivideGrid(g, x, y, newWidth, newHeight, c1, Edge1, Middle, Edge4)
      DivideGrid(g, x + newWidth, y, newWidth, newHeight, Edge1, c2, Edge2, Middle)
      DivideGrid(g, x + newWidth, y + newHeight, newWidth, newHeight, Middle, Edge2, c3, Edge3)
      DivideGrid(g, x, y + newHeight, newWidth, newHeight, Edge4, Middle, Edge3, c4)
    }
    else { //This is the "base case," where each grid piece is less than the size of a pixel.
      //The four corners of the grid piece will be averaged and drawn as a single pixel.
      val c = (c1 + c2 + c3 + c4) / 4
      g.fillColor = ComputeColor(c).getRGB

      g.rect(x.toInt, y.toInt, 1, 1)
    }
  }


  override def setup(): Unit = {
    super.setup()
    surface.setResizable(true)
    setRecording(false)
    noStroke()
  }

  override def settings(): Unit = {
    super.settings()
    size(700, 700)
    setDefaultFrameRate(1)
    setRecording(false)
  }
  override def draw(): Unit = {
    background(255f)
    drawPlasma(this.getGraphics, width, height)
    fill(Color.pink.getRGB)
    rect(10f,10f,10f,10f)
    fill(Color.blue.getRGB)
    rect(50f,50f,10f,10f)
    fill(Color.green.getRGB)
    rect(50f,500f,10f,10f)
    this.getGraphics.updatePixels()
  }
}

