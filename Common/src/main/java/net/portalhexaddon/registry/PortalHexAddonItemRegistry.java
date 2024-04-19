package net.portalhexaddon.registry;

import at.petrak.hexcasting.common.items.ItemStaff;
import dev.architectury.registry.CreativeTabRegistry;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.portalhexaddon.PortalHexAddon;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.core.Registry;

import static net.portalhexaddon.PortalHexAddon.id;

public class PortalHexAddonItemRegistry {
    // Register items through this
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(PortalHexAddon.MOD_ID, Registry.ITEM_REGISTRY);

    public static void init() {
        ITEMS.register();
    }

    // A new creative tab. Notice how it is one of the few things that are not deferred
    public static final CreativeModeTab PORTAL_GROUP = CreativeTabRegistry.create(id("portal_group"), () -> new ItemStack(PortalHexAddonItemRegistry.PORTAL_STAFF.get()));

    // During the loading phase, refrain from accessing suppliers' items (e.g. EXAMPLE_ITEM.get()), they will not be available
    public static final RegistrySupplier<Item> PORTAL_STAFF = ITEMS.register("portal_staff", () -> new ItemStaff(new Item.Properties().stacksTo(1).tab(PORTAL_GROUP)));

}
