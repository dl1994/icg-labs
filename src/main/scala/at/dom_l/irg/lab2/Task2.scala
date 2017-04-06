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
package at.dom_l.irg.lab2

import at.dom_l.irg.common.{GLContext, Vect}
import scala.io.{Source, StdIn}

object Task2 {

    private def check(input: String) = {
        val split = input.trim
            .split(",")
            .map(_.trim)

        if (split.length != 3) {
            println("3 values are expected!")
            System.exit(1)
        }

        Vect(split.map(_.toDouble): _*)
    }

    private def reducePointPosition(a: Double, b: Double) = {
        if (a > 0.0 || b > 0.0) {
            1.0
        } else if (a == 0.0 || b == 0.0) {
            0.0
        } else {
            -1.0
        }
    }

    def main(args: Array[String]) {
        print("Input file name: ")

        val source = Source.fromFile(StdIn.readLine())
        val glObject = new GLObject(source)

        print("Input 3D point coordinates: ")

        val point = check(StdIn.readLine())
        val pointPosition = glObject.pointPositions(point).reduce(reducePointPosition)

        println("Point is " + (if (pointPosition == 0.0) {
            "on the edge"
        } else if (pointPosition < 0.0) {
            "inside"
        } else {
            "outside"
        }) + " of the object.")

        val context = new GLContext()

        context.addDrawer(glObject)
        context.start()
    }
}
