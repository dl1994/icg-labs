package at.doml.irg.lab4

import at.doml.irg.common.GLDrawer
import at.doml.irg.common.GLUtils.GL2Extender
import com.jogamp.opengl.GL2

class Fractal(
                 uMin: Double, uMax: Double, vMin: Double, vMax: Double, eps: Double, m: Int,
                 constant: (Double, Double), drawJulia: Boolean
             ) extends GLDrawer {

    private val MIN_COLOR = (0.1f, 0.9f, 0.3f)
    private val MAX_COLOR = (0.1f, 0.3f, 0.9f)

    override def draw(gl2: GL2, width: Int, height: Int): Unit = {
        val uConstant = (uMax - uMin) / width
        val vConstant = (vMax - vMin) / height

        gl2.points {
            for {
                x <- 0 to width
                y <- 0 to height
            } {
                val u = uConstant * x + uMin
                val v = vConstant * y + vMin
                val c = if (drawJulia) {
                    constant
                } else {
                    (u, v)
                }

                val z0 = if (drawJulia) {
                    (u, v)
                } else {
                    (0.0, 0.0)
                }

                val k = findK(-1, z0, c)

                (gl2.glColor3f _).tupled(interpolateColor(k))
                gl2.glVertex2i(x, y)
            }
        }
    }

    private def findK(k: Int, z: (Double, Double), c: (Double, Double)): Int = {
        val newK = k + 1
        val newZ = add(square(z), c)
        val zr = newZ._1
        val zi = newZ._2
        val r = Math.sqrt(zr * zr + zi * zi)

        if (r > eps || k > m) {
            return newK
        }

        findK(newK, newZ, c)
    }

    private def square(n: (Double, Double)): (Double, Double) = {
        val r = n._1
        val i = n._2

        (r * r - i * i, 2 * r * i)
    }

    private def add(f: (Double, Double), s: (Double, Double)): (Double, Double) = {
        (f._1 + s._1, f._2 + s._2)
    }

    private def interpolateColor(k: Int): (Float, Float, Float) = {
        if (k > m) {
            return (0.0f, 0.0f, 0.0f)
        }

        val t = k.toFloat / m.toFloat

        (MIN_COLOR._1 + t * (MAX_COLOR._1 - MIN_COLOR._1),
            MIN_COLOR._2 + t * (MAX_COLOR._2 - MIN_COLOR._2),
            MIN_COLOR._3 + t * (MAX_COLOR._3 - MIN_COLOR._3))
    }
}
