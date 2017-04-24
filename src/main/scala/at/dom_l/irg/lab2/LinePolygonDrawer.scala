package at.dom_l.irg.lab2

import at.dom_l.irg.common.{GLDrawer, Vect}
import at.dom_l.irg.common.GLUtils.GL2Extender
import com.jogamp.opengl.GL2
import scala.math._

class LinePolygonDrawer(points: Array[Vect]) extends GLDrawer {

    private val xMin = points.map(_ (0)).reduce(min: (Double, Double) => Double).asInstanceOf[Int]
    private val xMax = points.map(_ (0)).reduce(max: (Double, Double) => Double).asInstanceOf[Int]
    private val yMin = points.map(_ (1)).reduce(min: (Double, Double) => Double).asInstanceOf[Int]
    private val yMax = points.map(_ (1)).reduce(max: (Double, Double) => Double).asInstanceOf[Int]
    private var enabled = false

    private def ni(i: Int) = {
        if (i < points.length - 1) {
            i + 1
        } else {
            0
        }
    }

    val coefficients = for (i <- points.indices) yield {
        val ni = this.ni(i)

        Vect(
            points(i)(1) - points(ni)(1), // y(i) - y(i + 1)
            points(ni)(0) - points(i)(0), // x(i + 1) - x(i)
            points(i)(0) * points(ni)(1) - points(ni)(0) * points(i)(1) // x(i) * y(i + 1) - x(i + 1) * y(i)
        )
    }

    private def drawLoop(gl2: GL2, width: Int, height: Int) = {
        for (y <- yMin to yMax) {
            var left = xMin
            var right = xMax

            for (i <- points.indices) {
                val ni = this.ni(i)

                if (coefficients(i)(0) != 0.0) {
                    val x = (-coefficients(i)(1) * y - coefficients(i)(2)) / coefficients(i)(0)

                    if (points(i)(1) < points(ni)(1) && x > left) {
                        left = round(x).asInstanceOf[Int]
                    }

                    if (points(i)(1) >= points(ni)(1) && x < right) {
                        right = round(x).asInstanceOf[Int]
                    }
                }

            }

            if (left < right) {
                gl2.glVertex2d(left, height - y)
                gl2.glVertex2d(right, height - y)
            }
        }
    }

    override def draw(gl2: GL2, width: Int, height: Int) = {
        if (this.enabled) {
            gl2.lines {
                gl2.glColor3f(1.0f, 1.0f, 1.0f)
                drawLoop(gl2, width, height)
            }
        }
    }

    def enable() = this.enabled = true
}
