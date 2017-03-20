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

import at.dom_l.irg.common.Matrix
import scala.io.StdIn

object Task2 {

    private def check(input: String) = {
        val split = input.trim
            .split(",")
            .map(_.trim)

        if (split.length != 4) {
            println("4 values are expected!")
            System.exit(1)
        }

        (split, split.map(_.toDouble))
    }

    private def printEquation(values: (Array[String], Array[Double])) = {
        values._1(0) + "x " + withSign(values._1(1), values._2(1), 'y') +
            withSign(values._1(2), values._2(2), 'z') + "= " + values._1(3)
    }

    private def withSign(string: String, value: Double, symbol: Char) = {
        (if (value < 0) {
            "- " + string.stripPrefix("-")
        } else {
            "+ " + string
        }) + symbol + " "
    }

    def main(args: Array[String]) {
        println("Input data for equations:")
        print("First equation coefficients: ")

        val first = check(StdIn.readLine())

        print("Second equation coefficients: ")

        val second = check(StdIn.readLine())

        print("Third equation coefficients: ")

        val third = check(StdIn.readLine())

        println()
        println("           " + printEquation(first))
        println("Equations: " + printEquation(second))
        println("           " + printEquation(third))

        val solution = (Matrix(
            first._2.dropRight(1),
            second._2.dropRight(1),
            third._2.dropRight(1)
        ) ^- 1) * Matrix(
            first._2.drop(3),
            second._2.drop(3),
            third._2.drop(3)
        )
        val solutionStrings = solution.toString.split("\n")

        println()
        println("          ┌   ┐   " + solutionStrings(0))
        println("          │ x │   " + solutionStrings(1))
        println("Solution: │ y │ = " + solutionStrings(2))
        println("          │ z │   " + solutionStrings(3))
        println("          └   ┘   " + solutionStrings(4))
    }
}
