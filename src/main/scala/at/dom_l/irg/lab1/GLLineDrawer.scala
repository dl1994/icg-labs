package at.dom_l.irg.lab1

import at.dom_l.irg.common.GLUtils.GL2Extender
import com.jogamp.opengl.GL2

class GLLineDrawer(x1: Int, y1: Int, x2: Int, y2: Int) extends LineDrawer {

    override var xs = x1
    override var xe = x2
    override var ys = y1
    override var ye = y2
    override val xsi = x1
    override val xei = x2
    override val ysi = y1
    override val yei = y2

    override def draw(gl2: GL2, width: Int, height: Int) = {
        gl2.lines {
            gl2.glColor3f(1.0f, 1.0f, 1.0f)
            gl2.glVertex2f(xs, height - ys + 20.0f)
            gl2.glVertex2f(xe, height - ye + 20.0f)
        }
    }
}
