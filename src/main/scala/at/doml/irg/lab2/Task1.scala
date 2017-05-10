package at.doml.irg.lab2

import at.dom_l.irg.common.Vect
import at.doml.irg.common.{GLContext, Vect}
import scala.io.StdIn

object Task1 {

    private def check(input: String) = {
        val split = input.trim
            .split(",")
            .map(_.trim)

        if (split.length != 2) {
            println("2 values are expected!")
            System.exit(1)
        }

        Vect(split.map(_.toDouble): _*)
    }

    private def reducePointPosition(a: Double, b: Double) = {
        if (a > 0.0 || b > 0.0) {
            1.0
        } else if (a == 0.0 || b == 0.0) {
            0.0
        } else {
            -1.0
        }
    }

    def main(args: Array[String]) {
        print("Number of polygon points: ")

        val numPoints = StdIn.readInt()
        val points = new Array[Vect](numPoints)

        for (i <- 0 until numPoints) {
            print("Input point: ")
            points(i) = check(StdIn.readLine())
        }

        val context = new GLContext()
        val lineLoopDrawer = new LineLoopDrawer(points)
        val linePolygonDrawer = new LinePolygonDrawer(points)

        context.addDrawer(lineLoopDrawer)
        context.addDrawer(linePolygonDrawer)
        context.start()

        val coefficients = linePolygonDrawer.coefficients

        print("Input coordinates of a point: ")

        val point = check(StdIn.readLine())
        val pointPositions = for (i <- 0 until numPoints) yield {
            (point(0) * coefficients(i)(0) + point(1) * coefficients(i)(1) + coefficients(i)(2))
        }

        val pointPosition = pointPositions.reduce(reducePointPosition)

        println("Point is " + (if (pointPosition == 0.0) {
            "on the edge"
        } else if (pointPosition < 0.0) {
            "inside"
        } else {
            "outside"
        }) + " of the polygon.")

        linePolygonDrawer.enable()
    }
}
