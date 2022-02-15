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

package com.ivan1pl.overhaul.potions;

import com.ivan1pl.overhaul.Overhaul;
import com.ivan1pl.overhaul.entities.effects.StatusEffects;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.potion.Potion;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

/**
 * Custom potions.
 */
public class Potions {
    public static final Potion GLIDING = register("gliding", new Potion(new StatusEffectInstance(StatusEffects.GLIDING, 3600)));
    public static final Potion LONG_GLIDING = register("long_gliding", new Potion("gliding", new StatusEffectInstance(StatusEffects.GLIDING, 9600)));
    public static final Potion STICKINESS = register("stickiness", new Potion(new StatusEffectInstance(StatusEffects.STICKINESS, 3600)));
    public static final Potion LONG_STICKINESS = register("long_stickiness", new Potion("stickiness", new StatusEffectInstance(StatusEffects.STICKINESS, 9600)));
    public static final Potion STRONG_STICKINESS = register("strong_stickiness", new Potion("stickiness", new StatusEffectInstance(StatusEffects.STICKINESS, 1800, 1)));

    /**
     * Register a potion in the potions registry.
     * @param name identifier path
     * @param potion potion to register
     * @return registered potion
     */
    private static Potion register(String name, Potion potion) {
        return Registry.register(Registry.POTION, new Identifier(Overhaul.ID, name), potion);
    }
}
