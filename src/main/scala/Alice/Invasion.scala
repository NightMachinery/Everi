//CC3
// Forked from https://www.openprocessing.org/sketch/468685/.
package Alice

import processing.core.PVector
import java.util
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

class Invasion extends ExtendedPApplet {
  var attractors: util.ArrayList[Attractor] = _
  var agents: util.ArrayList[Agent] = _
  var doClear = false
  var play = true
  var mirror = true
  val rotate = false
  var visibleAttractors = true


  override def settings(): Unit = {
    super.settings()
    size(700, 700)
    setDefaultFrameRate(60)
    setRecording(false)
  }

  override def setup(): Unit = {
    super.setup()
    surface.setResizable(true)
    setRecording(false)
    background(0)
    colorMode(PConstants.HSB, 360, 100, 100, 100)
    noStroke()
    frameRate(60)
    attractors = new util.ArrayList[Attractor]
    agents = new util.ArrayList[Agent]
    var i = 0
    while ( {
      i < 1500
    }) {
      val a = new Agent()
      agents.add(a)
      i += 1
    }
  }

  override def draw(): Unit = {
    if (doClear) {
      background(0)
      agents.clear()
      attractors.clear()
      var i = 0
      while ( {
        i < 2000
      }) {
        val a = new Agent()
        agents.add(a)
        i += 1
      }
      doClear = false
    }
    if (play) {
      fill(0, 0, 0, 10)
      noStroke()
      rect(0, 0, width, height)
      var i = 0
      while ( {
        i < agents.size
      }) {
        agents.get(i).update()
        i += 1
      }
    }
    var i = 0
    while ( {
      i < attractors.size
    }) {
      attractors.get(i).update()
      i += 1
    }
    super.draw()
  }

  override def mouseClicked(): Unit = {
    super.mouseClicked()
    val a = new Attractor(mouseX, mouseY)
    attractors.add(a)
    if (mirror) {
      val a = new Attractor(width - mouseX, mouseY) //height - mouseY)
      attractors.add(a)
    }
  }

  override def keyPressed(): Unit = {
    println(key.toInt)
    if (key == 'C') {
      doClear = true //This was commented out in original source.
    }
    if (key == 'c') {
      var i = 0
      while ( {
        i < agents.size
      }) {
        agents.get(i).reset()
        i += 1
      }
    }
    if (key == 'v')
      visibleAttractors = !visibleAttractors
    if ((key == 'M') || (key == 'm')) mirror = !mirror
    if (key == 10) play = !play //RET
    super.keyPressed()
  }

  class Agent() {
    var oPos: PVector = new PVector(0, 0)
    var nPos: PVector = new PVector(0, 0)
    var vel: PVector = new PVector(0, 0)
    var mass = 1f
    var c: Int = color(0, 100, 100)
    reset()

    def update(): Unit = {
      var doReset = false
      var i = 0
      while ( {
        i < attractors.size
      }) {
        val a = attractors.get(i)
        if (PVector.dist(nPos, a.pos) < 10) doReset = true
        val d2 = pow(PVector.dist(nPos, a.pos), 2)
        val n = PVector.sub(a.pos, nPos)
        n.normalize
        val f = 100 * (a.mass * mass) / d2
        n.mult(f)
        vel.add(n)
        i += 1
      }
      nPos.add(vel)
      if (doReset) reset()
      if (nPos.x < 0) reset()
      if (nPos.y < 0) reset()
      if (nPos.x > width) reset()
      if (nPos.y > height) reset()
      c = color(vel.mag * 50, 90, vel.mag * 15 + 30)
      stroke(c)
      strokeWeight(3)
      line(oPos.x, oPos.y, nPos.x, nPos.y)
      oPos = new PVector(nPos.x, nPos.y)
    }

    def reset(): Unit = {
      vel = new PVector(0, 0)
      val dir = 0
      random(4)
      if (dir == 0) nPos = new PVector(random(width), 0)
      if (dir == 1) nPos = new PVector(random(width), height)
      if (dir == 2) nPos = new PVector(0, random(height))
      if (dir == 3) nPos = new PVector(width, random(height))
      oPos = new PVector(nPos.x, nPos.y)
    }
  }

  class Attractor(val x: Float, val y: Float) {
    var pos: PVector = new PVector(x, y)
    var mass = 1f

    def update(): Unit = {
      fill(0, 0, 100)
      noStroke()
      if (visibleAttractors)
        ellipse(pos.x, pos.y, 5, 5)
    }
  }

}
