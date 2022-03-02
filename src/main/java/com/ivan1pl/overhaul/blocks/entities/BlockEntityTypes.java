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

package com.ivan1pl.overhaul.blocks.entities;

import com.ivan1pl.overhaul.Overhaul;
import com.ivan1pl.overhaul.blocks.Blocks;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

/**
 * Custom block entity types.
 */
public class BlockEntityTypes {
    public static BlockEntityType<HourglassBlockEntity> HOURGLASS_BLOCK_ENTITY = register("hourglass_block_entity", Blocks.HOURGLASS, HourglassBlockEntity::new);

    /**
     * Register custom block entity type.
     * @param name identifier path
     * @param block block
     * @param factory block entity factory
     * @return registered entity type
     */
    private static <T extends BlockEntity> BlockEntityType<T> register(String name, Block block,
                                                                       FabricBlockEntityTypeBuilder.Factory<T> factory) {
        return Registry.register(Registry.BLOCK_ENTITY_TYPE, new Identifier(Overhaul.ID, name),
                FabricBlockEntityTypeBuilder.create(factory, block).build(null));
    }

    /**
     * Empty static method to force class initialization.
     */
    public static void initialize() {}
}
