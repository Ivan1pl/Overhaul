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

package com.ivan1pl.overhaul.items;

import com.ivan1pl.overhaul.Overhaul;
import com.ivan1pl.overhaul.blocks.Blocks;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

/**
 * Custom items.
 */
public class Items {
    public static final Item BAT_WING = register("bat_wing", new Item(new Item.Settings().group(ItemGroup.BREWING)
            .food(FoodComponents.BAT_WING)));
    public static final Item HOURGLASS = register(Blocks.HOURGLASS, ItemGroup.REDSTONE);
    public static final Item SAW = register(Blocks.SAW, ItemGroup.DECORATIONS);

    /**
     * Register an item in the item registry.
     * @param name identifier path
     * @param item item to register
     * @return registered item
     */
    private static Item register(String name, Item item) {
        return register(new Identifier(Overhaul.ID, name), item);
    }

    /**
     * Register an item in the item registry.
     * @param identifier item identifier
     * @param item item to register
     * @return registered item
     */
    private static Item register(Identifier identifier, Item item) {
        if (item instanceof BlockItem) {
            ((BlockItem)item).appendBlocks(Item.BLOCK_ITEMS, item);
        }
        return Registry.register(Registry.ITEM, identifier, item);
    }

    /**
     * Register a block item in the item registry.
     * @param block block for which the item should be created
     * @param group item group
     * @return registered item
     */
    private static Item register(Block block, ItemGroup group) {
        return register(new BlockItem(block, new Item.Settings().group(group)));
    }

    /**
     * Register a block item in the item registry.
     * @param item block item
     * @return registered item
     */
    private static Item register(BlockItem item) {
        return register(item.getBlock(), item);
    }

    /**
     * Register a block item in the item registry.
     * @param block block for which the item should be registered
     * @param item block item
     * @return registered item
     */
    protected static Item register(Block block, Item item) {
        return register(Registry.BLOCK.getId(block), item);
    }
}
