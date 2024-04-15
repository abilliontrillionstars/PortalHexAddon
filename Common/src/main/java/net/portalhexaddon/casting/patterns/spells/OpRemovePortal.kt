package net.portalhexaddon.casting.patterns.spells

import at.petrak.hexcasting.api.misc.MediaConstants
import at.petrak.hexcasting.api.spell.*
import at.petrak.hexcasting.api.spell.casting.CastingContext
import at.petrak.hexcasting.api.spell.iota.Iota
import at.petrak.hexcasting.api.spell.mishaps.MishapBadEntity
import com.samsthenerd.hexgloop.casting.ContextModificationHandlers
import com.samsthenerd.hexgloop.loot.GloopLoot
import com.samsthenerd.hexgloop.utils.GloopUtils
import com.samsthenerd.hexgloop.utils.GloopyRenderUtils
import net.minecraft.network.chat.Component
import net.minecraft.world.entity.Entity
import qouteall.imm_ptl.core.portal.Portal
import qouteall.imm_ptl.core.portal.PortalExtension
import qouteall.imm_ptl.core.portal.PortalLike
import qouteall.imm_ptl.core.portal.PortalManipulation
import qouteall.imm_ptl.core.portal.PortalState

class OpRemovePortal : SpellAction {
    /**
     * The number of arguments from the stack that this action requires.
     */
    override val argc: Int = 1
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
        val Prt: Entity = args.getEntity(0,argc)

        //ctx.isEntityInRange(Prt)

        //if (Prt != Portal.entityType) {
        //    throw MishapBadEntity(Prt, Component.translatable("Portal"))
        //} else {
        //}

        return Triple(
            Spell(Prt),
            0,
            listOf(ParticleSpray.burst(ctx.caster.position(), 1.0))
        )
    }

    private data class Spell(val prt: Entity) : RenderedSpell {
        override fun cast(ctx: CastingContext) {

        }
    }
}
