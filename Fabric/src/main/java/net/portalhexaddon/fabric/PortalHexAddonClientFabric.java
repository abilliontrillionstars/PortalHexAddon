package net.portalhexaddon.fabric;

import net.fabricmc.api.ClientModInitializer;
import net.portalhexaddon.PortalHexAddonClient;

/**
 * Fabric client loading entrypoint.
 */
public class PortalHexAddonClientFabric implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        PortalHexAddonClient.init();
    }
}
