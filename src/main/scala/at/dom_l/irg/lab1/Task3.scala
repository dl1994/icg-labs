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
