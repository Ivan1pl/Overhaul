/*
 * Copyright 2022 Ivan1
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.ivan1pl.overhaul;

import com.ivan1pl.overhaul.blocks.Blocks;
import com.ivan1pl.overhaul.client.gui.screens.HandledScreens;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.client.render.RenderLayer;

public class OverhaulClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        Overhaul.LOGGER.info("Initializing Overhaul client...");
        BlockRenderLayerMap.INSTANCE.putBlock(Blocks.SAW, RenderLayer.getCutout());
        HandledScreens.initialize();
        Overhaul.LOGGER.info("Initialized Overhaul client!");
    }
}
