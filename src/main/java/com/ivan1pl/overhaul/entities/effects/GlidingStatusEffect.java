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

import net.fabricmc.fabric.api.entity.event.v1.EntityElytraEvents;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.server.network.ServerPlayerEntity;

/**
 * Custom status effect simulating elytra flight.
 */
public class GlidingStatusEffect extends StatusEffect {
    static {
        EntityElytraEvents.CUSTOM.register(((entity, tickElytra) ->
                entity instanceof ServerPlayerEntity player && player.hasStatusEffect(StatusEffects.GLIDING)));
    }

    /**
     * Default constructor.
     */
    protected GlidingStatusEffect() {
        super(StatusEffectCategory.BENEFICIAL, 0xCECECE);
    }

    /**
     * Start flight when player is in the air.
     * @param entity player
     * @param amplifier ignored
     */
    @Override
    public void applyUpdateEffect(LivingEntity entity, int amplifier) {
        if (entity instanceof ServerPlayerEntity player && !entity.isSpectator()) {
            if (!player.isFallFlying() && !player.isOnGround() && !player.isTouchingWater() && !player.isInLava() &&
                    !player.isClimbing() && !player.hasVehicle() && !player.getAbilities().flying) {
                player.startFallFlying();
            }
        }
    }

    /**
     * Check if the update effect can be applied.
     * @param duration ignored
     * @param amplifier ignored
     * @return always {@code true}
     */
    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true;
    }
}
