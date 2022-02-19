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

package com.ivan1pl.overhaul.recipes;

import com.ivan1pl.overhaul.blocks.Blocks;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.CuttingRecipe;
import net.minecraft.recipe.Ingredient;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

/**
 * Sawing recipe.
 */
public class SawingRecipe extends CuttingRecipe {
    /**
     * Constructor.
     * @param id identifier
     * @param group group
     * @param input input
     * @param output output
     */
    public SawingRecipe(Identifier id, String group, Ingredient input, ItemStack output) {
        super(RecipeTypes.SAWING, RecipeSerializers.SAWING, id, group, input, output);
    }

    /**
     * Check if the recipe matches current inventory.
     * @param inventory inventory
     * @param world world
     * @return whether the recipe matches current inventory
     */
    @Override
    public boolean matches(Inventory inventory, World world) {
        return this.input.test(inventory.getStack(0));
    }

    /**
     * Create icon.
     * @return saw item
     */
    @Override
    public ItemStack createIcon() {
        return new ItemStack(Blocks.SAW);
    }

    /**
     * Sawing recipe serializer.
     */
    public static class Serializer extends CuttingRecipe.Serializer<SawingRecipe> {
        /**
         * Create serializer instance.
         */
        protected Serializer() {
            super(SawingRecipe::new);
        }
    }
}
