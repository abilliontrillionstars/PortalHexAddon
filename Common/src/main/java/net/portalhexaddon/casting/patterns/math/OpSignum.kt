package net.portalhexaddon.casting.patterns.math

import at.petrak.hexcasting.api.spell.*
import at.petrak.hexcasting.api.spell.casting.CastingContext
import at.petrak.hexcasting.api.spell.iota.Iota
import at.petrak.hexcasting.api.spell.mishaps.MishapBadBlock
import at.petrak.hexcasting.api.spell.mishaps.MishapBadEntity
import com.mojang.math.Vector3f
import net.minecraft.network.chat.Component
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.entity.Entity
import net.minecraft.world.phys.Vec3
import qouteall.imm_ptl.core.portal.Portal
import kotlin.math.abs
import net.minecraft.server.level.ServerLevel

class OpSignum : SpellAction {
    override val argc: Int = 2


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
        val PrtSize: Double = args.getDouble(1,argc)

        //ctx.assertVecInRange(PrtPos)
        val PrtPosT = Vector3f(PrtPos.x.toFloat(), PrtPos.y.toFloat(), PrtPos.z.toFloat())
        return Triple(
            Spell(PrtPosT,PrtSize),
            0,
            listOf(ParticleSpray.burst(ctx.caster.position(), 999.0))
        )

    }

    private data class Spell(val PrtPos: Vector3f, val PrtSize: Double) : RenderedSpell {
        override fun cast(ctx: CastingContext) {
            print("TESTING PORTAL FIRST")
            val level = ctx.world
            val portal: Portal? = Portal.entityType.create(level)
            portal!!.originPos = Vec3(PrtPos)
            portal.setDestinationDimension(ctx.world.dimension())
            portal.setDestination(Vec3(10.0,100.0,10.0))
            portal.setOrientationAndSize(Vec3(1.0,0.5,0.5),Vec3(0.0,1.0,0.0),PrtSize,PrtSize)
            portal.level.addFreshEntity(portal)
            print("TESTING PORTAL LAST")
            println(portal)
        }

    }
}