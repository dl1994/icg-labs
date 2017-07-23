package at.doml.irg.lab4

import at.doml.irg.common.GLContext
import scala.io.{Source, StdIn}

object Task1 {

    def main(args: Array[String]): Unit = {
        print("Input object: ")

        val source = Source.fromFile(StdIn.readLine())
        val glObject = new GLShadedObject(source)
        val context = new GLContext()

        context.addDrawer(glObject)
        context.start()

        // TODO shading
    }
}
