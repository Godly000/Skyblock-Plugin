# Skyblock Plugin!
## Description
A Bukkit/Paper server plugin for Skyblock! Unlike most other Skyblock mods, plugins, and datapacks, this plugin makes it possible to obtain every item, entity, and advancement that is obtainable in vanilla survival! There are several changes made to the game that allow this to happen:
* A few types of blocks are kept: Chests, Trapped Chests, Spawners, End Portal Frames, End Portal Blocks, End Gateways, Obsidian within the Main End Island, and Bedrock in the End
* A helper datapack is used for obtaining the rest of the items, entities, and advancements, as well as for creating the spawn platform
## Helper Datapack (Still in Pre-Release!)
The helper datapack can be found [here](https://github.com/Godly000/Skyblock-Helper-Datapack) (still a pre-release!)
The datapack does these things:
* Immediately spawns players in the Nether above a Chest containing 64 Bone Blocks and a Crimson Nylium at 0, 0, 0
* Converts all Sculk Shriekers near players that cannot summon Wardens and give the Darkness effect to ones that can
* If players are able to catch one of a Mineshaft's Chest Minecart before it falls into the void, it contains Glow Berries, Glow Lichen, and Spore Blossoms at equal chances in place of just Glow Berries
* The same replacement happens for Ancient City chests
* End City chests have Chorus Flowers instead of Beetoroot Seeds
* _Work In Progress: Mansions generate special loot instead of having nearly the same loot table as Dungeon chests_
* Amethyst Cluster/Buds drop Amethyst with increasing chances when mined with Silk Touch (2%, 5%, 10%, 17.5%)
* The Ender Dragon drops its head when killed by a charged Creeper
* Piglin Brutes rarely drop Warped Nylium (2.5% chance +1% per Looting level)
* Wardens drop Deepslate (8-16, +8-16 per Looting level)
* Armorer Villagers gift a Lava Bucket 1% of the time
* Farmer Villagers gift a Grass Block 0.9% of the time, and Mycelium 0.1% of the time
* Toolsmiths gift Stone/Gold/Iron (5:3:1 ratio) Pickaxes/Shovels/Hoes (1:1:1 ratio)
* Weaponsmiths gift Stone/Gold/Iron (5:3:1 ratio) Swords/Axes (2:1 ratio)
* Cats gift Lilacs, Peonies, Rose Bushes, and Sunflowers with equal chance instead of what they used to gift
* Crafting recipes for: All ores, Amethyst Cluster/Buds, Calcite, Cobweb, Netherrack, Tuff
* _Work In Progress: Recipe Unlock Advancements for the added Crafting recipes_
* Bastion Remnants have completely changed spawning rules (inside its largest bounding box: 80% of the time Piglins with 3-4 per pack, 15% Hoglins with 2-3 per pack, 5% Piglin Brutes with 1 per pack)
* Inside Ocean Monuments, Elder Guardians spawn in place of Guardians 0.1% of the time
## For server managers: How to Install and Manage the Skyblock server
1. Follow directions on how to create/rent a Paper Server, but do NOT start the server just yet!
2. Put this plugin into the plugins folder
3. Edit server.properties by changing the world name to "Skyblock" under "level-name" (must be this name, or the plugin will not work). Optional: Use a seed where 0, 0 in the Nether is a Crimson Forest (speeds up progression dramatically). Optional: Set the entity distance (entity-broadcast-range-percentage) to 500 to speed up progression
3. Run the server for the first time, then stop the server once it is ready for players to join
4. Put in the Skyblock Helper Datapack into the Skyblock/datapacks folder
5. Start the server up again, and notice that it now understands that there is a datapack called "skyblock" there
6. Optional: Op the owner (type "op <Owner's IGN>" into the world seed), and load the spawn chunks for the Overworld and a 16 chunk radius for the Nether and End
7. Optional: Find a good render distance (view-distance) for the server so that it does not overload the server to the point of crashing (overloading the server at all is unavoidable with this plugin) and set the render distance for the server as such when testing is finished
8. MUST DO: Stop the server (type "stop" into the console) every time the server needs to stop instead of closing the console. Otherwise, people will lose their builds/items!
9. Optional: If you wish to re-generate chunks, you have to both use [MCA Selector](https://github.com/Querz/mcaselector/releases) and delete the corresponding chunk entry in loaded.txt inside the highest server files folder. Never add chunks to the text file, only delete lines from it _carefully_ (or there will be regrets)!
## Recommendations for Enhanced Gameplay
* [Chunkbase](chunkbase.com/apps/seed-map) if the world seed is known
* [Fabric Carpet](https://github.com/gnembon/fabric-carpet/releases) for knowing what mobs will spawn on each block (give a pink carpet first), and for many other helpful resources
* [MiniHUD](https://github.com/maruohon/minihud) for bounding boxes of structures (not out yet for 1.19)

## Progression
Stuck? Don't know how to get that one crucial thing? The entire progression list (how to get each item and entity) can be found [here](https://docs.google.com/spreadsheets/d/1S3jBzfy_PtJhQI_5jFIN3lXBiUEMebt_rT2x5os2MYw/edit#gid=973217780): (The Skyblock Items and Skyblock Entities sheets)
