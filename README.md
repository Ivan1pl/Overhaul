# Overhaul

This mod aims to add a lot of new features to the game while maintaining the "vanilla feel". It contains some of my
favourite ideas from minecraft suggestions reddit, as well as some of my own ideas.

## Features

### Additions

#### Blocks
* ![Saw](src/main/resources/assets/overhaul/textures/block/saw_side.png) **Saw** - can be crafted with stone in the
  middle, iron ingot in the middle slot of the top row, and any kind of wooden slabs in the remaining slots of the
  bottom two rows. Can be used to cut wood related blocks in smaller and more precise quantities than crafting, and is
  more efficient than crafting for certain recipes. Drops itself when mined with any kind of axe. Drops nothing when
  mined with any other tool.

#### Items
* ![Bat wing](src/main/resources/assets/overhaul/textures/item/bat_wing.png) **Bat wing** - dropped by bats upon death.
  Can be used as potion ingredient. When eaten, restores 1 hunger and 0.2 saturation and applies Poison II effect for
  5 seconds.

#### Status effects
* ![Gliding](src/main/resources/assets/overhaul/textures/mob_effect/gliding.png) **Gliding** - players glide in the air
  as if they had an elytra.
* ![Stickiness](src/main/resources/assets/overhaul/textures/mob_effect/stickiness.png) **Stickiness** - decreases jump
  velocity.

#### Potions
* **Potion of Gliding, Splash Potion of Gliding, Lingering Potion of Gliding** - brewed by adding Bat Wing to an Awkward
  Potion. Gives **Gliding** status effect. Effect duration can be extended with redstone dust.
* **Potion of Stickiness, Splash Potion of Stickiness, Lingering Potion of Stickiness** - brewed by adding Fermented
  Spider Eye to a Potion of Gliding. Gives **Stickiness** status effect. Effect duration can be extended with redstone
  dust. Strength can be increased with glowstone dust.

#### Recipe types
* **Sawing recipes** - can be used within the new **Saw** block.

#### Sawing recipes
* Logs, stems, wood and hyphae can be cut into 4 fences, 4 fence gates, 4 planks, 4 signs, 8 slabs, 4 stairs or 4
  trapdoors of the same type.
* Planks can be cut into 1 fence, 1 fence gate, 1 sign, 2 slabs, 1 stair or 1 trapdoor of the same type.
* Logs, stems, wood and hyphae can be cut into their stripped variant.

#### Villager professions
* **Carpenter** - uses the new **Saw** block as their workstation. Buys overworld planks and items required to craft
  their products (Paper, String, Flint, Redstone dust, Iron ingot, Diamond) and sells Wooden Axe, Bowl, Cartography
  table, Fletching table, Loom, Lectern, Note block, Bookshelf, Smithing table and Jukebox. If a nearby player has the
  **Hero of the village** status effect, gifts them oak boats and bowls.

### Changes

#### Generated structures
* All types of villages can now generate carpenter's house, containing carpenter's workplace, the **Saw** block.

#### Loot
* Bats now drop 0-1 bat wings upon death. Upper limit is increased by one for each level of Looting.

#### Trading
* Wandering Traders now have a 10% chance of selling a map leading to a random location within a 12800x12800 area around
  them. The map is labeled with the biome name. If you are lucky, you can get a map of a rare biome. This includes
  underground biomes.
* If all of the Wandering Trader's offers has been disabled, it converts to an unemployed villager.

## Credits

* [Wandering traders should have a small chance of selling you a map to rare biomes like Mushroom, Mesa and Jungle.](https://www.reddit.com/r/minecraftsuggestions/comments/htbm63/wandering_traders_should_have_a_small_chance_of/)
* [If you buy a wandering trader's whole stock, he moves in and becomes a villager.](https://www.reddit.com/r/minecraftsuggestions/comments/nf8edj/if_you_buy_a_wandering_traders_whole_stock_he/)
