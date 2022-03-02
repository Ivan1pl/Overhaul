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

package com.ivan1pl.overhaul.blocks;

import com.ivan1pl.overhaul.blocks.entities.BlockEntityTypes;
import com.ivan1pl.overhaul.blocks.entities.HourglassBlockEntity;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

/**
 * Hourglass block.
 */
public class HourglassBlock extends BlockWithEntity {
    protected static final VoxelShape SHAPE = Block.createCuboidShape(3.0, 0.0, 3.0, 13.0, 16.0, 13.0);
    public static final IntProperty TIME = IntProperty.of("time", 0, 4);
    public static final BooleanProperty POWERED = BooleanProperty.of("powered");

    /**
     * Constructor.
     * @param settings settings
     */
    public HourglassBlock(Settings settings) {
        super(settings);
        setDefaultState((stateManager.getDefaultState()).with(TIME, 4).with(POWERED, false));
    }

    /**
     * Create block entity.
     * @param pos position
     * @param state state
     * @return block entity
     */
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new HourglassBlockEntity(pos, state);
    }

    /**
     * Action on block use.
     * @param state state
     * @param world world
     * @param pos position
     * @param player player
     * @param hand hand
     * @param hit hit result
     * @return action result
     */
    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (world.isClient) {
            return ActionResult.SUCCESS;
        }
        if (world.getBlockEntity(pos) instanceof HourglassBlockEntity hourglassBlockEntity) {
            hourglassBlockEntity.reverse(world, pos, state);//TODO advancement timekeeper
        }
        return ActionResult.CONSUME;
    }

    /**
     * Get block entity ticker.
     * @param world world
     * @param state state
     * @param type entity type
     * @return block entity ticker
     */
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return world.isClient ? null : checkType(type, BlockEntityTypes.HOURGLASS_BLOCK_ENTITY, HourglassBlockEntity::tick);
    }

    /**
     * Run every time a neighbor updates.
     * @param state state
     * @param world world
     * @param pos position
     * @param block block
     * @param fromPos update source position
     * @param notify notify
     */
    @Override
    public void neighborUpdate(BlockState state, World world, BlockPos pos, Block block, BlockPos fromPos, boolean notify) {
        boolean powered = world.isReceivingRedstonePower(pos);
        boolean poweredCurrent = state.get(POWERED);
        if (powered != poweredCurrent) {
            world.setBlockState(pos, state = state.with(POWERED, powered));
            if (powered && world.getBlockEntity(pos) instanceof HourglassBlockEntity hourglassBlockEntity) {
                hourglassBlockEntity.reverse(world, pos, state);
            }
        }
    }

    /**
     * Check if the block has comparator output.
     * @param state state
     * @return {@code true}
     */
    @Override
    public boolean hasComparatorOutput(BlockState state) {
        return true;
    }

    /**
     * Get comparator output.
     * @param state state
     * @param world world
     * @param pos position
     * @return comparator output
     */
    @Override
    public int getComparatorOutput(BlockState state, World world, BlockPos pos) {
        return world.getBlockEntity(pos) instanceof HourglassBlockEntity hourglassBlockEntity ?
                hourglassBlockEntity.calculateComparatorOutput() : 0;
    }

    /**
     * Get outline shape.
     * @param state block state
     * @param world world
     * @param pos position
     * @param context shape context
     * @return outline shape
     */
    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    /**
     * Check if the block has sided transparency/
     * @param state block state
     * @return {@code true}
     */
    @Override
    public boolean hasSidedTransparency(BlockState state) {
        return true;
    }

    /**
     * Get block render type.
     * @param state block state
     * @return block render type
     */
    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    /**
     * Add block state properties.
     * @param builder state manager builder
     */
    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(TIME, POWERED);
    }

    /**
     * Check if the block can be pathfinded through.
     * @param state block state
     * @param world world
     * @param pos position
     * @param type navigation type
     * @return {@code false}
     */
    @Override
    public boolean canPathfindThrough(BlockState state, BlockView world, BlockPos pos, NavigationType type) {
        return false;
    }
}
