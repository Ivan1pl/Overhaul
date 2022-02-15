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

package com.ivan1pl.overhaul.entities.effects;

import com.ivan1pl.overhaul.Overhaul;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

/**
 * Custom status effects.
 */
public class StatusEffects {
    public static final StatusEffect GLIDING = register("gliding", new GlidingStatusEffect());
    public static final StatusEffect STICKINESS = register("stickiness", new StickinessStatusEffect());

    /**
     * Register a status effect in the status effect registry.
     * @param name identifier path
     * @param statusEffect status effect to register
     * @return registered status effect
     */
    private static StatusEffect register(String name, StatusEffect statusEffect) {
        return Registry.register(Registry.STATUS_EFFECT, new Identifier(Overhaul.ID, name), statusEffect);
    }
}
