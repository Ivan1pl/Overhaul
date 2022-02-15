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

import com.ivan1pl.overhaul.trading.factories.SellBiomeMapFactory;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.WanderingTraderEntity;
import net.minecraft.item.map.MapIcon;
import net.minecraft.village.TradeOffer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Random;

/**
 * Additional functionality to wandering trader entity.
 */
@Mixin(WanderingTraderEntity.class)
public class WanderingTraderEntityMixin {
    private static final SellBiomeMapFactory SELL_BIOME_MAP_FACTORY =
            new SellBiomeMapFactory(5, MapIcon.Type.RED_X, 1, 1);

    /**
     * Add a trade for a map leading to a random biome within 6400 block radius.
     *
     * There is a 10% chance that this trade will be added. Radius is the same as in the {@code /locatebiome} command.
     *
     * @param callbackInfo mixin callback info
     */
    @Inject(method = "Lnet/minecraft/entity/passive/WanderingTraderEntity;fillRecipes()V", at = @At("TAIL"))
    private void addMapTrade(CallbackInfo callbackInfo) {
        WanderingTraderEntity wanderingTrader = thisObject();
        Random random = wanderingTrader.getRandom();
        if (random.nextInt(10) == 0) {
            TradeOffer tradeOffer = SELL_BIOME_MAP_FACTORY.create(wanderingTrader, random);
            if (tradeOffer != null) {
                wanderingTrader.getOffers().add(tradeOffer);
            }
        }
    }

    /**
     * Convert the wandering trader to villager if all of the available offers have been used.
     * @param tradeOffer most recently used offer
     * @param callbackInfo mixin callback info
     */
    @Inject(method = "Lnet/minecraft/entity/passive/WanderingTraderEntity;afterUsing(Lnet/minecraft/village/TradeOffer;)V",
            at = @At("TAIL"))
    private void checkAllOffersUsed(TradeOffer tradeOffer, CallbackInfo callbackInfo) {
        if (thisObject().getOffers().stream().allMatch(TradeOffer::isDisabled)) {
            thisObject().convertTo(EntityType.VILLAGER, true);
        }
    }

    /**
     * Get {@code this} instance from the mixin target.
     * @return {@code this} instance in the mixin target
     */
    private WanderingTraderEntity thisObject() {
        return (WanderingTraderEntity) (Object) this;
    }
}
