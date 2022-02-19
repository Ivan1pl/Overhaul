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

package com.ivan1pl.overhaul.mixin.server;

import com.ivan1pl.overhaul.blocks.Blocks;
import net.minecraft.data.server.BlockLootTableGenerator;
import net.minecraft.loot.LootTable;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.BiConsumer;

/**
 * Additional functionality to block loot table generator.
 */
@Mixin(BlockLootTableGenerator.class)
public class BlockLootTableGeneratorMixin {
    /**
     * Add custom block drop.
     * @param biConsumer loot table builder consumer
     * @param callbackInfo callback info
     */
    @Inject(method = "Lnet/minecraft/data/server/BlockLootTableGenerator;accept(Ljava/util/function/BiConsumer;)V",
            at = @At("HEAD"))
    private void acceptCustom(BiConsumer<Identifier, LootTable.Builder> biConsumer, CallbackInfo callbackInfo) {
        thisObject().addDrop(Blocks.SAW, BlockLootTableGenerator::nameableContainerDrops);
    }

    /**
     * Get {@code this} instance from the mixin target.
     * @return {@code this} instance in the mixin target
     */
    private BlockLootTableGenerator thisObject() {
        return (BlockLootTableGenerator) (Object) this;
    }
}
