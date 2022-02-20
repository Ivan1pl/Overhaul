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

package com.ivan1pl.overhaul;

import com.ivan1pl.overhaul.initializers.LootTablesInitializer;
import com.ivan1pl.overhaul.loot.LootTables;
import com.ivan1pl.overhaul.recipes.RecipeSerializers;
import com.ivan1pl.overhaul.villages.TradeOffers;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Overhaul implements ModInitializer {
	public static final String ID = "overhaul";
	public static final Logger LOGGER = LoggerFactory.getLogger(ID);

	@Override
	public void onInitialize() {
		LOGGER.info("Initializing Overhaul...");
		LootTablesInitializer.initialize();
		RecipeSerializers.initialize();
		TradeOffers.initialize();
		LootTables.initialize();
		LOGGER.info("Initialized Overhaul!");
	}
}
