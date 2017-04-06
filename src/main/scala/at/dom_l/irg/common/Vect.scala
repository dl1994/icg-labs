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
import org.apache.commons.math3.linear.{ArrayRealVector, RealVector}

class Vect(v: RealVector) {

    private val vector = v

    private def this(elements: Double*) = this(new ArrayRealVector(Array(elements: _*)))

    def apply(index: Int) = v.getEntry(index)

    private def binaryOperation(other: Vect, action: RealVector => RealVector) = {
        new Vect(action(other.vector))
    }

    override def toString = this.toString(0)

    def createSubscript(index: Int) = {
        val subscripts = Map(
            '0' -> '₀',
            '1' -> '₁',
            '2' -> '₂',
            '3' -> '₃',
            '4' -> '₄',
            '5' -> '₅',
            '6' -> '₆',
            '7' -> '₇',
            '8' -> '₈',
            '9' -> '₉'
        )

        index.toString.map(subscripts)
    }

    def toString(decimals: Int) = {
        val formatter = if (decimals > 0) {
            new DecimalFormat("0." + "0" * decimals)
        } else {
            new DecimalFormat()
        }

        if (this.vector.getDimension == 3) {
            "[ " + formatter.format(this.vector.getEntry(0)) + "i⃗, " +
                formatter.format(this.vector.getEntry(1)) + "j⃗, " +
                formatter.format(this.vector.getEntry(2)) + "k⃗ ]"
        } else {
            var index = 0
            "[ " + this.vector.toArray.map(formatter.format)
                .reduce((left, right) => {
                    index += 1
                    left + "x⃗" + createSubscript(index) + ", " + right
                }) + "x⃗" + createSubscript(index + 1) + " ]"
        }
    }

    def +(other: Vect) = binaryOperation(other, this.vector.add)

    def -(other: Vect) = binaryOperation(other, this.vector.subtract)

    def x(other: Vect) = {
        require(this.vector.getDimension == 3)
        require(other.vector.getDimension == 3)

        val thisArray = this.vector.toArray
        val otherArray = other.vector.toArray

        new Vect(
            thisArray(1) * otherArray(2) - thisArray(2) * otherArray(1),
            thisArray(2) * otherArray(0) - thisArray(0) * otherArray(2),
            thisArray(0) * otherArray(1) - thisArray(1) * otherArray(0)
        )
    }

    def unary_- = new Vect(this.vector.mapMultiply(-1.0))

    def norm = this.vector.getNorm

    def toArray = this.vector.toArray

    def toColMatrix = Matrix(this.vector.toArray.map(Array(_)): _*)
}

object Vect {

    def apply(elements: Double*) = new Vect(elements: _*)
}
