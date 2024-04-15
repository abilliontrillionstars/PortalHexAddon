package net.portalhexaddon.portals;

import com.samsthenerd.hexgloop.casting.ContextModificationHandlers.Modification;
import at.petrak.hexcasting.api.spell.casting.CastingContext;
import com.samsthenerd.hexgloop.blocks.HexGloopBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

import static com.samsthenerd.hexgloop.casting.ContextModificationHandlers.registerAmbitModifier;

public class PortalAmbit {
    public static Modification ambitModifier(CastingContext ctx, Vec3 pos, Boolean original){
        //ServerPlayer caster = ctx.getCaster();
        //if(!original && caster != null){
        //    BlockState state = caster.getLevel().getBlockState(new BlockPos(pos));
         //   if(state.getBlock() == HexGloopBlocks.CHARGED_AMETHYST_BLOCK.get())
                return Modification.ENABLE;
        //}
        //return Modification.NONE;
    }
    public static void init(){
        registerAmbitModifier(PortalAmbit::ambitModifier,0);
    }
}
