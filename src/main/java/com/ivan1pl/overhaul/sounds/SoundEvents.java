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

package com.ivan1pl.overhaul.sounds;

import com.ivan1pl.overhaul.Overhaul;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

/**
 * Custom sound events.
 */
public class SoundEvents {
    public static final SoundEvent SAW = register("saw");

    /**
     * Register sound event.
     * @param name identifier path
     * @return registered sound event
     */
    private static SoundEvent register(String name) {
        return Registry.register(Registry.SOUND_EVENT, name, new SoundEvent(new Identifier(Overhaul.ID, name)));
    }
}
