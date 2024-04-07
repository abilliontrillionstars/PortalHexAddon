package net.portalhexaddon.forge;

import net.portalhexaddon.PortalHexAddonClient;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

/**
 * Forge client loading entrypoint.
 */
public class PortalHexAddonClientForge {
    public static void init(FMLClientSetupEvent event) {
        PortalHexAddonClient.init();
    }
}
