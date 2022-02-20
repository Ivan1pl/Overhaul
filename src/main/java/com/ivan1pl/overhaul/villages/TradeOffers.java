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

package com.ivan1pl.overhaul.villages;

import com.google.common.collect.ImmutableMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.minecraft.item.Items;
import net.minecraft.village.VillagerProfession;

/**
 * Custom trade offers.
 */
public class TradeOffers {
    private static final Int2ObjectMap<net.minecraft.village.TradeOffers.Factory[]> CARPENTER_OFFERS = register(
            VillagerProfessions.CARPENTER,
            ImmutableMap.of(
                    1, new net.minecraft.village.TradeOffers.Factory[]{
                            new net.minecraft.village.TradeOffers.BuyForOneEmeraldFactory(Items.ACACIA_PLANKS, 16, 16, 2),
                            new net.minecraft.village.TradeOffers.BuyForOneEmeraldFactory(Items.BIRCH_PLANKS, 16, 16, 2),
                            new net.minecraft.village.TradeOffers.BuyForOneEmeraldFactory(Items.DARK_OAK_PLANKS, 16, 16, 2),
                            new net.minecraft.village.TradeOffers.BuyForOneEmeraldFactory(Items.JUNGLE_PLANKS, 16, 16, 2),
                            new net.minecraft.village.TradeOffers.BuyForOneEmeraldFactory(Items.OAK_PLANKS, 16, 16, 2),
                            new net.minecraft.village.TradeOffers.BuyForOneEmeraldFactory(Items.SPRUCE_PLANKS, 16, 16, 2),
                            new net.minecraft.village.TradeOffers.SellItemFactory(Items.WOODEN_AXE, 1, 1, 1)},
                    2, new net.minecraft.village.TradeOffers.Factory[]{
                            new net.minecraft.village.TradeOffers.BuyForOneEmeraldFactory(Items.PAPER, 32, 16, 10),
                            new net.minecraft.village.TradeOffers.BuyForOneEmeraldFactory(Items.STRING, 32, 16, 10),
                            new net.minecraft.village.TradeOffers.SellItemFactory(Items.CARTOGRAPHY_TABLE, 4, 2, 16, 5),
                            new net.minecraft.village.TradeOffers.SellItemFactory(Items.BOWL, 4, 16, 16, 5)},
                    3, new net.minecraft.village.TradeOffers.Factory[]{
                            new net.minecraft.village.TradeOffers.BuyForOneEmeraldFactory(Items.FLINT, 32, 16, 20),
                            new net.minecraft.village.TradeOffers.SellItemFactory(Items.FLETCHING_TABLE, 4, 2, 16, 10),
                            new net.minecraft.village.TradeOffers.SellItemFactory(Items.LOOM, 4, 2, 16, 10),
                            new net.minecraft.village.TradeOffers.SellItemFactory(Items.LECTERN, 4, 2, 16, 10)},
                    4, new net.minecraft.village.TradeOffers.Factory[]{
                            new net.minecraft.village.TradeOffers.BuyForOneEmeraldFactory(Items.REDSTONE, 16, 16, 30),
                            new net.minecraft.village.TradeOffers.SellItemFactory(Items.NOTE_BLOCK, 4, 2, 16, 15),
                            new net.minecraft.village.TradeOffers.SellItemFactory(Items.BOOKSHELF, 4, 2, 16, 15)},
                    5, new net.minecraft.village.TradeOffers.Factory[]{
                            new net.minecraft.village.TradeOffers.BuyForOneEmeraldFactory(Items.IRON_INGOT, 16, 16, 30),
                            new net.minecraft.village.TradeOffers.BuyForOneEmeraldFactory(Items.DIAMOND, 1, 16, 30),
                            new net.minecraft.village.TradeOffers.SellItemFactory(Items.SMITHING_TABLE, 4, 2, 16, 15),
                            new net.minecraft.village.TradeOffers.SellItemFactory(Items.JUKEBOX, 4, 1, 16, 15)}));

    /**
     * Register trade offers.
     * @param profession profession
     * @param offers offer factories
     * @return registered offers
     */
    private static Int2ObjectMap<net.minecraft.village.TradeOffers.Factory[]> register(
            VillagerProfession profession,
            ImmutableMap<Integer, net.minecraft.village.TradeOffers.Factory[]> offers) {
        Int2ObjectMap<net.minecraft.village.TradeOffers.Factory[]> result = new Int2ObjectOpenHashMap<>(offers);
        net.minecraft.village.TradeOffers.PROFESSION_TO_LEVELED_TRADE.put(profession, result);
        return result;
    }

    /**
     * Empty static method to force class initialization.
     */
    public static void initialize() {}
}
