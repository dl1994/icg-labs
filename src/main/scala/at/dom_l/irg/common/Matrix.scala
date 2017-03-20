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
package at.dom_l.irg.common

import java.text.DecimalFormat
import org.apache.commons.math3.linear.{Array2DRowRealMatrix, LUDecomposition, RealMatrix}

class Matrix(m: RealMatrix) {

    private val matrix = m

    private def this(values: Array[Double]*) = this(new Array2DRowRealMatrix(Array(values: _*)))

    private def binaryOperation(other: Matrix, action: RealMatrix => RealMatrix) = {
        new Matrix(action(other.matrix))
    }

    override def toString = this.toString(0)

    def toString(decimals: Int) = {
        val formatter = if (decimals > 0) {
            new DecimalFormat("0." + "0" * decimals)
        } else {
            new DecimalFormat()
        }

        val numberStrings = for {
            row <- this.matrix.getData
            cell <- row
        } yield {
            formatter.format(cell)
        }

        val maxNumLength = numberStrings.map(_.length).max
        val columnSize = this.matrix.getColumnDimension
        val numSpaces = ((maxNumLength + 2) * columnSize - 2)
        val builder = new StringBuilder("┌ " + " " * numSpaces + " ┐\n")

        var items = 0

        for (string <- numberStrings) {
            items += 1

            if (items == 1) {
                builder.append("│ ")
            }

            builder.append(" " * (maxNumLength - string.length))
                .append(string)

            if (items == columnSize) {
                builder.append(" │\n")
                items = 0
            } else {
                builder.append(", ")
            }
        }

        builder.append("└ " + " " * numSpaces + " ┘\n")
        builder.toString
    }

    def +(other: Matrix) = binaryOperation(other, this.matrix.add)

    def -(other: Matrix) = binaryOperation(other, this.matrix.subtract)

    def *(other: Matrix) = binaryOperation(other, this.matrix.multiply)

    def transpose = new Matrix(this.matrix.transpose)

    def ^-(power: Int) = new Matrix(new LUDecomposition(this.matrix).getSolver.getInverse.power(power))

    def ^(power: Int) = new Matrix(this.matrix.power(power))
}

object Matrix {

    def apply(values: Array[Double]*) = new Matrix(values: _*)

    def fromVectors(vectors: Vect*) = new Matrix(vectors.map(_.toArray): _*)
}
