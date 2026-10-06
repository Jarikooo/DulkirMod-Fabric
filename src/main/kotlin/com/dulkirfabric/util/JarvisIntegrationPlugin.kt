package com.dulkirfabric.util

import com.dulkirfabric.DulkirModFabric
import com.dulkirfabric.config.DulkirConfig
import com.dulkirfabric.jarvis.api.Jarvis
import com.dulkirfabric.jarvis.api.JarvisConfigOption
import com.dulkirfabric.jarvis.api.JarvisHud
import com.dulkirfabric.jarvis.api.JarvisPlugin

class JarvisIntegrationPlugin: JarvisPlugin {

    override fun getModId(): String =
        DulkirModFabric.MOD_ID

    companion object {
        lateinit var jarvis: Jarvis
    }

    override fun onInitialize(jarvis: Jarvis) {
        Companion.jarvis = jarvis
    }

    override fun getAllHuds(): List<JarvisHud> {
        return DulkirConfig.ConfigVars.huds.map { it -> it.first };
    }

    override fun onHudEditorClosed() {
        return DulkirConfig.ConfigVars.saveConfig()
    }

    override fun getAllConfigOptions(): List<JarvisConfigOption> {
        return listOf() // todo, dont understand the jumpTo function
    }

}