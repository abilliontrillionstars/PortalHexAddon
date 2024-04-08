package net.portalhexaddon.casting.patterns.math

import at.petrak.hexcasting.api.spell.*
import at.petrak.hexcasting.api.spell.casting.CastingContext
import at.petrak.hexcasting.api.spell.iota.Iota
import at.petrak.hexcasting.api.spell.mishaps.MishapBadEntity
import net.minecraft.network.chat.Component
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.entity.Entity
import kotlin.math.abs

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
        val playe: Entity = args.getEntity(0, argc)
        val numb: Double = args.getDouble(1,argc)

        ctx.assertEntityInRange(playe)
        if (playe !is ServerPlayer) throw MishapBadEntity(
            playe,
            Component.translatable("text.portalhexaddon.congrats.player")
        )

        return Triple(
            Spell(playe,numb),
            0,
            listOf(ParticleSpray.burst(playe.position(), 1.0))

        )
    }

    private data class Spell(val player: ServerPlayer, val numb: Double) : RenderedSpell {
        override fun cast(ctx: CastingContext) {
            player.sendSystemMessage(Component.translatable("text.portalhexaddon.congrats", player.displayName))
            abs(numb).asActionResult
        }
    }
}