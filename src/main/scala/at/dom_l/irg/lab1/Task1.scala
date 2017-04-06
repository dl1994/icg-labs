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

import at.dom_l.irg.common.{Matrix, Vect}

object Task1 {

    def main(args: Array[String]) {
        val v1 = Vect(2, 3, -4) + Vect(-1, 4, -1)

        println("v⃗₁ = [ 2i⃗ + 3j⃗ - 4k⃗ ] + [ -i⃗ + 4j⃗ - k⃗ ] = " + v1)
        println()
        println("s⃗ = v⃗₁ * [ -i⃗ + 4j⃗ - k⃗ ] = " + (v1 + Vect(-1, 4, -1)))
        println()

        val v2 = v1 x Vect(2, 2, 4)

        println("v⃗₂ = v⃗₁ x [ 2i⃗ + 2j⃗ + 4k⃗ ] = " + v2)
        println()
        println("v⃗₃ = |v⃗₂| = " + v2.norm)
        println()
        println("v⃗₄ = -v⃗₂ = " + -v2)

        val m1 = Matrix(
            Array(1, 2, 3),
            Array(2, 1, 3),
            Array(4, 5, 1)
        ) + Matrix(
            Array(-1, 2, -3),
            Array(5, -2, 7),
            Array(-4, -1, 3)
        )
        val m1Strings = m1.toString.split("\n")

        println("     ┌         ┐   ┌            ┐   " + m1Strings(0))
        println("     │ 1, 2, 3 │   │ -1,  2, -3 │   " + m1Strings(1))
        println("M₁ = │ 2, 1, 3 │ + │  5, -2,  7 │ = " + m1Strings(2))
        println("     │ 4, 5, 1 │   │ -4, -1,  3 │   " + m1Strings(3))
        println("     └         ┘   └            ┘   " + m1Strings(4))

        val m2 = Matrix(
            Array(1, 2, 3),
            Array(2, 1, 3),
            Array(4, 5, 1)
        ) + (Matrix(
            Array(-1, 2, -3),
            Array(5, -2, 7),
            Array(-4, -1, 3)
        ).transpose)
        val m2Strings = m2.toString.split("\n")

        println("     ┌         ┐   ┌            ┐T    " + m2Strings(0))
        println("     │ 1, 2, 3 │   │ -1,  2, -3 │     " + m2Strings(1))
        println("M₂ = │ 2, 1, 3 │ + │  5, -2,  7 │  =  " + m2Strings(2))
        println("     │ 4, 5, 1 │   │ -4, -1,  3 │     " + m2Strings(3))
        println("     └         ┘   └            ┘     " + m2Strings(4))

        val m3 = Matrix(
            Array(1, 2, 3),
            Array(2, 1, 3),
            Array(4, 5, 1)
        ) + (Matrix(
            Array(-1, 2, -3),
            Array(5, -2, 7),
            Array(-4, -1, 3)
        ) ^- 1)
        val m3Strings = m3.toString.split("\n")

        println("     ┌         ┐   ┌            ┐-1     " + m3Strings(0))
        println("     │ 1, 2, 3 │   │ -1,  2, -3 │       " + m3Strings(1))
        println("M₃ = │ 2, 1, 3 │ + │  5, -2,  7 │   =   " + m3Strings(2))
        println("     │ 4, 5, 1 │   │ -4, -1,  3 │       " + m3Strings(3))
        println("     └         ┘   └            ┘       " + m3Strings(4))
    }
}
