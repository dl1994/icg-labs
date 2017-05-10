package at.doml.irg.lab1

import at.doml.irg.common.GLContext
import scala.io.StdIn

object Task4 {

    private def check(input: String) = {
        val split = input.trim
            .split(",")
            .map(_.trim)

        if (split.length != 2) {
            println("2 values are expected!")
            System.exit(1)
        }

        split.map(_.toInt)
    }

    def main(args: Array[String]) {
        print("Enter first point coordinates: ")

        val first = check(StdIn.readLine())

        print("Enter second point coordinates: ")

        val second = check(StdIn.readLine())

        println()
        println("Points: A = ( " + first(0) + ", " + first(1) +
            " ), B = ( " + second(0) + ", " + second(1) + " )")

        val context = new GLContext()
        val bresenhamDrawer = new BresenhamDrawer(first(0), first(1), second(0), second(1))
        val glLineDrawer = new GLLineDrawer(first(0), first(1), second(0), second(1))
        val mouseHandler = new LineDrawerMouseHandler()

        mouseHandler.addDrawer(bresenhamDrawer)
        mouseHandler.addDrawer(glLineDrawer)

        context.addMouseListener(mouseHandler)
        context.addMouseMotionListener(mouseHandler)
        context.addDrawer(bresenhamDrawer)
        context.addDrawer(glLineDrawer)
        context.start()
    }
}
