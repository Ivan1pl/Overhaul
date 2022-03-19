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

package com.ivan1pl.overhaul.mixin.client;

import com.ivan1pl.overhaul.blocks.Blocks;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.BlockStateVariant;
import net.minecraft.data.client.ModelIds;
import net.minecraft.data.client.VariantSettings;
import net.minecraft.data.client.VariantsBlockStateSupplier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Additional functionality to block state model generator.
 */
@Mixin(BlockStateModelGenerator.class)
public class BlockStateModelGeneratorMixin {
    /**
     * Register custom block state suppliers.
     * @param callbackInfo callback info
     */
    @Inject(method = "Lnet/minecraft/data/client/BlockStateModelGenerator;register()V", at = @At("TAIL"))
    private void registerCustom(CallbackInfo callbackInfo) {
        registerSaw();
    }

    /**
     * Register block state supplier for saw block.
     */
    private void registerSaw() {
        thisObject().blockStateCollector.accept(VariantsBlockStateSupplier.create(Blocks.SAW, BlockStateVariant.create().put(VariantSettings.MODEL, ModelIds.getBlockModelId(Blocks.SAW))).coordinate(BlockStateModelGenerator.createNorthDefaultHorizontalRotationStates()));
    }

    /**
     * Get {@code this} instance from the mixin target.
     * @return {@code this} instance in the mixin target
     */
    private BlockStateModelGenerator thisObject() {
        return (BlockStateModelGenerator) (Object) this;
    }
}
