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

import com.ivan1pl.overhaul.interfaces.StructurePoolModifier;
import com.mojang.datafixers.util.Pair;
import net.minecraft.structure.pool.StructurePool;
import net.minecraft.structure.pool.StructurePoolElement;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/**
 * API which allows to modify existing structure pools.
 */
@Mixin(StructurePool.class)
public abstract class StructurePoolMixin implements StructurePoolModifier {
    /**
     * Element counts accessor.
     * @return value of {@code elementCounts}
     */
    @Accessor("elementCounts")
    protected abstract List<Pair<StructurePoolElement, Integer>> getElementCounts();

    /**
     * Elements accessor.
     * @return value of {@code elements}
     */
    @Accessor("elements")
    protected abstract List<StructurePoolElement> getElements();

    /**
     * Wrap {@code elementCounts} to make sure it is mutable.
     * @param list {@code elementCounts}
     * @return wrapped list
     */
    @ModifyVariable(method = "Lnet/minecraft/structure/pool/StructurePool;<init>(Lnet/minecraft/util/Identifier;Lnet/minecraft/util/Identifier;Ljava/util/List;)V",
            at = @At("HEAD"), ordinal = 0, argsOnly = true)
    private static List<Pair<StructurePoolElement, Integer>> wrap(List<Pair<StructurePoolElement, Integer>> list) {
        return new ArrayList<>(list);
    }

    /**
     * Add element to structure pool.
     * @param element element
     */
    @Override
    public void add(Pair<StructurePoolElement, Integer> element) {
        StructurePoolElement structurePoolElement = element.getFirst();
        for (int i = 0; i < element.getSecond(); ++i) {
            getElements().add(structurePoolElement);
        }
    }

    /**
     * Add element to structure pool.
     * @param element element
     * @param projection projection
     */
    @Override
    public void add(Pair<Function<StructurePool.Projection, ? extends StructurePoolElement>, Integer> element,
                    StructurePool.Projection projection) {
        StructurePoolElement structurePoolElement = element.getFirst().apply(projection);
        getElementCounts().add(Pair.of(structurePoolElement, element.getSecond()));
        for (int i = 0; i < element.getSecond(); ++i) {
            getElements().add(structurePoolElement);
        }
    }
}
