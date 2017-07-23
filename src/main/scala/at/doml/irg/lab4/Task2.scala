package at.doml.irg.lab4

import at.doml.irg.common.GLContext
import scala.io.StdIn

object Task2 {

    private def readDouble(message: String): Double = {
        print(message)

        val value = StdIn.readDouble()

        if (value < 0.0) {
            println("Positive value is expected!")
            System.exit(1)
        }

        value
    }

    private def readInt(message: String): Int = {
        print(message)

        val value = StdIn.readInt()

        if (value < 0) {
            println("Positive value is expected!")
            System.exit(1)
        }

        value
    }

    private def readPoint(message: String): (Double, Double) = {
        print(message)

        val split = StdIn.readLine()
            .trim
            .split(",")
            .map(_.trim)
            .map(_.toDouble)

        if (split.length != 2) {
            println("2 values are expected!")
            System.exit(1)
        }

        (split(0), split(1))
    }

    private def readBoolean(message: String): Boolean = {
        print(message)
        StdIn.readBoolean()
    }

    def main(args: Array[String]): Unit = {
        val eps = readDouble("Input eps: ")
        val m = readInt("Input number of iterations: ")
        val bottomLeft = readPoint("Input bottom left point: ")
        val topRight = readPoint("Input top right point: ")
        val uMin = bottomLeft._1 min topRight._1
        val uMax = bottomLeft._1 max topRight._1
        val vMin = bottomLeft._2 min topRight._2
        val vMax = bottomLeft._2 max topRight._2
        val drawJulia = readBoolean("Draw Julia fractal? (y/n): ")
        val constant = if (drawJulia) {
            readPoint("Input constant: ")
        } else {
            (0.0, 0.0)
        }

        val mandelbrotFractal = new Fractal(uMin, uMax, vMin, vMax, eps, m, constant, drawJulia)
        val context = new GLContext()

        context.addDrawer(mandelbrotFractal)
        context.start()
    }
}
