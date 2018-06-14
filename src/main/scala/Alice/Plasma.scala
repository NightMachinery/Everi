package Alice


import java.awt.Font

import processing.core.PApplet._
import processing.core.PApplet.{map => pMap}
import processing.core._
import java.applet.Applet
import java.awt._
import java.nio.file.FileSystems

import UnbiasedColor._
import javax.swing.JOptionPane
import processing.event.KeyEvent

import scala.annotation.switch
import scala.util.Random

class Plasma extends ExtendedPApplet {
  val midpointRand = new Random()
  var displacementPotency = 0.4f
  var displacementRange = 1f

  implicit class mySuperFloat(val self: Float) {
    def getMe: Float = {
      //println( (self + 0.00000005f) % 1.0f)
      //(self + 0.00000005f) % 1.0f
      val v1 = self + pMap(midpointRand.nextGaussian().toFloat, -displacementRange, displacementRange, -displacementPotency, displacementPotency)
      circleValue(v1,1f)
      // Math.random.toFloat

    }

    def circleValue(value: Float, negativeMedicine: Float = 1f): Float = {
      if (value  < 0)
        return 0
      else
        return value
      val r = value % 1
      if (r < 0)
        r + negativeMedicine
      else
        r
    }

    def circleMe: Float = {
      circleValue(self)
    }
  }

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

    Red = Red.circleMe
    Green = Green.circleMe
    Blue = Blue.circleMe

    //UnbiasedColor.generate((Color.decode("#F7F7F6"), Math.min(Math.abs(Red * 1.0f - 0.0f), 1.0f)) :: (Color.decode("#DAA053"), Math.min(Green * 1.0f, 1.0f)) :: (Color.decode("#989DA9"), Math.min(Blue * 1.0f, 1.0f)) :: Nil)
   // UnbiasedColor.generate((Color.decode("#F7F7F6"), Math.min(Math.abs(Red * 1.0f - 0.0f), 1.0f)) :: (Color.decode("#DAA053"), Math.min(Green * 1.0f, 1.0f)) :: (Color.decode("#989DA9"), Math.min(Blue * 1.0f, 1.0f)) :: Nil)
    new Color(Math.min(Math.abs(Red * 1.9f - 0.0f), 1.0f), Math.min(Green * 1.2f, 1.0f), Math.min(Blue * 1.9f, 1.0f)) //Seed: 8 Creates heart

    //Intense pink heart
    //new Color(Math.min(Math.abs(Red * 1.9f - 0.0f), 1.0f), Math.min(Green * 1.2f, 1.0f), Math.min(Blue * 1.9f, 1.0f)) //Seed: 8 Creates heart
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
    //mySeed += 1
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
      g.stroke(ComputeColor(c).getRGB)
      // g.stroke(Color.red.getRGB)
      g.strokeCap(PConstants.SQUARE)
      // g.point(x.toInt, y.toInt)
      g.rect(x.toInt, y.toInt, 1, 1)
    }
  }


  override def setup(): Unit = {
    super.setup()
    surface.setResizable(true)
    setRecording(false)
  }

  override def settings(): Unit = {
    super.settings()
    size(700, 700)
    setDefaultFrameRate(60)
    setRecording(false)
  }

  override def draw(): Unit = {
    background(255f)
    drawPlasma(this.getGraphics, width, height)
    //    fill(Color.pink.getRGB)
    //    rect(600f,600f,10f,10f)
    //    fill(Color.blue.getRGB)
    //    rect(50f,60f,10f,10f)
    //    fill(Color.green.getRGB)
    //    rect(50f,500f,10f,10f)
    //    fill(Color.cyan.getRGB)
    //this.getGraphics.updatePixels()
    super.draw()

  }

  override def keyPressed(event: KeyEvent) {
    super.keyPressed(event)
    (key.toLower: @switch) match {
      case '8' | '*' =>
        if (event.isShiftDown) {
          swingInvoke {
            Option(JOptionPane.showInputDialog("Specify corners' seed.", mySeed)) foreach (v => mySeed = v.toLong)
            return
          }
        } else {
          mySeed += 1
          println("Seed: " + mySeed)
        }
      case '9' | '(' =>
        if (event.isShiftDown)
          swingInvoke {
            Option(JOptionPane.showInputDialog("Specify displacement's range.", displacementRange)) foreach (v => displacementRange = v.toFloat)
            return
          }
        else
          swingInvoke {
            Option(JOptionPane.showInputDialog("Specify displacement's potency.", displacementPotency)) foreach (v => displacementPotency = v.toFloat)
            return
          }
      case _ =>
    }
  }
}

