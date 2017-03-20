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

import at.dom_l.irg.common.GLContext
import scala.io.StdIn

object Task4 {

    private def check(input: String) = {
        val split = input.trim
            .split(",")
            .map(_.trim)

        if (split.length != 2) {
            println("2 values are expected!")
            System.exit(1)
        }

        split.map(_.toInt)
    }

    def main(args: Array[String]) {
        print("Enter first point coordinates: ")

        val first = check(StdIn.readLine())

        print("Enter second point coordinates: ")

        val second = check(StdIn.readLine())

        println()
        println("Points: A = ( " + first(0) + ", " + first(1) +
            " ), B = ( " + second(0) + ", " + second(1) + " )")

        val context = new GLContext()
        val bresenhamDrawer = new BresenhamDrawer(first(0), first(1), second(0), second(1))
        val glLineDrawer = new GLLineDrawer(first(0), first(1), second(0), second(1))
        val mouseHandler = new LineDrawerMouseHandler()

        mouseHandler.addDrawer(bresenhamDrawer)
        mouseHandler.addDrawer(glLineDrawer)

        context.addMouseListener(mouseHandler)
        context.addMouseMotionListener(mouseHandler)
        context.addDrawer(bresenhamDrawer)
        context.addDrawer(glLineDrawer)

        //        context.addDrawer(new BresenhamDrawer(100, 100, 150, 100))
        //        context.addDrawer(new BresenhamDrawer(100, 100, 150, 150))
        //        context.addDrawer(new BresenhamDrawer(100, 100, 100, 150))
        //        context.addDrawer(new BresenhamDrawer(100, 100, 50, 150))
        //        context.addDrawer(new BresenhamDrawer(100, 100, 50, 100))
        //        context.addDrawer(new BresenhamDrawer(100, 100, 150, 50))
        //        context.addDrawer(new BresenhamDrawer(100, 100, 100, 50))
        //        context.addDrawer(new BresenhamDrawer(100, 100, 50, 50))
        //        context.addDrawer(new BresenhamDrawer(100, 100, 150, 125))
        //        context.addDrawer(new BresenhamDrawer(100, 100, 125, 150))
        //        context.addDrawer(new BresenhamDrawer(100, 100, 150, 75))
        //        context.addDrawer(new BresenhamDrawer(100, 100, 125, 50))
        //        context.addDrawer(new BresenhamDrawer(100, 100, 75, 50))
        //        context.addDrawer(new BresenhamDrawer(100, 100, 50, 75))
        //        context.addDrawer(new BresenhamDrawer(100, 100, 75, 150))
        //        context.addDrawer(new BresenhamDrawer(100, 100, 50, 125))

        context.start()
    }
}
