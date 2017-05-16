package at.doml.irg.lab3

import at.doml.irg.common.{GLDrawer, Vect}
import at.doml.irg.common.GLUtils.GL2Extender
import com.jogamp.opengl.GL2

class BrezierCurve(points: Array[Vect], coeffStep: Double) extends GLDrawer {

    private def interpolate(start: Vect, end: Vect, coeff: Double) = {
        Vect(start(0) + coeff * (end(0) - start(0)), start(1) + coeff * (end(1) - start(1)))
    }

    private def deepInterpolate(gl2: GL2, points: Array[Vect], coeff: Double): Unit = {
        if (points.length == 1) {
            gl2.glVertex2d(points(0)(0), points(0)(1))
        } else if (points.length > 1) {
            val interpolation = new Array[Vect](points.length - 1)

            for (i <- 0 until points.length - 1) {
                interpolation(i) = interpolate(points(i), points(i + 1), coeff)
            }

            deepInterpolate(gl2, interpolation, coeff)
        }
    }

    override def draw(gl2: GL2, width: Int, height: Int) = {
        gl2.lineStrip {
            gl2.glColor3f(1.0f, 1.0f, 1.0f)

            for (point <- this.points) {
                gl2.glVertex2d(point(0), point(1))
            }
        }

        gl2.points {
            gl2.glColor3f(1.0f, 0.0f, 0.0f)

            for (coeff <- 0.0 to(1.0, coeffStep)) {
                deepInterpolate(gl2, this.points, coeff)
            }
        }
    }
}
