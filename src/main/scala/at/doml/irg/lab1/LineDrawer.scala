package at.doml.irg.lab1

import at.doml.irg.common.GLDrawer

trait LineDrawer extends GLDrawer {

    var xs: Int
    var xe: Int
    var ys: Int
    var ye: Int

    val xsi: Int
    val xei: Int
    val ysi: Int
    val yei: Int

    def reset = {
        xs = xsi
        xe = xei
        ys = ysi
        ye = yei
    }
}
