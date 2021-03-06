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

package com.ivan1pl.overhaul.blocks;

import com.ivan1pl.overhaul.Overhaul;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

/**
 * Custom blocks.
 */
public class Blocks {
    public static final Block HOURGLASS = register("hourglass", new HourglassBlock(AbstractBlock.Settings.of(Material.GLASS).strength(2.0f).sounds(BlockSoundGroup.GLASS).requiresTool().nonOpaque()));
    public static final Block SAW = register("saw", new SawBlock(AbstractBlock.Settings.of(Material.WOOD).strength(2.5f).sounds(BlockSoundGroup.WOOD).requiresTool()));

    /**
     * Register a block in the block registry.
     * @param name identifier path
     * @param block block to register
     * @return registered block
     */
    private static Block register(String name, Block block) {
        return Registry.register(Registry.BLOCK, new Identifier(Overhaul.ID, name), block);
    }

    /**
     * Empty static method to force class initialization before vanilla blocks.
     */
    public static void init() {}
}
