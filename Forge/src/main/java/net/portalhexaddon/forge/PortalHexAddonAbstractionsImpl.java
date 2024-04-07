package net.portalhexaddon.forge;

import net.portalhexaddon.PortalHexAddonAbstractions;
import net.minecraftforge.fml.loading.FMLPaths;

import java.nio.file.Path;

public class PortalHexAddonAbstractionsImpl {
    /**
     * This is the actual implementation of {@link PortalHexAddonAbstractions#getConfigDirectory()}.
     */
    public static Path getConfigDirectory() {
        return FMLPaths.CONFIGDIR.get();
    }
	
    public static void initPlatformSpecific() {
        PortalHexAddonConfigForge.init();
    }
}
