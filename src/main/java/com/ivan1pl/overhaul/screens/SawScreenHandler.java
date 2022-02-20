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

package com.ivan1pl.overhaul.screens;

import com.google.common.collect.Lists;
import com.ivan1pl.overhaul.blocks.Blocks;
import com.ivan1pl.overhaul.recipes.RecipeTypes;
import com.ivan1pl.overhaul.recipes.SawingRecipe;
import com.ivan1pl.overhaul.sounds.SoundEvents;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.CraftingResultInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.Property;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.screen.slot.Slot;
import net.minecraft.sound.SoundCategory;
import net.minecraft.world.World;

import java.util.List;

/**
 * Screen handler for saw.
 */
public class SawScreenHandler extends ScreenHandler {
    private final ScreenHandlerContext context;
    private final Property selectedRecipe = Property.create();
    private final World world;
    private List<SawingRecipe> availableRecipes = Lists.newArrayList();
    private ItemStack inputStack = ItemStack.EMPTY;
    long lastTakeTime;
    final Slot inputSlot;
    final Slot outputSlot;
    Runnable contentsChangedListener = () -> {};
    public final Inventory input = new SimpleInventory(1) {
        @Override
        public void markDirty() {
            super.markDirty();
            SawScreenHandler.this.onContentChanged(this);
            SawScreenHandler.this.contentsChangedListener.run();
        }
    };
    final CraftingResultInventory output = new CraftingResultInventory();

    /**
     * Constructor.
     * @param syncId sync id
     * @param playerInventory player inventory
     */
    public SawScreenHandler(int syncId, PlayerInventory playerInventory) {
        this(syncId, playerInventory, ScreenHandlerContext.EMPTY);
    }

    /**
     * Constructor.
     * @param syncId sync id
     * @param playerInventory player inventory
     * @param context screen handler context
     */
    public SawScreenHandler(int syncId, PlayerInventory playerInventory, final ScreenHandlerContext context) {
        super(ScreenHandlerTypes.SAW, syncId);
        int i;
        this.context = context;
        this.world = playerInventory.player.world;
        this.inputSlot = this.addSlot(new Slot(this.input, 0, 20, 33));
        this.outputSlot = this.addSlot(new Slot(this.output, 1, 143, 33) {
            @Override
            public boolean canInsert(ItemStack stack) {
                return false;
            }

            @Override
            public void onTakeItem(PlayerEntity player, ItemStack stack) {
                stack.onCraft(player.world, player, stack.getCount());
                SawScreenHandler.this.output.unlockLastRecipe(player);
                ItemStack itemStack = SawScreenHandler.this.inputSlot.takeStack(1);
                if (!itemStack.isEmpty()) {
                    SawScreenHandler.this.populateResult();
                }
                context.run((world, pos) -> {
                    long l = world.getTime();
                    if (SawScreenHandler.this.lastTakeTime != l) {
                        world.playSound(null, pos, SoundEvents.SAW, SoundCategory.BLOCKS, 1.0f, 1.0f);
                        SawScreenHandler.this.lastTakeTime = l;
                    }
                });
                super.onTakeItem(player, stack);
            }
        });
        for (i = 0; i < 3; ++i) {
            for (int j = 0; j < 9; ++j) {
                this.addSlot(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }
        for (i = 0; i < 9; ++i) {
            this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 142));
        }
        this.addProperty(this.selectedRecipe);
    }

    /**
     * Get selected recipe.
     * @return selected recipe
     */
    public int getSelectedRecipe() {
        return this.selectedRecipe.get();
    }

    /**
     * Get all available recipes.
     * @return available recipes
     */
    public List<SawingRecipe> getAvailableRecipes() {
        return this.availableRecipes;
    }

    /**
     * Get available recipe count.
     * @return available recipe count
     */
    public int getAvailableRecipeCount() {
        return this.availableRecipes.size();
    }

    /**
     * Check if crafting is possible.
     * @return whether crafting is possible
     */
    public boolean canCraft() {
        return this.inputSlot.hasStack() && !this.availableRecipes.isEmpty();
    }

    /**
     * Check if a player can use the block.
     * @param player player trying to use the block
     * @return whether the player can use the block
     */
    @Override
    public boolean canUse(PlayerEntity player) {
        return SawScreenHandler.canUse(this.context, player, Blocks.SAW);
    }

    /**
     * React to button click event.
     * @param player player
     * @param id recipe index
     * @return {@code true}
     */
    @Override
    public boolean onButtonClick(PlayerEntity player, int id) {
        if (this.isInBounds(id)) {
            this.selectedRecipe.set(id);
            this.populateResult();
        }
        return true;
    }

    /**
     * Check if recipe index is correct.
     * @param id recipe index
     * @return whether recipe index is correct
     */
    private boolean isInBounds(int id) {
        return id >= 0 && id < this.availableRecipes.size();
    }

    /**
     * React to inventory content change.
     * @param inventory inventory
     */
    @Override
    public void onContentChanged(Inventory inventory) {
        ItemStack itemStack = this.inputSlot.getStack();
        if (!itemStack.isOf(this.inputStack.getItem())) {
            this.inputStack = itemStack.copy();
            this.updateInput(inventory, itemStack);
        }
    }

    /**
     * Update crafting input.
     * @param input input
     * @param stack input item stack
     */
    private void updateInput(Inventory input, ItemStack stack) {
        this.availableRecipes.clear();
        this.selectedRecipe.set(-1);
        this.outputSlot.setStack(ItemStack.EMPTY);
        if (!stack.isEmpty()) {
            this.availableRecipes = this.world.getRecipeManager().getAllMatches(RecipeTypes.SAWING, input, this.world);
        }
    }

    /**
     * Populate crafting result.
     */
    void populateResult() {
        if (!this.availableRecipes.isEmpty() && this.isInBounds(this.selectedRecipe.get())) {
            SawingRecipe sawingRecipe = this.availableRecipes.get(this.selectedRecipe.get());
            this.output.setLastRecipe(sawingRecipe);
            this.outputSlot.setStack(sawingRecipe.craft(this.input));
        } else {
            this.outputSlot.setStack(ItemStack.EMPTY);
        }
        this.sendContentUpdates();
    }

    /**
     * Get screen handler type.
     * @return screen handler type
     */
    @Override
    public ScreenHandlerType<?> getType() {
        return ScreenHandlerTypes.SAW;
    }

    /**
     * Set contents changed listener.
     * @param contentsChangedListener contents changed listener
     */
    public void setContentsChangedListener(Runnable contentsChangedListener) {
        this.contentsChangedListener = contentsChangedListener;
    }

    /**
     * Check if item stack can be inserted into slot.
     * @param stack item stack
     * @param slot slot
     * @return whether the item stack can be inserted into slot
     */
    @Override
    public boolean canInsertIntoSlot(ItemStack stack, Slot slot) {
        return slot.inventory != this.output && super.canInsertIntoSlot(stack, slot);
    }

    /**
     * Transfer slot.
     * @param player player
     * @param index slot index
     * @return transferred slot
     */
    @Override
    public ItemStack transferSlot(PlayerEntity player, int index) {
        ItemStack itemStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        if (slot != null && slot.hasStack()) {
            ItemStack itemStack2 = slot.getStack();
            Item item = itemStack2.getItem();
            itemStack = itemStack2.copy();
            if (index == 1) {
                item.onCraft(itemStack2, player.world, player);
                if (!this.insertItem(itemStack2, 2, 38, true)) {
                    return ItemStack.EMPTY;
                }
                slot.onQuickTransfer(itemStack2, itemStack);
            } else if (index == 0 ? !this.insertItem(itemStack2, 2, 38, false) : (this.world.getRecipeManager().getFirstMatch(RecipeTypes.SAWING, new SimpleInventory(itemStack2), this.world).isPresent() ? !this.insertItem(itemStack2, 0, 1, false) : (index >= 2 && index < 29 ? !this.insertItem(itemStack2, 29, 38, false) : index >= 29 && index < 38 && !this.insertItem(itemStack2, 2, 29, false)))) {
                return ItemStack.EMPTY;
            }
            if (itemStack2.isEmpty()) {
                slot.setStack(ItemStack.EMPTY);
            }
            slot.markDirty();
            if (itemStack2.getCount() == itemStack.getCount()) {
                return ItemStack.EMPTY;
            }
            slot.onTakeItem(player, itemStack2);
            this.sendContentUpdates();
        }
        return itemStack;
    }

    /**
     * Close screen.
     * @param player player
     */
    @Override
    public void close(PlayerEntity player) {
        super.close(player);
        this.output.removeStack(1);
        this.context.run((world, pos) -> this.dropInventory(player, this.input));
    }
}

