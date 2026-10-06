/*
 * Vendored from Jarvis (https://github.com/nea89o/jarvis), LGPL-3.0-or-later, see LICENSE_jarvis.
 * Replaces Jarvis' own Fabric entrypoint so DulkirMod can ship it on 26.1+, where no Jarvis release exists.
 */
package com.dulkirfabric.jarvis;

import com.dulkirfabric.jarvis.api.JarvisPlugin;
import com.dulkirfabric.jarvis.impl.JarvisContainer;
import com.dulkirfabric.jarvis.impl.LoaderSupport;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keymapping.v1.KeyMappingHelper;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.KeyMapping;
import net.minecraft.network.chat.Component;

import java.util.List;
import java.util.Optional;

public final class JarvisBootstrap {
    private static JarvisContainer container;

    private JarvisBootstrap() {
    }

    public static void init() {
        if (container != null) return;
        List<JarvisPlugin> plugins = FabricLoader.getInstance().getEntrypoints("dulkir-jarvis", JarvisPlugin.class);
        container = JarvisContainer.init(new LoaderSupport() {
            @Override
            public Optional<Component> getModName(String modid) {
                return FabricLoader.getInstance().getModContainer(modid)
                    .map(it -> Component.literal(it.getMetadata().getName()));
            }
        });
        container.plugins.addAll(plugins);
        KeyMapping hudKey = KeyMappingHelper.registerKeyMapping(container.hudKeyBinding);
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (hudKey.consumeClick()) {
                container.hudKeyBindingPressed();
            }
        });
        container.finishLoading();
        ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> container.registerCommands(dispatcher));
    }
}
