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

import com.ivan1pl.overhaul.loot.LootTables;
import net.minecraft.data.server.GiftLootTableGenerator;
import net.minecraft.item.Items;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.BiConsumer;

/**
 * Additional functionality to gift loot table generator.
 */
@Mixin(GiftLootTableGenerator.class)
public class GiftLootTableGeneratorMixin {
    /**
     * Accept custom loot tables.
     * @param biConsumer loot table consumer
     * @param callbackInfo callback info
     */
    @Inject(method = "Lnet/minecraft/data/server/GiftLootTableGenerator;accept(Ljava/util/function/BiConsumer;)V",
            at = @At("TAIL"))
    private void acceptCustom(BiConsumer<Identifier, LootTable.Builder> biConsumer, CallbackInfo callbackInfo) {
        biConsumer.accept(LootTables.HERO_OF_THE_VILLAGE_CARPENTER_GIFT_GAMEPLAY,
                LootTable.builder().pool(LootPool.builder().rolls(ConstantLootNumberProvider.create(1.0f))
                        .with(ItemEntry.builder(Items.OAK_BOAT))));
    }
}
