package net.portalhexaddon.portals

import net.fabricmc.loader.impl.FabricLoaderImpl.InitHelper
import qouteall.imm_ptl.core.portal.GeometryPortalShape
import qouteall.imm_ptl.core.portal.Portal
import java.util.stream.Collectors
import java.util.stream.IntStream
import kotlin.math.cos
import kotlin.math.sin

class PortalHexUtils {
    companion object {
    fun makePortalNGon(portal: Portal, sides: Int) { //GOTTEN FROM IMEMRSIVE PORTALS PortalCommand
        val shape = GeometryPortalShape()
        val triangleNum = sides
        val twoPi = Math.PI * 2
        shape.triangles = IntStream.range(0, triangleNum)
            .mapToObj { i: Int ->
                GeometryPortalShape.TriangleInPlane(
                    0.0, 0.0,
                    portal.width * 0.5 * cos(twoPi * (i.toDouble()) / triangleNum),
                    portal.height * 0.5 * sin(twoPi * (i.toDouble()) / triangleNum),
                    portal.width * 0.5 * cos(twoPi * (i.toDouble() + 1) / triangleNum),
                    portal.height * 0.5 * sin(twoPi * (i.toDouble() + 1) / triangleNum)
                )
            }.collect(Collectors.toList())
        portal.specialShape = shape
        portal.cullableXStart = 0.0
        portal.cullableXEnd = 0.0
        portal.cullableYStart = 0.0
        portal.cullableYEnd = 0.0
        }
    }
}