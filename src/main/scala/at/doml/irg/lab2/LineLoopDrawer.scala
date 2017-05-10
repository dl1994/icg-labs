package at.doml.irg.lab2

import at.dom_l.irg.common.{GLDrawer, Vect}
import at.dom_l.irg.common.GLUtils.GL2Extender
import at.doml.irg.common.{GLDrawer, Vect}
import com.jogamp.opengl.GL2

class LineLoopDrawer(points: Array[Vect]) extends GLDrawer {

    override def draw(gl2: GL2, width: Int, height: Int) = {
        gl2.lineLoop {
            gl2.glColor3f(1.0f, 1.0f, 1.0f)
            points.foreach(p => gl2.glVertex2d(p(0), height - p(1)))
        }
    }
}
