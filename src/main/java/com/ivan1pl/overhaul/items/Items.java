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

    /**
     * Register an item in the item registry.
     * @param name identifier path
     * @param item item to register
     * @return registered item
     */
    private static Item register(String name, Item item) {
        return Registry.register(Registry.ITEM, new Identifier(Overhaul.ID, name), item);
    }
}
