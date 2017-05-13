package at.doml.irg.lab1

import at.doml.irg.common.{Matrix, Vect}

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
