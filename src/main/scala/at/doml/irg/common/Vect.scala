package at.doml.irg.common

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

    def cos(other: Vect) = this.vector.dotProduct(other.vector)

    def unary_- = new Vect(this.vector.mapMultiply(-1.0))

    def norm = this.vector.getNorm

    def toArray = this.vector.toArray

    def toColMatrix = Matrix(this.vector.toArray.map(Array(_)): _*)

    def toRowMatrix = Matrix(this.vector.toArray)
}

object Vect {

    def apply(elements: Double*) = new Vect(elements: _*)
}
