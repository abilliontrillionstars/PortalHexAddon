package net.portalhexaddon.portals

import at.petrak.hexcasting.api.spell.casting.CastingContext
import net.minecraft.world.entity.Entity
import qouteall.imm_ptl.core.portal.GeometryPortalShape
import qouteall.imm_ptl.core.portal.Portal
import java.util.ArrayList
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

        fun getPortalInAmbit(ctx: CastingContext): ArrayList<Entity> {
            var prtEnt: Iterable<Entity> = ctx.world.getLevel().getAllEntities();
            val portalsinambit: ArrayList<Entity> = ArrayList()
                for (Entity in prtEnt) {
                    if ((Entity.getEyePosition().distanceToSqr(ctx.caster.position()) <= 32.0 * 32.0)
                        && Portal.entityType == Entity.getType()
                    ) {
                        portalsinambit.add(Entity)
                    }
                }
            return portalsinambit
        }
    }
}