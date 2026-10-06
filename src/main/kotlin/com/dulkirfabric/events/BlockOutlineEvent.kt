package com.dulkirfabric.events

import com.dulkirfabric.events.base.CancellableEvent
import net.fabricmc.fabric.api.client.rendering.v1.level.LevelRenderContext
import net.minecraft.client.renderer.state.BlockOutlineRenderState

data class BlockOutlineEvent(
    val worldRenderContext: LevelRenderContext,
    val blockOutlineContext: BlockOutlineRenderState
): CancellableEvent()
