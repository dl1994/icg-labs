/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * The MIT License (MIT)                                                           *
 *                                                                                 *
 * Copyright © 2017 Domagoj Latečki                                             *
 *                                                                                 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy    *
 * of this software and associated documentation files (the "Software"), to deal   *
 * in the Software without restriction, including without limitation the rights    *
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell       *
 * copies of the Software, and to permit persons to whom the Software is           *
 * furnished to do so, subject to the following conditions:                        *
 *                                                                                 *
 * The above copyright notice and this permission notice shall be included in all  *
 * copies or substantial portions of the Software.                                 *
 *                                                                                 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR      *
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,        *
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE     *
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER          *
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,   *
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE   *
 * SOFTWARE.                                                                       *
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
package at.dom_l.irg.lab1

import at.dom_l.irg.common.GLUtils.GL2Extender
import at.dom_l.irg.common.GLDrawer
import com.jogamp.opengl.GL2

class BresenhamDrawer(x1: Int, y1: Int, x2: Int, y2: Int) extends LineDrawer {

    override var xs = x1
    override var xe = x2
    override var ys = y1
    override var ye = y2
    override val xsi = x1
    override val xei = x2
    override val ysi = y1
    override val yei = y2

    override def draw(gl2: GL2, width: Int, height: Int) = {
        if (xs <= xe) {
            draw(gl2, xs, ys, xe, ye)
        } else {
            draw(gl2, xe, ye, xs, ys)
        }
    }

    private def draw(gl2: GL2, xStart: Int, yStart: Int, xEnd: Int, yEnd: Int) = {
        var drawFunction: (Int, Int) => Unit = gl2.glVertex2f(_, _)
        var errorChecker: Int => Boolean = {
            _ >= 0
        }
        var correction = -2 * (xEnd - xStart)
        var stepChange = 1
        var step = 2 * (yEnd - yStart)
        var current = yStart
        var error = -(xEnd - xStart)
        var range = xStart to xEnd

        if (yStart <= yEnd) {
            if (!(yEnd - yStart < xEnd - xStart)) {
                drawFunction = (x, y) => gl2.glVertex2f(y, x)
                correction = -2 * (yEnd - yStart)
                step = 2 * (xEnd - xStart)
                current = xStart
                error = -(yEnd - yStart)
                range = yStart to yEnd
            }
        } else {
            errorChecker = _ <= 0
            stepChange = -1

            if (-(yEnd - yStart) <= xEnd - xStart) {
                correction = 2 * (xEnd - xStart)
            } else {
                drawFunction = (x: Int, y: Int) => gl2.glVertex2f(y, x)
                correction = 2 * (yStart - yEnd)
                step = 2 * (xStart - xEnd)
                current = xEnd
                error = -(yStart - yEnd)
                range = yEnd to yStart
            }
        }


        gl2.points {
            gl2.glColor3f(1.0f, 0.0f, 0.0f)

            for (v <- range) {
                drawFunction(v, current)
                error = error + step

                if (errorChecker(error)) {
                    error = error + correction
                    current += stepChange
                }
            }
        }
    }
}
