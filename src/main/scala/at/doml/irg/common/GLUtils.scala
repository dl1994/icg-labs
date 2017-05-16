package at.doml.irg.common

import com.jogamp.opengl.{GL, GL2}

object GLUtils {

    implicit class GL2Extender(gl2: GL2) {

        private def scopedGLDraw(mode: Int, function: => Unit) = {
            gl2.glBegin(mode)
            function
            gl2.glEnd()
        }

        def lines(function: => Unit) = scopedGLDraw(GL.GL_LINES, function)

        def lineLoop(function: => Unit) = scopedGLDraw(GL.GL_LINE_LOOP, function)

        def lineStrip(function: => Unit) = scopedGLDraw(GL.GL_LINE_STRIP, function)

        def points(function: => Unit) = scopedGLDraw(GL.GL_POINTS, function)
    }
}
