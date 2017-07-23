package at.doml.irg.lab4

import at.doml.irg.common.GLUtils.GL2Extender
import at.doml.irg.common.{GLDrawer, Matrix, Vect}
import com.jogamp.opengl.GL2
import scala.collection.mutable.ListBuffer
import scala.io.Source

class GLShadedObject(source: Source) extends GLDrawer {

    private val faces = ListBuffer[(Int, Int, Int)]()
    private val vertices = ListBuffer[(Double, Double, Double)]()

    for (line <- source.getLines()) {
        val trimmedLine = line.trim

        if (trimmedLine.startsWith("v")) {
            val v = trimmedLine.split("\\s+")
                .drop(1)
                .map(_.toDouble)
            vertices += ((v(0), v(1), v(2)))
        } else if (trimmedLine.startsWith("f")) {
            val t = trimmedLine.split("\\s+")
                .drop(1)
                .map(_.toInt)
                .map(_ - 1)
            faces += ((t(0), t(1), t(2)))
        }
    }

    private val normalizedPoints = {
        val xMax = this.vertices.map(_._1).max
        val xMin = this.vertices.map(_._1).min
        val yMax = this.vertices.map(_._2).max
        val yMin = this.vertices.map(_._2).min
        val zMax = this.vertices.map(_._3).max
        val zMin = this.vertices.map(_._3).min
        val xMid = (xMin + xMax) / 2.0
        val yMid = (yMin + yMax) / 2.0
        val zMid = (zMin + zMax) / 2.0
        val scale = 2 * ((xMax - xMin) max (yMax - yMin) max (zMax - zMin))

        for ((x, y, z) <- this.vertices) yield {
            ((x - xMid) * scale, (y - yMid) * scale, (z - zMid) * scale)
        }
    }

    private def calculateTransformationMatrix(directionPoint: Vect, viewpoint: Vect): Matrix = {
        val t1 = Matrix(
            Array(1, 0, 0, 0),
            Array(0, 1, 0, 0),
            Array(0, 0, 1, 0),
            Array(-viewpoint(0), -viewpoint(1), -viewpoint(2), 1)
        )

        val dp1 = directionPoint.toRowMatrix * t1
        val t2 = if ((dp1(0)(1) == 0 && dp1(0)(0) == 0)) {
            Matrix(
                Array(1, 0, 0, 0),
                Array(0, 1, 0, 0),
                Array(0, 0, 1, 0),
                Array(0, 0, 0, 1)
            )
        } else {
            val sinA = getSineOrCosine(dp1(0)(1), dp1(0)(0))
            val cosA = getSineOrCosine(dp1(0)(0), dp1(0)(1))
            Matrix(
                Array(cosA, -sinA, 0, 0),
                Array(sinA, cosA, 0, 0),
                Array(0, 0, 1, 0),
                Array(0, 0, 0, 1)
            )
        }

        val t3 = if (dp1(0)(0) == 0 && dp1(0)(2) == 0) {
            Matrix(
                Array(1, 0, 0, 0),
                Array(0, 1, 0, 0),
                Array(0, 0, 1, 0),
                Array(0, 0, 0, 1)
            )
        } else {
            val sinB = getSineOrCosine(dp1(0)(0), dp1(0)(2))
            val cosB = getSineOrCosine(dp1(0)(2), dp1(0)(0))
            Matrix(
                Array(cosB, 0, sinB, 0),
                Array(0, 1, 0, 0),
                Array(-sinB, 0, cosB, 0),
                Array(0, 0, 0, 1)
            )
        }

        val t4 = Matrix(
            Array(0, -1, 0, 0),
            Array(1, 0, 0, 0),
            Array(0, 0, 1, 0),
            Array(0, 0, 0, 1)
        )
        val t5 = Matrix(
            Array(-1, 0, 0, 0),
            Array(0, 1, 0, 0),
            Array(0, 0, 1, 0),
            Array(0, 0, 0, 1)
        )

        t1 * t2 * t3 * t4 * t5
    }

    private def getSineOrCosine(a: Double, b: Double) = {
        a / Math.sqrt(a * a + b * b)
    }

    private def getPerspectiveProjectionMatrix(directionPoint: Vect, viewpoint: Vect) = {
        val distance = Math.sqrt(Math.pow(viewpoint(0) - directionPoint(0), 2.0) +
            Math.pow(viewpoint(1) - directionPoint(1), 2.0) +
            Math.pow(viewpoint(2) - directionPoint(2), 2.0))
        Matrix(
            Array(1, 0, 0, 0),
            Array(0, 1, 0, 0),
            Array(0, 0, 0, 1.0 / distance),
            Array(0, 0, 0, 0)
        )
    }

    private val directionPoint = Vect(0, 0, 0)
    private val viewpoint = Vect(0, 0, 5)
    private val viewVector = viewpoint - directionPoint

    private val projectedPoints = {
        val d = Vect(directionPoint(0), directionPoint(1), directionPoint(2), 1)
        val v = Vect(viewpoint(0), viewpoint(1), viewpoint(2), 1)
        val t = calculateTransformationMatrix(d, v)
        val p = getPerspectiveProjectionMatrix(d, v)
        val transformationMatrix = t * p

        for (point <- this.normalizedPoints) yield {
            val v = Vect(point._1, point._2, point._3, 1).toRowMatrix * transformationMatrix
            (v(0)(0) / v(0)(3), v(0)(1) / v(0)(3), v(0)(2) / v(0)(3))
        }
    }

    private val triangles = {
        for (face <- this.faces) yield {
            Array(this.projectedPoints(face._1),
                this.projectedPoints(face._2),
                this.projectedPoints(face._3))
        }
    }

    private def getTriangleNormal(triangle: Array[(Double, Double, Double)]): Vect = {
        val p1 = triangle(0)
        val p2 = triangle(1)
        val p3 = triangle(2)
        val v1 = Vect(p2._1 - p1._1, p2._2 - p1._2, p2._3 - p1._3)
        val v2 = Vect(p3._1 - p1._1, p3._2 - p1._2, p3._3 - p1._3)

        v1 x v2
    }

    override def draw(gl2: GL2, width: Int, height: Int) = {
        gl2.lines {
            gl2.glColor3f(1.0f, 1.0f, 1.0f)

            val scale = (width min height) * 0.05
            val xOffset = width / 2.0
            val yOffset = height / 2.0

            for (p <- this.triangles) {
                if (getTriangleNormal(p).cos(viewVector) >= 0) {
                    gl2.glVertex2d(p(0)._1 * scale + xOffset, p(0)._2 * scale + yOffset)
                    gl2.glVertex2d(p(1)._1 * scale + xOffset, p(1)._2 * scale + yOffset)
                    gl2.glVertex2d(p(1)._1 * scale + xOffset, p(1)._2 * scale + yOffset)
                    gl2.glVertex2d(p(2)._1 * scale + xOffset, p(2)._2 * scale + yOffset)
                    gl2.glVertex2d(p(2)._1 * scale + xOffset, p(2)._2 * scale + yOffset)
                    gl2.glVertex2d(p(0)._1 * scale + xOffset, p(0)._2 * scale + yOffset)
                }
            }
        }
    }
}
