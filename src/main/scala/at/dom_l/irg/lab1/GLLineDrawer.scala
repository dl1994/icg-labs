/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * The MIT License (MIT)                                                           *
 *                                                                                 *
 * Copyright © 2017 Domagoj Latečki                                                *
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
