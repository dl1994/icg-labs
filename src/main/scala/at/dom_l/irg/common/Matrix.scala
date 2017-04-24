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
