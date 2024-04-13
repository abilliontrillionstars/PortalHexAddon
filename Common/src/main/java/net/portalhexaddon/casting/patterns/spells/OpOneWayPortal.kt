package net.portalhexaddon.casting.patterns.spells

import at.petrak.hexcasting.api.misc.MediaConstants
import at.petrak.hexcasting.api.spell.*
import at.petrak.hexcasting.api.spell.casting.CastingContext
import at.petrak.hexcasting.api.spell.iota.Iota
import com.mojang.math.Vector3f
import net.minecraft.world.phys.Vec3
import net.portalhexaddon.portals.MakePortalNgon
import qouteall.imm_ptl.core.api.PortalAPI
import qouteall.imm_ptl.core.portal.Portal

class OpOneWayPortal : SpellAction {
    override val argc: Int = 4
    /**
     * The method called when this Action is actually executed. Accepts the [args]
     * that were on the stack (there will be [argc] of them), and the [ctx],
     * which contains things like references to the caster, the ServerLevel,
     * methods to determine whether locations and entities are in ambit, etc.
     * Returns the list of iotas that should be added back to the stack as the
     * result of this action executing.
     */
    override fun execute(args: List<Iota>, ctx: CastingContext): Triple<RenderedSpell, Int, List<ParticleSpray>> {
        val PrtPos: Vec3 = args.getVec3(0,argc)
        val PrtPosOut: Vec3 = args.getVec3(1,argc)
        val PrtRot: Vec3 = args.getVec3(2,argc)
        val PrtSize: Double = args.getDoubleBetween(3,1.0/10.0,10.0,argc)

        val cost = (16 * MediaConstants.CRYSTAL_UNIT * PrtSize).toInt()

        val PrtPos3f = Vector3f(PrtPos.x.toFloat(), PrtPos.y.toFloat(), PrtPos.z.toFloat())

        ctx.assertVecInRange(PrtPos)
        ctx.assertVecInRange(PrtPosOut)

        return Triple(
            Spell(PrtPos3f,PrtPosOut,PrtRot,PrtSize),
            cost,
            listOf(ParticleSpray.burst(ctx.caster.position(), 1.0))
        )

    }

    private data class Spell(val PrtPos: Vector3f, val PrtPosOut: Vec3, val PrtRot: Vec3, val PrtSize: Double) : RenderedSpell {
        override fun cast(ctx: CastingContext) {
            val portal: Portal? = Portal.entityType.create(ctx.world)
            var PrtRotCors: Vec3 = PrtRot.cross(Vec3(0.0, 1.0, 0.0))
            var PrtRotCorsCors = Vec3(0.0,0.0,0.0)

            if (1.0 == PrtRot.y) {
                PrtRotCors = Vec3(0.0,0.0,1.0)
                PrtRotCorsCors = Vec3(1.0,0.0,0.0)
            } else if (-1.0 == PrtRot.y) {
                PrtRotCors = Vec3(1.0,0.0,0.0)
                PrtRotCorsCors = Vec3(0.0,0.0,-1.0)
            } else {
                PrtRotCorsCors = PrtRotCors.cross(PrtRot)
            }

            portal!!.originPos = Vec3(PrtPos)
            portal.setDestinationDimension(ctx.world.dimension())
            portal.setDestination(PrtPosOut)
            portal.setOrientationAndSize(
                PrtRotCors,
                PrtRotCorsCors,
                PrtSize,
                PrtSize
            )
            MakePortalNgon.makePortalNGon(portal,6)

            val portal2 = PortalAPI.createFlippedPortal(portal)

            portal.level.addFreshEntity(portal2)
            portal.level.addFreshEntity(portal)
        }
    }
}
