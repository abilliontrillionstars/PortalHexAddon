package net.portalhexaddon.fabric;

import net.fabricmc.loader.api.FabricLoader;
import net.portalhexaddon.PortalHexAddonAbstractions;

import java.nio.file.Path;

public class PortalHexAddonAbstractionsImpl {
    /**
     * This is the actual implementation of {@link PortalHexAddonAbstractions#getConfigDirectory()}.
     */
    public static Path getConfigDirectory() {
        return FabricLoader.getInstance().getConfigDir();
    }
	
    public static void initPlatformSpecific() {
        PortalHexAddonConfigFabric.init();
    }
}
