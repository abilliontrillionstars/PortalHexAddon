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
import qouteall.imm_ptl.core.portal.PortalManipulation
import qouteall.imm_ptl.core.portal.PortalManipulation.createFlippedPortal

class OpTwoWayPortal : SpellAction {
    /**
     * The number of arguments from the stack that this action requires.
     */
    override val argc: Int = 4
    val cost = 64 * MediaConstants.CRYSTAL_UNIT

    /**
     * The method called when this Action is actually executed. Accepts the [args]
     * that were on the stack (there will be [argc] of them), and the [ctx],
     * which contains things like references to the caster, the ServerLevel,
     * methods to determine whether locations and entities are in ambit, etc.
     * Returns a triple of things. The [RenderedSpell] is responsible for the spell actually
     * doing things in the world, the [Int] is how much media the spell should cost,
     * and the [List] of [ParticleSpray] renders particle effects for the result of the SpellAction.
     *
     * The [execute] method should only contain code to find the targets of the spell and validate
     * them. All the code that actually makes changes to the world (breaking blocks, teleporting things,
     * etc.) should be in the private [Spell] data class below.
     */
    override fun execute(args: List<Iota>, ctx: CastingContext): Triple<RenderedSpell, Int, List<ParticleSpray>> {
        val PrtPos: Vec3 = args.getVec3(0,argc)
        val PrtPosOut: Vec3 = args.getVec3(1,argc)
        val PrtRot: Vec3 = args.getVec3(2,argc)
        val PrtSize: Double = args.getDoubleBetween(3,1.0/10.0,10.0,argc)

        val PrtPos3f = Vector3f(PrtPos.x.toFloat(), PrtPos.y.toFloat(), PrtPos.z.toFloat())

        ctx.assertVecInRange(PrtPos)
        ctx.assertVecInRange(PrtPosOut)

        return Triple(
            Spell(PrtPos3f,PrtPosOut,PrtRot,PrtSize),
            0,
            listOf(ParticleSpray.burst(ctx.caster.position(), 1.0))
        )

    }

    private data class Spell(val PrtPos: Vector3f, val PrtPosOut: Vec3, val PrtRot: Vec3, val PrtSize: Double) : RenderedSpell {
        override fun cast(ctx: CastingContext) {
            val level = ctx.world
            val portal: Portal? = Portal.entityType.create(level)
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

            val portal2 = PortalAPI.createReversePortal(portal) //Reverse makes a portal at the output
            val portal3 = PortalAPI.createFlippedPortal(portal) //Flip rotates the portal
            val portal4 = PortalAPI.createFlippedPortal(portal2)

            portal.level.addFreshEntity(portal)
            portal.level.addFreshEntity(portal2)
            portal.level.addFreshEntity(portal3)
            portal.level.addFreshEntity(portal4)
        }
    }
}
