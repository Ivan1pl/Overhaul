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

package com.ivan1pl.overhaul.stats;

import com.ivan1pl.overhaul.Overhaul;
import net.minecraft.stat.StatFormatter;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

/**
 * Custom stats.
 */
public class Stats {
    public static final Identifier INTERACT_WITH_SAW = register("interact_with_saw", StatFormatter.DEFAULT);

    /**
     * Register custom stat.
     * @param name identifier path
     * @param formatter stat formatter
     * @return registered stat
     */
    private static Identifier register(String name, StatFormatter formatter) {
        Identifier identifier = new Identifier(Overhaul.ID, name);
        Registry.register(Registry.CUSTOM_STAT, name, identifier);
        net.minecraft.stat.Stats.CUSTOM.getOrCreateStat(identifier, formatter);
        return identifier;
    }
}
