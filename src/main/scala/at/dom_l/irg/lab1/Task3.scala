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

import at.dom_l.irg.common.{Matrix, Vect}
import scala.io.StdIn

object Task3 {

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

    def main(args: Array[String]) {
        println("Input data for triangle points:")
        print("First triangle point: ")

        val first = check(StdIn.readLine())

        print("Second triangle point: ")

        val second = check(StdIn.readLine())

        print("Third triangle point: ")

        val third = check(StdIn.readLine())

        println()
        println("          A = " + first)
        println("Triangle: B = " + second)
        println("          C = " + third)
        println()
        println("Input data for a single point: ")

        val point = check(StdIn.readLine())
        val solution = (Matrix.fromVectors(
            first,
            second,
            third
        ) ^- 1) * point.toColMatrix
        val solutionStrings = solution.toString.split("\n")

        println()
        println("          ┌    ┐   " + solutionStrings(0))
        println("          │ t₁ │   " + solutionStrings(1))
        println("Solution: │ t₂ │ = " + solutionStrings(2))
        println("          │ t₃ │   " + solutionStrings(3))
        println("          └    ┘   " + solutionStrings(4))
    }
}
