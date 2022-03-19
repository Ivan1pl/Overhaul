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

import com.ivan1pl.overhaul.Overhaul;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

/**
 * Custom recipe types.
 */
public class RecipeTypes {
    public static final RecipeType<SawingRecipe> SAWING = register("sawing");

    /**
     * Register recipe type.
     * @param name identifier path
     * @return registered recipe type
     */
    private static <T extends Recipe<?>> RecipeType<T> register(String name) {
        return Registry.register(Registry.RECIPE_TYPE, new Identifier(Overhaul.ID, name), new RecipeType<T>(){
            public String toString() {
                return name;
            }
        });
    }

    /**
     * Empty static method to force class initialization.
     */
    public static void init() {}
}
