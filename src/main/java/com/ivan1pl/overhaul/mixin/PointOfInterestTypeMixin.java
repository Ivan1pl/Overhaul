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

import com.ivan1pl.overhaul.villages.VillagerProfessions;
import net.minecraft.world.poi.PointOfInterestType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Additional functionality to point of interest.
 */
@Mixin(PointOfInterestType.class)
public class PointOfInterestTypeMixin {
    /**
     * Make sure custom professions are initialized.
     * @param callbackInfo callback info
     */
    @Inject(method = "<clinit>", at = @At("TAIL"))
    private static void postInit(CallbackInfo callbackInfo) {
        VillagerProfessions.init();
    }
}
