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

package com.ivan1pl.overhaul.client.gui.screens;

import com.ivan1pl.overhaul.recipes.SawingRecipe;
import com.ivan1pl.overhaul.screens.SawScreenHandler;
import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.sound.PositionedSoundInstance;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

import java.util.List;

/**
 * Screen for sawing.
 */
@Environment(value=EnvType.CLIENT)
public class SawScreen extends HandledScreen<SawScreenHandler> {
    //TODO add texture to resources
    private static final Identifier TEXTURE = new Identifier("textures/gui/container/stonecutter.png");
    private static final int SCROLLBAR_WIDTH = 12;
    private static final int SCROLLBAR_HEIGHT = 15;
    private static final int RECIPE_LIST_COLUMNS = 4;
    private static final int RECIPE_LIST_ROWS = 3;
    private static final int RECIPE_ENTRY_WIDTH = 16;
    private static final int RECIPE_ENTRY_HEIGHT = 18;
    private static final int SCROLLBAR_AREA_HEIGHT = 54;
    private static final int RECIPE_LIST_OFFSET_X = 52;
    private static final int RECIPE_LIST_OFFSET_Y = 14;
    private float scrollAmount;
    private boolean mouseClicked;
    private int scrollOffset;
    private boolean canCraft;

    /**
     * Constructor.
     * @param handler screen handler
     * @param inventory player inventory
     * @param title screen title
     */
    public SawScreen(SawScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
        handler.setContentsChangedListener(this::onInventoryChange);
        --this.titleY;
    }

    /**
     * Render screen.
     * @param matrices matrices
     * @param mouseX mouse X position
     * @param mouseY mouse Y position
     * @param delta time delta since last frame
     */
    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        super.render(matrices, mouseX, mouseY, delta);
        this.drawMouseoverTooltip(matrices, mouseX, mouseY);
    }

    /**
     * Draw background.
     * @param matrices matrices
     * @param delta time delta since last frame
     * @param mouseX mouse X position
     * @param mouseY mouse Y position
     */
    @Override
    protected void drawBackground(MatrixStack matrices, float delta, int mouseX, int mouseY) {
        this.renderBackground(matrices);
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
        RenderSystem.setShaderTexture(0, TEXTURE);
        int i = this.x;
        int j = this.y;
        this.drawTexture(matrices, i, j, 0, 0, this.backgroundWidth, this.backgroundHeight);
        int k = (int)(41.0f * this.scrollAmount);
        this.drawTexture(matrices, i + 119, j + SCROLLBAR_HEIGHT + k, 176 + (this.shouldScroll() ? 0 : SCROLLBAR_WIDTH), 0, SCROLLBAR_WIDTH, SCROLLBAR_HEIGHT);
        int l = this.x + RECIPE_LIST_OFFSET_X;
        int m = this.y + RECIPE_LIST_OFFSET_Y;
        int n = this.scrollOffset + SCROLLBAR_WIDTH;
        this.renderRecipeBackground(matrices, mouseX, mouseY, l, m, n);
        this.renderRecipeIcons(l, m, n);
    }

    /**
     * Draw tooltip.
     * @param matrices matrices
     * @param x mouse X position
     * @param y mouse Y position
     */
    @Override
    protected void drawMouseoverTooltip(MatrixStack matrices, int x, int y) {
        super.drawMouseoverTooltip(matrices, x, y);
        if (this.canCraft) {
            int i = this.x + RECIPE_LIST_OFFSET_X;
            int j = this.y + RECIPE_LIST_OFFSET_Y;
            int k = this.scrollOffset + SCROLLBAR_WIDTH;
            List<SawingRecipe> list = this.handler.getAvailableRecipes();
            for (int l = this.scrollOffset; l < k && l < this.handler.getAvailableRecipeCount(); ++l) {
                int m = l - this.scrollOffset;
                int n = i + m % RECIPE_LIST_COLUMNS * RECIPE_ENTRY_WIDTH;
                int o = j + m / RECIPE_LIST_COLUMNS * RECIPE_ENTRY_HEIGHT + 2;
                if (x < n || x >= n + RECIPE_ENTRY_WIDTH || y < o || y >= o + RECIPE_ENTRY_HEIGHT) continue;
                this.renderTooltip(matrices, list.get(l).getOutput(), x, y);
            }
        }
    }

    /**
     * Render recipe list background.
     * @param matrices matrices
     * @param mouseX mouse X position
     * @param mouseY mouse Y position
     * @param x recipe list X position
     * @param y recipe list Y position
     * @param scrollOffset scroll offset
     */
    private void renderRecipeBackground(MatrixStack matrices, int mouseX, int mouseY, int x, int y, int scrollOffset) {
        for (int i = this.scrollOffset; i < scrollOffset && i < this.handler.getAvailableRecipeCount(); ++i) {
            int j = i - this.scrollOffset;
            int k = x + j % RECIPE_LIST_COLUMNS * RECIPE_ENTRY_WIDTH;
            int l = j / RECIPE_LIST_COLUMNS;
            int m = y + l * RECIPE_ENTRY_HEIGHT + 2;
            int n = this.backgroundHeight;
            if (i == this.handler.getSelectedRecipe()) {
                n += RECIPE_ENTRY_HEIGHT;
            } else if (mouseX >= k && mouseY >= m && mouseX < k + RECIPE_ENTRY_WIDTH && mouseY < m + RECIPE_ENTRY_HEIGHT) {
                n += 2 * RECIPE_ENTRY_HEIGHT;
            }
            this.drawTexture(matrices, k, m - 1, 0, n, RECIPE_ENTRY_WIDTH, RECIPE_ENTRY_HEIGHT);
        }
    }

    /**
     * Render recipe icons.
     * @param x recipe list X position
     * @param y recipe list Y position
     * @param scrollOffset scroll offset
     */
    private void renderRecipeIcons(int x, int y, int scrollOffset) {
        List<SawingRecipe> list = this.handler.getAvailableRecipes();
        for (int i = this.scrollOffset; i < scrollOffset && i < this.handler.getAvailableRecipeCount(); ++i) {
            int j = i - this.scrollOffset;
            int k = x + j % RECIPE_LIST_COLUMNS * RECIPE_ENTRY_WIDTH;
            int l = j / RECIPE_LIST_COLUMNS;
            int m = y + l * RECIPE_ENTRY_HEIGHT + 2;
            this.client.getItemRenderer().renderInGuiWithOverrides(list.get(i).getOutput(), k, m);
        }
    }

    /**
     * React to mouse click event.
     * @param mouseX mouse X position
     * @param mouseY mouse Y position
     * @param button mouse button
     * @return whether the click was handled
     */
    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        this.mouseClicked = false;
        if (this.canCraft) {
            int i = this.x + RECIPE_LIST_OFFSET_X;
            int j = this.y + RECIPE_LIST_OFFSET_Y;
            int k = this.scrollOffset + SCROLLBAR_WIDTH;
            for (int l = this.scrollOffset; l < k; ++l) {
                int m = l - this.scrollOffset;
                double d = mouseX - (double)(i + m % RECIPE_LIST_COLUMNS * RECIPE_ENTRY_WIDTH);
                double e = mouseY - (double)(j + m / RECIPE_LIST_COLUMNS * RECIPE_ENTRY_HEIGHT);
                if (!(d >= 0.0) || !(e >= 0.0) || !(d < RECIPE_ENTRY_WIDTH) || !(e < RECIPE_ENTRY_HEIGHT) || !this.handler.onButtonClick(this.client.player, l)) continue;
                MinecraftClient.getInstance().getSoundManager().play(PositionedSoundInstance.master(/*TODO*/SoundEvents.UI_STONECUTTER_SELECT_RECIPE, 1.0f));
                this.client.interactionManager.clickButton(this.handler.syncId, l);
                return true;
            }
            i = this.x + 119;
            j = this.y + 9;
            if (mouseX >= (double)i && mouseX < (double)(i + SCROLLBAR_WIDTH) && mouseY >= (double)j && mouseY < (double)(j + SCROLLBAR_AREA_HEIGHT)) {
                this.mouseClicked = true;
            }
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    /**
     * React to mouse drag event.
     * @param mouseX mouse X position
     * @param mouseY mouse Y position
     * @param button mouse button
     * @param deltaX X position delta
     * @param deltaY Y position delta
     * @return whether the drag was handled
     */
    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
        if (this.mouseClicked && this.shouldScroll()) {
            int i = this.y + RECIPE_LIST_OFFSET_Y;
            int j = i + SCROLLBAR_AREA_HEIGHT;
            this.scrollAmount = ((float)mouseY - (float)i - 7.5f) / ((float)(j - i) - 15.0f);
            this.scrollAmount = MathHelper.clamp(this.scrollAmount, 0.0f, 1.0f);
            this.scrollOffset = (int)((double)(this.scrollAmount * (float)this.getMaxScroll()) + 0.5) * RECIPE_LIST_COLUMNS;
            return true;
        }
        return super.mouseDragged(mouseX, mouseY, button, deltaX, deltaY);
    }

    /**
     * React to mouse scroll event.
     * @param mouseX mouse X position
     * @param mouseY mouse Y position
     * @param amount scroll amount
     * @return whether the scroll was handled
     */
    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double amount) {
        if (this.shouldScroll()) {
            int i = this.getMaxScroll();
            this.scrollAmount = (float)((double)this.scrollAmount - amount / (double)i);
            this.scrollAmount = MathHelper.clamp(this.scrollAmount, 0.0f, 1.0f);
            this.scrollOffset = (int)((double)(this.scrollAmount * (float)i) + 0.5) * RECIPE_LIST_COLUMNS;
        }
        return true;
    }

    /**
     * Check if scrolling is possible.
     * @return whether scrolling is possible
     */
    private boolean shouldScroll() {
        return this.canCraft && this.handler.getAvailableRecipeCount() > RECIPE_LIST_COLUMNS * RECIPE_LIST_ROWS;
    }

    /**
     * Get maximum scroll amount.
     * @return maximum scroll amount
     */
    protected int getMaxScroll() {
        return (this.handler.getAvailableRecipeCount() + RECIPE_LIST_COLUMNS - 1) / RECIPE_LIST_COLUMNS - RECIPE_LIST_ROWS;
    }

    /**
     * Handle inventory contents change event.
     */
    private void onInventoryChange() {
        this.canCraft = this.handler.canCraft();
        if (!this.canCraft) {
            this.scrollAmount = 0.0f;
            this.scrollOffset = 0;
        }
    }
}

