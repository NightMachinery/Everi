package Alice


import java.awt.Font

import processing.core.PApplet._
import processing.core.PApplet.{map => pMap}
import processing.core._
//import processing.data.XML

import scala.xml.XML
import scala.xml._

class GrossAddedByKindV extends ExtendedPApplet {

  import GrossAddedByKindV._
  //  val records: NodeSeq = null

  val ecoData: Elem = XML.loadFile("/Users/evar/Downloads/- Java Code/PPad/Data/us value added UNdata_Export_20171117_203535446.xml")
  //http://data.un.org/Data.aspx?q=iran&d=SNAAMA&f=grID%3a202%3bcurrID%3aNCU%3bpcFlag%3a0%3bcrID%3a364

  val agriRecs = (for {record <- ecoData \\ "record"
                       field <- record \ "field" if (field \@ "name" == "Item" && field.text.startsWith("Agri"))} yield record)
  //.sortWith((n1: Node, n2: Node) => n1 \@ "Year" < n2 \@ "Year")
  val miningRecs = (for {record <- ecoData \\ "record"
                         field <- record \ "field" if (field \@ "name" == "Item" && field.text.startsWith("Minin"))} yield record) //.sortWith((n1: Node, n2: Node) => n1 \@ "Year" < n2 \@ "Year")
  val manufacturingRecs = (for {record <- ecoData \\ "record"
                                field <- record \ "field" if (field \@ "name" == "Item" && field.text.startsWith("Manufacturing"))} yield record) //.sortWith((n1: Node, n2: Node) => n1 \@ "Year" < n2 \@ "Year")
  val constructionRecs = (for {record <- ecoData \\ "record"
                               field <- record \ "field" if (field \@ "name" == "Item" && field.text.startsWith("Construction"))} yield record) //.sortWith((n1: Node, n2: Node) => n1 \@ "Year" < n2 \@ "Year")
  val wholesaleRecs = (for {record <- ecoData \\ "record"
                            field <- record \ "field" if (field \@ "name" == "Item" && field.text.startsWith("Wholesale"))} yield record) //.sortWith((n1: Node, n2: Node) => n1 \@ "Year" < n2 \@ "Year")
  val transportRecs = (for {record <- ecoData \\ "record"
                            field <- record \ "field" if (field \@ "name" == "Item" && field.text.startsWith("Transport"))} yield record) //.sortWith((n1: Node, n2: Node) => n1 \@ "Year" < n2 \@ "Year")
  val otherRecs = (for {record <- ecoData \\ "record"
                        field <- record \ "field" if (field \@ "name" == "Item" && field.text.startsWith("Other"))} yield record) //.sortWith((n1: Node, n2: Node) => n1 \@ "Year" < n2 \@ "Year")
  val totalRecs = (for {record <- ecoData \\ "record"
                        field <- record \ "field" if (field \@ "name" == "Item" && field.text.startsWith("Total"))} yield record) //.sortWith((n1: Node, n2: Node) => n1 \@ "Year" < n2 \@ "Year")

  override def setup() = {
    super.setup()
    surface.setResizable(true)
    setRecording(false)
  }

  override def settings(): Unit = {
    super.settings()
    size(1110, 1110)
    setDefaultFrameRate(10)
    setRecording(false)
  }

  def mapYear(year: Float, leftMargin: Float = 10, rightMargin: Float = stepWidth + 10): Float = {
    pMap(year, 1970, 2015, leftMargin, width - rightMargin)
  }

  override def draw(): Unit = {
    //    ellipse(20,20,20,20)
    //surface.setSize(1110, 1110) //(825, 825)
    pushStyle()
    background(240, 231, 216, 255);
    //    fill(179, 136, 235, 255)
    //    //    fill(255,0,0)
    //    for (agriRec <- agriRecs) {
    //      ellipse(mapYear((agriRec \ "field").find(_ \@ "name" == "Year").get.text.toFloat), 30, 2, (((agriRec \ "field") find {
    //        _ \@ "name" == "Value"
    //      }).get.text.toFloat / Math.pow(10, 10).toFloat))
    //    }
    //    pushMatrix()
    //    fill(128, 147, 241, 255)
    //    stroke(51, 65, 57, 255)
    //    for (agriRec <- agriRecs) {
    //      ellipse(mapYear((agriRec \ "field").find(_ \@ "name" == "Year").get.text.toFloat), 30, 10, (((agriRec \ "field") find {
    //        _ \@ "name" == "Value"
    //      }).get.text.toFloat / Math.pow(10, 11).toFloat))
    //    }
    //    popMatrix()
    noStroke()
    pushMatrix()
    fill(85, 230, 185)
    //    rotate(radians(5))
    //    translate(0,0)


    var locY = 30
    textFont(new PFont(new Font("FFF Galaxy Extended", Font.PLAIN, 10 /*10*/), true))
    textAlign(PConstants.CENTER)
    text("Agriculture, hunting, forestry, fishing (ISIC A-B)", width / 2, locY)
    locY += 10
    for (agriRec <- agriRecs) {

      rect(mapYear((agriRec \ "field").find(_ \@ "name" == "Year").get.text.toFloat), locY, stepWidth, (((agriRec \ "field") find {
        _ \@ "name" == "Value"
      }).get.text.toFloat / scaleFactor.toFloat))
    }
    popMatrix()
    pushMatrix()
    fill(172, 176, 189);
    //    rotate(radians(-10))
    //    translate(-10, -90)
    locY += 100
    text("Mining, Manufacturing, Utilities (ISIC C-E)", width / 2, locY)
    locY += 10
    for (agriRec <- miningRecs) {

      rect(mapYear((agriRec \ "field").find(_ \@ "name" == "Year").get.text.toFloat), locY, stepWidth, (((agriRec \ "field") find {
        _ \@ "name" == "Value"
      }).get.text.toFloat / scaleFactor.toFloat))
    }
    popMatrix()

    pushMatrix()
    fill(65, 97, 101)
    locY += 100
    text("Manufacturing (ISIC D)", width / 2, locY)
    locY += 10
    for (agriRec <- manufacturingRecs) {

      rect(mapYear((agriRec \ "field").find(_ \@ "name" == "Year").get.text.toFloat), locY, stepWidth, (((agriRec \ "field") find {
        _ \@ "name" == "Value"
      }).get.text.toFloat / scaleFactor.toFloat))
    }
    popMatrix()

    pushMatrix()
    //fill(11,57,72)
    fill(92, 87, 62)
    locY += 100
    text("Construction (ISIC F)", width / 2, locY)
    locY += 10
    for (agriRec <- constructionRecs) {

      rect(mapYear((agriRec \ "field").find(_ \@ "name" == "Year").get.text.toFloat), locY, stepWidth, (((agriRec \ "field") find {
        _ \@ "name" == "Value"
      }).get.text.toFloat / scaleFactor.toFloat))
    }
    popMatrix()

    pushMatrix()
    fill(165, 180, 82)
    locY += 100
    text("Wholesale, retail trade, restaurants and hotels (ISIC G-H)", width / 2, locY)
    locY += 10
    for (agriRec <- wholesaleRecs) {

      rect(mapYear((agriRec \ "field").find(_ \@ "name" == "Year").get.text.toFloat), locY, stepWidth, (((agriRec \ "field") find {
        _ \@ "name" == "Value"
      }).get.text.toFloat / scaleFactor.toFloat))
    }
    popMatrix()

    pushMatrix()
    fill(239, 118, 122)
    locY += 100
    text("Transport, storage and communication (ISIC I)", width / 2, locY)
    locY += 10
    for (agriRec <- transportRecs) {

      rect(mapYear((agriRec \ "field").find(_ \@ "name" == "Year").get.text.toFloat), locY, stepWidth, (((agriRec \ "field") find {
        _ \@ "name" == "Value"
      }).get.text.toFloat / scaleFactor.toFloat))
    }
    popMatrix()

    pushMatrix()
    fill(69, 105, 144)
    locY += 100
    text("Other Activities (ISIC J-P)", width / 2, locY)
    locY += 10
    for (agriRec <- otherRecs) {

      rect(mapYear((agriRec \ "field").find(_ \@ "name" == "Year").get.text.toFloat), locY, stepWidth, (((agriRec \ "field") find {
        _ \@ "name" == "Value"
      }).get.text.toFloat / scaleFactor.toFloat))
    }
    popMatrix()

    pushMatrix()
    locY += 100
    fill(128, 18, 100, 50)
    text("Total Value Added", width / 2, locY)
    locY += 10
    for (agriRec <- totalRecs) {

      rect(mapYear((agriRec \ "field").find(_ \@ "name" == "Year").get.text.toFloat), locY, stepWidth, (((agriRec \ "field") find {
        _ \@ "name" == "Value"
      }).get.text.toFloat / scaleFactor.toFloat))
    }
    popMatrix()

    fill(11, 57, 72)
    locY += 275
    textFont(new PFont(new Font("Gill Sans", Font.PLAIN, 10 /*10*/), true))
    text("Gross Value Added by Kind of Economic Activity at constant (2005) prices; US 1970 (left) to 2015 (right). The topleft bar represents 48,182,736,345.0016 in local currency.\nSource: National Accounts Estimates of Main Aggregates | United Nations Statistics Division", width / 2, locY)
    popStyle()
    super.draw()
  }
}

object GrossAddedByKindV {
  final val stepWidth = 7
  final val scaleFactor = Math.pow(10, 10.9)
}