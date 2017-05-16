package at.doml.irg.lab3

import at.doml.irg.common.{GLContext, Vect}
import scala.io.StdIn

object Task2 {

    private def readPoint(message: String) = {
        print(message)

        val split = StdIn.readLine()
            .trim
            .split(",")
            .map(_.trim)

        if (split.length != 2) {
            println("2 values are expected!")
            System.exit(1)
        }

        Vect(split.map(_.toDouble): _*)
    }

    def main(args: Array[String]) {
        print("Number of polygon points: ")

        val numPoints = StdIn.readInt()
        val points = new Array[Vect](numPoints)

        for (i <- 0 until numPoints) {
            points(i) = readPoint("Input direction point: ")
        }

        print("Step: ")

        val coeffStep = StdIn.readDouble()
        val glObject = new BrezierCurve(points, coeffStep)
        val context = new GLContext()

        context.addDrawer(glObject)
        context.start()
    }
}
