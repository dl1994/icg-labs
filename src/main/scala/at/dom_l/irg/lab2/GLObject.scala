package at.dom_l.irg.lab2

import at.dom_l.irg.common.{GLDrawer, Vect}
import at.dom_l.irg.common.GLUtils.GL2Extender
import com.jogamp.opengl.GL2
import scala.collection.mutable.ListBuffer
import scala.io.Source

class GLObject(source: Source) extends GLDrawer {

    private val faces = ListBuffer[Array[Int]]()
    private val vertices = ListBuffer[Vect]()

    for (line <- source.getLines()) {
        val trimmedLine = line.trim

        if (trimmedLine.startsWith("v")) {
            this.vertices += Vect(trimmedLine.split("\\s+")
                .drop(1)
                .map(_.toDouble): _*)
        } else if (trimmedLine.startsWith("f")) {
            this.faces += trimmedLine.split("\\s+")
                .drop(1)
                .map(_.toInt)
                .map(_ - 1)
        }
    }

    private val facePlaneCoefficients = for (face <- this.faces) yield {
        val x = 0
        val y = 1
        val z = 2
        val v1 = this.vertices(face(0))
        val v2 = this.vertices(face(1))
        val v3 = this.vertices(face(2))

        val a = (v2(y) - v1(y)) * (v3(z) - v1(z)) - (v2(z) - v1(z)) * (v3(y) - v1(y))
        val b = (v2(z) - v1(z)) * (v3(x) - v1(x)) - (v2(x) - v1(x)) * (v3(z) - v1(z))
        val c = (v2(x) - v1(x)) * (v3(y) - v1(y)) - (v2(y) - v1(y)) * (v3(x) - v1(x))
        val d = -v1(x) * a - v1(y) * b - v1(z) * c

        (a, b, c, d)
    }

    private val points = {
        val nonNormalizedPoints = for {
            face <- this.faces
            vertexIndex <- face
            vertex = this.vertices(vertexIndex)
        } yield {
            (vertex(0), vertex(1))
        }

        val xMax = nonNormalizedPoints.map(_._1).max
        val xMin = nonNormalizedPoints.map(_._1).min
        val yMax = nonNormalizedPoints.map(_._2).max
        val yMin = nonNormalizedPoints.map(_._2).min
        val xMid = (xMin + xMax) / 2.0
        val yMid = (yMin + yMax) / 2.0
        val scale = 2 * ((xMax - xMin) max (yMax - yMin))

        for ((x, y) <- nonNormalizedPoints) yield {
            ((x - xMid) * scale, (y - yMid) * scale)
        }
    }

    def pointPositions(point: Vect) = {
        for (coefficients <- this.facePlaneCoefficients) yield {
            point(0) * coefficients._1 + point(1) * coefficients._2 + point(2) * coefficients._3 + coefficients._4
        }
    }

    override def draw(gl2: GL2, width: Int, height: Int) = {
        gl2.lines {
            gl2.glColor3f(1.0f, 1.0f, 1.0f)

            val scale = (width min height) * 0.45
            val xOffset = width / 2.0
            val yOffset = height / 2.0

            for (point <- this.points) {
                gl2.glVertex2d(point._1 * scale + xOffset, point._2 * scale + yOffset)
            }
        }
    }
}
