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

package com.ivan1pl.overhaul.world.poi;

import com.google.common.collect.ImmutableSet;
import com.ivan1pl.overhaul.blocks.Blocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.world.poi.PointOfInterestType;

import java.util.Set;

/**
 * Custom points of interest.
 */
public class PointsOfInterest {
    public static final PointOfInterestType CARPENTER = PointOfInterestType.register("carpenter", getAllStatesOf(Blocks.SAW), 1, 1);

    /**
     * Get all states of a block.
     * @param block block
     * @return all states of given block
     */
    private static Set<BlockState> getAllStatesOf(Block block) {
        return ImmutableSet.copyOf(block.getStateManager().getStates());
    }
}
