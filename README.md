# ModdedIntegration
[![Version](https://img.shields.io/badge/version-1.1-blue)](https://github.com/UknownGino/ModdedIntegration/releases/latest) [![Download](https://img.shields.io/badge/download-latest-brightgreen)](https://github.com/UknownGino/ModdedIntegration/releases/latest/download/ModdedIntegration.jar)  

A simple Minecraft Plugin that auto-populates the [EssentialsX](https://www.spigotmc.org/resources/essentialsx.9089/)'s [items.csv file](https://github.com/EssentialsX/Essentials/blob/2.x/Essentials/src/main/resources/items.csv) with [Forge](http://files.minecraftforge.net/) items and blocks.
# ✅ Requirements
This Plugin needs some dependencies in order to work.
* [Magma 1.12.2](https://magmafoundation.org/#download)
* [EssentialsX](https://www.spigotmc.org/resources/essentialsx.9089/)
# 🔮 How do i use it?
Put the [plugin jar](https://github.com/UknownGino/ModdedIntegration/releases/latest/download/ModdedIntegration.jar) in your plugins folder and start your server.
Now the [Essentials](https://www.spigotmc.org/resources/essentialsx.9089/)' [items.csv file](https://github.com/EssentialsX/Essentials/blob/2.x/Essentials/src/main/resources/items.csv) is populated.

To use modded materials with essentials you need to follow the following scheme ➠ `ModID_ItemID`  
Example with the [ToughAsNails Mod](https://www.curseforge.com/minecraft/mc-mods/tough-as-nails) ➠ **/give < playerName > toughasnails_purified_water_bottle**
# ❓ How it works?
The plugin gets all of the modded materials (blocks and items) and puts them into the essentials' [items.csv](https://github.com/EssentialsX/Essentials/blob/2.x/Essentials/src/main/resources/items.csv) file.
# 💡 Original idea
The idea of creating this plugin came to me through [this issue](https://github.com/magmafoundation/Magma/issues/311) opened on the [Magma's GitHub](https://github.com/magmafoundation/Magma).
