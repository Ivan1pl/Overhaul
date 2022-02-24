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

package com.ivan1pl.overhaul.interfaces;

import com.mojang.datafixers.util.Pair;
import net.minecraft.structure.pool.StructurePool;
import net.minecraft.structure.pool.StructurePoolElement;

import java.util.function.Function;

/**
 * Interface describing additional methods to {@code StructurePool} class, added with a mixin.
 */
public interface StructurePoolModifier {
    /**
     * Add element to structure pool.
     * @param element element
     */
    void add(Pair<StructurePoolElement, Integer> element);

    /**
     * Add element to structure pool.
     * @param element element
     * @param projection projection
     */
    void add(Pair<Function<StructurePool.Projection, ? extends StructurePoolElement>, Integer> element,
             StructurePool.Projection projection);
}
