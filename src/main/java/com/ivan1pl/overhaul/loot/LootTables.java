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

package com.ivan1pl.overhaul.loot;

import com.ivan1pl.overhaul.Overhaul;
import com.ivan1pl.overhaul.villages.VillagerProfessions;
import net.minecraft.entity.ai.brain.task.GiveGiftsToHeroTask;
import net.minecraft.util.Identifier;

/**
 * Custom loot tables.
 */
public class LootTables {
    public static final Identifier HERO_OF_THE_VILLAGE_CARPENTER_GIFT_GAMEPLAY = register("gameplay/hero_of_the_village/carpenter_gift");

    /**
     * Register loot table.
     * @param id identifier path
     * @return registered identifier
     */
    private static Identifier register(String id) {
        return net.minecraft.loot.LootTables.registerLootTable(new Identifier(Overhaul.ID, id));
    }

    /**
     * Ensure that loot tables are added to all necessary static contexts.
     */
    public static void initialize() {
        GiveGiftsToHeroTask.GIFTS.put(VillagerProfessions.CARPENTER, HERO_OF_THE_VILLAGE_CARPENTER_GIFT_GAMEPLAY);
    }
}
