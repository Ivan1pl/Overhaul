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

import com.google.common.collect.ImmutableSet;
import com.ivan1pl.overhaul.Overhaul;
import com.ivan1pl.overhaul.sounds.SoundEvents;
import com.ivan1pl.overhaul.world.poi.PointsOfInterest;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.village.VillagerProfession;
import net.minecraft.world.poi.PointOfInterestType;

/**
 * Custom villager professions.
 */
public class VillagerProfessions {
    public static final VillagerProfession CARPENTER = register("carpenter", PointsOfInterest.CARPENTER, SoundEvents.SAW);

    /**
     * Register villager profession.
     * @param name namespace path
     * @param workStation work station
     * @param workSound work sound
     * @return registered profession
     */
    private static VillagerProfession register(String name, PointOfInterestType workStation, SoundEvent workSound) {
        return Registry.register(Registry.VILLAGER_PROFESSION, new Identifier(Overhaul.ID, name),
                new VillagerProfession(name, workStation, ImmutableSet.of(), ImmutableSet.of(), workSound));
    }

    /**
     * Empty static method to force class initialization.
     */
    public static void init() {}
}
