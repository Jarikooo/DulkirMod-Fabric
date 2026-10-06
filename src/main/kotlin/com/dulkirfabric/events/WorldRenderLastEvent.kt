package com.dulkirfabric.events

import com.dulkirfabric.events.base.Event
import net.fabricmc.fabric.api.client.rendering.v1.level.LevelRenderContext

data class WorldRenderLastEvent(val context: LevelRenderContext): Event()
