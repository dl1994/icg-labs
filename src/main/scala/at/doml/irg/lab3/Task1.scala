package at.doml.irg.lab3

import at.doml.irg.common.{GLContext, Matrix, Vect}
import scala.io.{Source, StdIn}

object Task1 {

    private def readPoint(message: String) = {
        print(message)

        val split = StdIn.readLine()
            .trim
            .split(",")
            .map(_.trim)

        if (split.length != 3) {
            println("3 values are expected!")
            System.exit(1)
        }

        val cooridnates = split.map(_.toDouble)
        val vectorArray = new Array[Double](4)

        vectorArray(0) = cooridnates(0)
        vectorArray(1) = cooridnates(1)
        vectorArray(2) = cooridnates(2)
        vectorArray(3) = 1.0

        Vect(vectorArray: _*)
    }

    private def printMatrix(m: Matrix, i: String) = {
        val mStrings = m.toString.split("\n")

        println("          " + mStrings(0))
        println("_____     " + mStrings(1))
        println("  │    __ " + mStrings(2))
        println("  │    ‾‾ " + mStrings(3))
        println("    " + i + "     " + mStrings(4))
        println("          " + mStrings(5))
    }

    private def calculateTransformationMatrix(directionPoint: Vect, viewpoint: Vect) = {
        val t1 = Matrix(
            Array(1, 0, 0, 0),
            Array(0, 1, 0, 0),
            Array(0, 0, 1, 0),
            Array(-viewpoint(0), -viewpoint(1), -viewpoint(2), 1)
        )

        printMatrix(t1, "1")

        val dp1 = directionPoint.toRowMatrix * t1
        val sinA = getSineOrCosine(dp1(0)(1), dp1(0)(0))
        val cosA = getSineOrCosine(dp1(0)(0), dp1(0)(1))
        val t2 = Matrix(
            Array(cosA, -sinA, 0, 0),
            Array(sinA, cosA, 0, 0),
            Array(0, 0, 1, 0),
            Array(0, 0, 0, 1)
        )

        printMatrix(t2, "2")

        val dp2 = dp1 * t2
        val sinB = getSineOrCosine(dp1(0)(0), dp1(0)(2))
        val cosB = getSineOrCosine(dp1(0)(2), dp1(0)(0))
        val t3 = Matrix(
            Array(cosB, 0, sinB, 0),
            Array(0, 1, 0, 0),
            Array(-sinB, 0, cosB, 0),
            Array(0, 0, 0, 1)
        )

        printMatrix(t3, "3")

        val t4 = Matrix(
            Array(0, -1, 0, 0),
            Array(1, 0, 0, 0),
            Array(0, 0, 1, 0),
            Array(0, 0, 0, 1)
        )
        val t5 = Matrix(
            Array(-1, 0, 0, 0),
            Array(0, 1, 0, 0),
            Array(0, 0, 1, 0),
            Array(0, 0, 0, 1)
        )
        val t = t1 * t2 * t3 * t4 * t5

        printMatrix(t4, "4")
        printMatrix(t5, "5")
        printMatrix(t, " ")

        t
    }

    private def getSineOrCosine(a: Double, b: Double) = {
        a / Math.sqrt(a * a + b * b)
    }

    private def getPerspectiveProjectionMatrix(directionPoint: Vect, viewpoint: Vect) = {
        val distance = Math.sqrt(Math.pow(viewpoint(0) - directionPoint(0), 2.0) +
            Math.pow(viewpoint(1) - directionPoint(1), 2.0) +
            Math.pow(viewpoint(2) - directionPoint(2), 2.0))
        Matrix(
            Array(1, 0, 0, 0),
            Array(0, 1, 0, 0),
            Array(0, 0, 0, 1.0 / distance),
            Array(0, 0, 0, 0)
        )
    }

    def main(args: Array[String]) {
        val directionPoint = readPoint("Input direction point: ")
        val viewpoint = readPoint("Input viewpoint: ")
        print("Input file name: ")

        val source = Source.fromFile(StdIn.readLine())
        val t = calculateTransformationMatrix(directionPoint, viewpoint)
        val p = getPerspectiveProjectionMatrix(directionPoint, viewpoint)
        val transformationMatrix = t * p
        val glObject = new ProjectableGLObject(source, transformationMatrix)
        val context = new GLContext()

        context.addDrawer(glObject)
        context.start()
    }
}
