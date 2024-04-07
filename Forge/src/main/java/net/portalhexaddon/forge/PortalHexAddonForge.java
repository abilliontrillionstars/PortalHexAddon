package net.portalhexaddon.forge;

import dev.architectury.platform.forge.EventBuses;
import net.portalhexaddon.PortalHexAddon;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

/**
 * This is your loading entrypoint on forge, in case you need to initialize
 * something platform-specific.
 */
@Mod(PortalHexAddon.MOD_ID)
public class PortalHexAddonForge {
    public PortalHexAddonForge() {
        // Submit our event bus to let architectury register our content on the right time
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        EventBuses.registerModEventBus(PortalHexAddon.MOD_ID, bus);
        bus.addListener(PortalHexAddonClientForge::init);
        PortalHexAddon.init();
    }
}
