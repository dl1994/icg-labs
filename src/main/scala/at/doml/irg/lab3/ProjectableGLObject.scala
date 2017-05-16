package at.doml.irg.lab3

import at.doml.irg.common.GLUtils.GL2Extender
import at.doml.irg.common.{GLDrawer, Matrix, Vect}
import com.jogamp.opengl.GL2
import scala.collection.mutable.ListBuffer
import scala.io.Source

class ProjectableGLObject(source: Source, transformation: Matrix) extends GLDrawer {

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

    protected val points = {
        val nonNormalizedPoints = for {
            face <- this.faces
            vertexIndex <- face
            vertex = this.vertices(vertexIndex)
        } yield {
            val p = Vect(vertex(0), vertex(1), vertex(2), 1).toRowMatrix * transformation
            (p(0)(0) / p(0)(3), p(0)(1) / p(0)(3), p(0)(2) / p(0)(3))
        }

        val xMax = nonNormalizedPoints.map(_._1).max
        val xMin = nonNormalizedPoints.map(_._1).min
        val yMax = nonNormalizedPoints.map(_._2).max
        val yMin = nonNormalizedPoints.map(_._2).min
        val zMax = nonNormalizedPoints.map(_._3).max
        val zMin = nonNormalizedPoints.map(_._3).min
        val xMid = (xMin + xMax) / 2.0
        val yMid = (yMin + yMax) / 2.0
        val zMid = (zMin + zMax) / 2.0
        val scale = 2 * ((xMax - xMin) max (yMax - yMin) max (zMax - zMin))

        for ((x, y, z) <- nonNormalizedPoints) yield {
            ((x - xMid) * scale, (y - yMid) * scale, (z - zMid) * scale)
        }
    }

    override def draw(gl2: GL2, width: Int, height: Int) = {
        gl2.lineLoop {
            gl2.glColor3f(1.0f, 1.0f, 1.0f)

            val scale = (width min height) * 0.025
            val xOffset = width / 2.0
            val yOffset = height / 2.0

            for ((x, y, z) <- this.points) {
                gl2.glVertex2d(x * scale + xOffset, y * scale + yOffset)
            }
        }
    }
}
