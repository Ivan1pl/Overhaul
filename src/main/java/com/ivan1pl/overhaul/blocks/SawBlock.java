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

import com.ivan1pl.overhaul.screens.SawScreenHandler;
import com.ivan1pl.overhaul.stats.Stats;
import net.minecraft.block.*;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.SimpleNamedScreenHandlerFactory;
import net.minecraft.state.StateManager;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.ActionResult;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

import static net.minecraft.block.HorizontalFacingBlock.FACING;

/**
 * Saw block implementation.
 */
public class SawBlock extends Block {
    private static final Text TITLE = new TranslatableText("container.saw");
    protected static final VoxelShape SHAPE = Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 9.0, 16.0);

    /**
     * Constructor.
     * @param settings block settings
     */
    public SawBlock(Settings settings) {
        super(settings);
        setDefaultState((stateManager.getDefaultState()).with(FACING, Direction.NORTH));
    }

    /**
     * Get block placement state.
     * @param ctx placement context
     * @return block placement state
     */
    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return getDefaultState().with(FACING, ctx.getPlayerFacing().getOpposite());
    }

    /**
     * Get action result on block use.
     * @param state block state
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
        player.openHandledScreen(state.createScreenHandlerFactory(world, pos));
        player.incrementStat(Stats.INTERACT_WITH_SAW);
        return ActionResult.CONSUME;
    }

    /**
     * Get screen handler factory.
     * @param state block state
     * @param world world
     * @param pos position
     * @return screen handler factory
     */
    @Override
    public NamedScreenHandlerFactory createScreenHandlerFactory(BlockState state, World world, BlockPos pos) {
        return new SimpleNamedScreenHandlerFactory((syncId, playerInventory, player) -> new SawScreenHandler(syncId, playerInventory, ScreenHandlerContext.create(world, pos)), TITLE);
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
     * Rotate block state.
     * @param state state to rotate
     * @param rotation rotation
     * @return rotated state
     */
    @Override
    public BlockState rotate(BlockState state, BlockRotation rotation) {
        return state.with(FACING, rotation.rotate(state.get(FACING)));
    }

    /**
     * Mirror block state.
     * @param state state to mirror
     * @param mirror mirror
     * @return mirrored state
     */
    @Override
    public BlockState mirror(BlockState state, BlockMirror mirror) {
        return state.rotate(mirror.getRotation(state.get(FACING)));
    }

    /**
     * Add block state properties.
     * @param builder state manager builder
     */
    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING);
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
