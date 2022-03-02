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

package com.ivan1pl.overhaul.blocks.entities;

import com.ivan1pl.overhaul.blocks.HourglassBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * Block entity for hourglass block.
 */
public class HourglassBlockEntity extends BlockEntity {
    private static final int TICKS_PER_STATE = 1500;
    private static final int MAX_TIME = 6000;
    private int time = MAX_TIME;

    /**
     * Constructor.
     * @param pos position
     * @param state state
     */
    public HourglassBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntityTypes.HOURGLASS_BLOCK_ENTITY, pos, state);
    }

    /**
     * Read NBT.
     * @param nbt NBT compound
     */
    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        time = nbt.getInt("time");
    }

    /**
     * Write NBT.
     * @param nbt NBT compound
     */
    @Override
    protected void writeNbt(NbtCompound nbt) {
        nbt.putInt("time", time);
        super.writeNbt(nbt);
    }

    /**
     * Reverse the hourglass.
     * @param world world
     * @param pos position
     * @param state state
     */
    public void reverse(World world, BlockPos pos, BlockState state) {
        time = MAX_TIME - time;
        updateState(world, pos, state, this);
    }

    /**
     * Calculate comparator output.
     * @return comparator output.
     */
    public int calculateComparatorOutput() {
        return (time + TICKS_PER_STATE - 1) / TICKS_PER_STATE;
    }

    /**
     * Runs every game tick.
     * @param world world
     * @param pos position
     * @param state state
     * @param hourglassBlockEntity block entity
     */
    public static void tick(World world, BlockPos pos, BlockState state, HourglassBlockEntity hourglassBlockEntity) {
        if (hourglassBlockEntity.time > 0) {
            --hourglassBlockEntity.time;
        }
        updateState(world, pos, state, hourglassBlockEntity);
    }

    /**
     * Update block state.
     * @param world world
     * @param pos position
     * @param state state
     * @param hourglassBlockEntity block entity
     */
    private static void updateState(World world, BlockPos pos, BlockState state,
                                    HourglassBlockEntity hourglassBlockEntity) {
        int timeState = (hourglassBlockEntity.time + TICKS_PER_STATE - 1) / TICKS_PER_STATE;
        if (timeState != state.get(HourglassBlock.TIME)) {
            state = state.with(HourglassBlock.TIME, timeState);
            world.setBlockState(pos, state, Block.NOTIFY_ALL);
            markDirty(world, pos, state);
        }
    }
}
