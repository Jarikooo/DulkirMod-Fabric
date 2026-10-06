package com.dulkirfabric.events

import com.dulkirfabric.events.base.Event
import net.minecraft.client.gui.GuiGraphicsExtractor

data class HudRenderEvent(
    val context: GuiGraphicsExtractor,
    val delta: Float
): Event()
