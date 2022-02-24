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

package com.ivan1pl.overhaul.villages;

import com.ivan1pl.overhaul.Overhaul;
import com.ivan1pl.overhaul.interfaces.StructurePoolModifier;
import com.mojang.datafixers.util.Either;
import com.mojang.datafixers.util.Pair;
import net.minecraft.structure.pool.LegacySinglePoolElement;
import net.minecraft.structure.pool.StructurePool;
import net.minecraft.structure.processor.StructureProcessorList;
import net.minecraft.structure.processor.StructureProcessorLists;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.BuiltinRegistries;

import java.util.function.Function;

/**
 * Helper class to simplify adding custom structures to villages.
 */
public class VillageData {
    /**
     * Create projection to pool element function with mod id as identifier namespace.
     * @param id identifier path
     * @param processors processor
     * @return projection to pool element function
     */
    private static Function<StructurePool.Projection, LegacySinglePoolElement> of(String id,
                                                                                  StructureProcessorList processors) {
        return projection ->
                new LegacySinglePoolElement(Either.left(new Identifier(Overhaul.ID, id)), () -> processors, projection);
    }

    /**
     * Add custom house to village.
     * @param villageType village type
     * @param houseId house identifier
     * @param processors processors
     */
    public static void addCustomHouse(String villageType, String houseId, StructureProcessorList processors) {
        ((StructurePoolModifier) BuiltinRegistries.STRUCTURE_POOL
                .get(new Identifier("village/" + villageType + "/houses")))
                .add(Pair.of(of("village/" + villageType + "/houses/" + houseId, processors), 2),
                        StructurePool.Projection.RIGID);
    }

    /**
     * Add custom house to village.
     * @param villageType village type
     * @param houseId house identifier
     */
    public static void addCustomHouse(String villageType, String houseId) {
        addCustomHouse(villageType, houseId, StructureProcessorLists.EMPTY);
    }
}
