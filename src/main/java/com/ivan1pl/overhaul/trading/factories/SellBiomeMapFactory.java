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

package com.ivan1pl.overhaul.trading.factories;

import net.minecraft.entity.Entity;
import net.minecraft.item.FilledMapItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.map.MapIcon;
import net.minecraft.item.map.MapState;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.math.BlockPos;
import net.minecraft.village.TradeOffer;
import net.minecraft.village.TradeOffers;

import java.util.Random;

/**
 * Trade offer factory for maps leading to random biomes.
 */
public class SellBiomeMapFactory implements TradeOffers.Factory {
    private final int price;
    private final MapIcon.Type iconType;
    private final int maxUses;
    private final int experience;

    /**
     * Create factory instance.
     * @param price map price
     * @param iconType icon to be used on the map at target coordinates
     * @param maxUses times the trade can be used
     * @param experience experience gained from using the trade
     */
    public SellBiomeMapFactory(int price, MapIcon.Type iconType, int maxUses, int experience) {
        this.price = price;
        this.iconType = iconType;
        this.maxUses = maxUses;
        this.experience = experience;
    }

    /**
     * Create trade offer.
     * @param entity entity for which the trade should be created
     * @param random random
     * @return new trade offer, or {@code null} if offer creation failed
     */
    @Override
    public TradeOffer create(Entity entity, Random random) {
        if (!(entity.world instanceof ServerWorld serverWorld)) {
            return null;
        }
        BlockPos blockPos = getRandomPosition(serverWorld, entity.getBlockPos(), random);
        String translationKey = serverWorld.getBiomeKey(blockPos)
                .map(b -> "biome." + b.getValue().getNamespace() + "." + b.getValue().getPath()).orElseThrow();
        ItemStack itemStack = FilledMapItem.createMap(serverWorld, blockPos.getX(), blockPos.getZ(), (byte)2, true, true);
        FilledMapItem.fillExplorationMap(serverWorld, itemStack);
        MapState.addDecorationsNbt(itemStack, blockPos, "+", this.iconType);
        itemStack.setCustomName(new TranslatableText(translationKey));
        return new TradeOffer(new ItemStack(Items.EMERALD, this.price), itemStack, this.maxUses, this.experience, 0.2f);
    }

    /**
     * Get random position within the radius the map can point to.
     * @param world world
     * @param origin origin
     * @param random random
     * @return random map target position
     */
    private BlockPos getRandomPosition(ServerWorld world, BlockPos origin, Random random) {
        int radius = 6400;
        int blockCheckInterval = 64;
        int steps = 2 * radius / blockCheckInterval;
        int stepsY = (world.getTopY() - world.getBottomY() - 2) / blockCheckInterval;
        int x = origin.getX() - radius + random.nextInt(steps + 1) * blockCheckInterval;
        int y = world.getBottomY() + 1 + random.nextInt(stepsY + 1) * blockCheckInterval;
        int z = origin.getZ() - radius + random.nextInt(steps + 1) * blockCheckInterval;
        return new BlockPos(x, y, z);
    }
}
