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

package com.ivan1pl.overhaul.initializers;

import com.ivan1pl.overhaul.items.Items;
import net.fabricmc.fabric.api.loot.v1.FabricLootPoolBuilder;
import net.fabricmc.fabric.api.loot.v1.event.LootTableLoadingCallback;
import net.minecraft.entity.EntityType;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.function.LootingEnchantLootFunction;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.loot.provider.number.UniformLootNumberProvider;

/**
 * Initializer for custom loot tables.
 */
public class LootTablesInitializer {
    private static final FabricLootPoolBuilder BAT_LOOT = FabricLootPoolBuilder.builder().rolls(ConstantLootNumberProvider.create(1.0f)).with(ItemEntry.builder(Items.BAT_WING).apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(0.0f, 1.0f))).apply(LootingEnchantLootFunction.builder(UniformLootNumberProvider.create(0.0f, 1.0f))));

    /**
     * Initialize all custom loot tables.
     */
    public static void initialize() {
        register(EntityType.BAT, BAT_LOOT);
    }

    /**
     * Register loot table for entity type.
     * @param entityType entity type
     * @param poolBuilder loot pool builder
     */
    private static void register(EntityType<?> entityType, FabricLootPoolBuilder poolBuilder) {
        LootTableLoadingCallback.EVENT.register((resourceManager, lootManager, id, table, setter) -> {
            if (entityType.getLootTableId().equals(id)) {
                table.pool(poolBuilder);
            }
        });
    }
}
