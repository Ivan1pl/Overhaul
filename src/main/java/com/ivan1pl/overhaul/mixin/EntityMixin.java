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

import com.ivan1pl.overhaul.entities.effects.StatusEffects;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Additional functionality to all entities.
 */
@Mixin(Entity.class)
public class EntityMixin {
    /**
     * Decrease jump velocity if {@link StatusEffects#STICKINESS} status effect is present.
     * @param callbackInfo callback info
     */
    @Inject(method = "Lnet/minecraft/entity/Entity;getJumpVelocityMultiplier()F", at = @At("RETURN"),
            cancellable = true)
    private void applyStickiness(CallbackInfoReturnable<Float> callbackInfo) {
        Entity entity = thisObject();
        if (entity instanceof LivingEntity livingEntity && livingEntity.hasStatusEffect(StatusEffects.STICKINESS)) {
            callbackInfo.setReturnValue(callbackInfo.getReturnValueF() /
                    (1.5f + 0.5f * (float) livingEntity.getStatusEffect(StatusEffects.STICKINESS).getAmplifier()));
        }
    }

    /**
     * Get {@code this} instance from the mixin target.
     * @return {@code this} instance in the mixin target
     */
    private Entity thisObject() {
        return (Entity) (Object) this;
    }
}
