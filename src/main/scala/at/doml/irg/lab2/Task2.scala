package at.doml.irg.lab2

import at.dom_l.irg.common.Vect
import at.doml.irg.common.{GLContext, Vect}
import scala.io.{Source, StdIn}

object Task2 {

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
        print("Input file name: ")

        val source = Source.fromFile(StdIn.readLine())
        val glObject = new GLObject(source)

        print("Input 3D point coordinates: ")

        val point = check(StdIn.readLine())
        val pointPosition = glObject.pointPositions(point).reduce(reducePointPosition)

        println("Point is " + (if (pointPosition == 0.0) {
            "on the edge"
        } else if (pointPosition < 0.0) {
            "inside"
        } else {
            "outside"
        }) + " of the object.")

        val context = new GLContext()

        context.addDrawer(glObject)
        context.start()
    }
}
