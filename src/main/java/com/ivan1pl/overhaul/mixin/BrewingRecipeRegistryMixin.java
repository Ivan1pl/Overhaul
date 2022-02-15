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

package com.ivan1pl.overhaul.mixin;

import com.ivan1pl.overhaul.items.Items;
import com.ivan1pl.overhaul.potions.Potions;
import net.minecraft.item.Item;
import net.minecraft.potion.Potion;
import net.minecraft.recipe.BrewingRecipeRegistry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Additional functionalities to brewing recipe registry.
 */
@Mixin(BrewingRecipeRegistry.class)
public class BrewingRecipeRegistryMixin {
    /**
     * Register custom brewing recipes.
     * @param callbackInfo callback info
     */
    @Inject(method = "Lnet/minecraft/recipe/BrewingRecipeRegistry;registerDefaults()V", at = @At("TAIL"))
    private static void registerCustom(CallbackInfo callbackInfo) {
        registerCustomPotionRecipe(net.minecraft.potion.Potions.AWKWARD, Items.BAT_WING, Potions.GLIDING);
        registerCustomPotionRecipe(Potions.GLIDING, net.minecraft.item.Items.REDSTONE, Potions.LONG_GLIDING);
        registerCustomPotionRecipe(Potions.GLIDING, net.minecraft.item.Items.FERMENTED_SPIDER_EYE, Potions.STICKINESS);
        registerCustomPotionRecipe(Potions.LONG_GLIDING, net.minecraft.item.Items.FERMENTED_SPIDER_EYE, Potions.LONG_STICKINESS);
        registerCustomPotionRecipe(Potions.STICKINESS, net.minecraft.item.Items.REDSTONE, Potions.LONG_STICKINESS);
        registerCustomPotionRecipe(Potions.STICKINESS, net.minecraft.item.Items.GLOWSTONE_DUST, Potions.STRONG_STICKINESS);
    }

    /**
     * Invoker for private {@code registerPotionRecipe} method.
     * @param input input potion
     * @param item brewing ingredient
     * @param output output potion
     */
    @Invoker("registerPotionRecipe")
    private static void registerCustomPotionRecipe(Potion input, Item item, Potion output) {}
}
