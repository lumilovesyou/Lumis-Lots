<div align="center"><center>

<img src="https://raw.githubusercontent.com/lumilovesyou/Lumis-Lots/refs/heads/master/src/main/resources/icon.png" style="border-radius: 10px;" alt="An error, red wool, and green wool block">

# Lumi's Lots

*Just things I deemed important enough to add.*

Backports and reworks modern features while adding new features to 1.7.10.
</center></div>

## Features

### Backported/Reworked Features
<ol>
    <li>
        <details>
        <summary>Composting Dirt</summary>
        Crafted from dirt and eight compostable items.<br>
        ID: <code>lumis_lots:composting_dirt</code> <br>
        <img src="https://raw.githubusercontent.com/lumilovesyou/Lumis-Lots/refs/heads/master/readme/composting_dirt.png" alt="Darker dirt with green and brown spots">
        </details>
    </li>
    <li>
        <details>
        <summary>Composted Dirt</summary>
        A result of letting composting dirt sit.<br>
        ID: <code>lumis_lots:composted_dirt</code> <br>
        <img src="https://raw.githubusercontent.com/lumilovesyou/Lumis-Lots/refs/heads/master/readme/composted_dirt.png" alt="Darker dirt with white spots">
        </details>
    </li>
    <li>
        <details>
        <summary>Hoes Breaking Leaves</summary>
        Hoes now break leaves at various speeds depending on material level
        </details>
    </li>
    <li>
        <details>
        <summary>Meta Key Copy/Pasting</summary>
        You can now use the left and right meta key (⌘ on macOS) for copy, cutting, selecting all, and pasting
        </details>
    </li>
    <li>
        <details>
        <summary>Sponge Functionality</summary>
        Sponges can now be found in the creative inventory and have complete functionality (minus their furnace drying recipe, to be added). Not obtainable in survival yet
        </details>
    </li>
</ol>

### New Features
<ol>
    <li>
        <details>
        <summary>Inventory Movement</summary>
        Allows the user to move within most GUIs
        </details>
    </li>
    <li>
        <details>
        <summary>No Music Cooldown</summary>
        Removes the cooldown between music tracks
        </details>
    </li>
    <li>
        <details>
        <summary>Wither Bones</summary>
        Dropped from wither skeletons and can be crafted into wither bonemeal<br>
        ID: <code>lumis_lots:wither_bone</code> <br>
        <img src="https://raw.githubusercontent.com/lumilovesyou/Lumis-Lots/refs/heads/master/readme/wither_bone.png" alt="A long, dark bone">
        </details>
    </li>
    <li>
        <details>
        <summary>Wither Bonemeal</summary>
        Crafted from wither bones and can be used to de-age plants, clear grass/flowers from an area, or turn plants into bushes<br>
        ID: <code>lumis_lots:wither_bonemeal</code> <br>
        <img src="https://raw.githubusercontent.com/lumilovesyou/Lumis-Lots/refs/heads/master/readme/wither_bonemeal.png" alt="Bonemeal recoloured with the wither colour palette">
        </details>
    </li>
    <li>
        <details>
        <summary>Deepslate Cobblestone Generators (<a href="https://modrinth.com/mod/etfuturum">EtFuturum</a> necessary)</summary>
        Makes cobblestone/stone generators produce their deepslate equivalent at Y=22 and below
        </details>
    </li>
</ol>

## Gameplay guide
### Compost
Composting dirt can be crafted by placing eight compostable items around a block of dirt. Once placed down it will begin to age into composted dirt, a process which can be sped up by placing a water source nearby. Once aged into composted dirt it can be broken to drop 3-5 bonemeal and a block of dirt. If you're using fortune bonemeal drops follow the formula <code>Drop rate = Y * ⌊1.5 * Fortune level⌋, Y ∈ {3, 4, 5}</code>.<br>
<img src="https://raw.githubusercontent.com/lumilovesyou/Lumis-Lots/refs/heads/master/readme/composting_dirt_recipe.png" style="border-radius: 10px;">
<details>
<summary>Compostable items</summary>
<ul>
<li>Apples</li>
<li>Baked Potatoes</li>
<li>Bread</li>
<li>Brown Mushrooms</li>
<li>Cacti</li>
<li>Eggs</li>
<li>Fish</li>
<li>Flowers</li>
<li>Grass</li>
<li>Hay Blocks</li>
<li>Leaves</li>
<li>Lily Pads</li>
<li>Melon Blocks</li>
<li>Melon Seeds</li>
<li>Melon Slices</li>
<li>Nether Wart</li>
<li>Potatoes</li>
<li>Pumpkins</li>
<li>Pumpkin Seeds</li>
<li>Raw Beefs</li>
<li>Raw Chickens</li>
<li>Raw Porkchops</li>
<li>Red Mushrooms</li>
<li>Rotten Flesh</li>
<li>Saplings</li>
<li>Sugar Cane</li>
<li>Vines</li>
<li>Wheat Seeds</li>
<li>Wheat</li>
</ul>
</details>

### Wither Bones & Bonemeal
When killed a wither skeleton will drop wither bones following the formula `Drop rate = Y + Looting level, Y ∈ {0, 1, 2, 3}`. These bones can be crafted into wither bonemeal. When used on a crop it will reverse the crop's stage, opposite to bonemeal. When placed upon the ground it will remove nearby grass and flower. Finally, if used upon a plant it will either turn it to a bush or in the case of tall grass turn it to short grass.

Note: Eventually wither bonemeal will be useable as black dye

## How to use

Add this mod to your game's `mods` folder along with [Unimixins](https://modrinth.com/mod/unimixins/versions) 0.1.23 or greater.
