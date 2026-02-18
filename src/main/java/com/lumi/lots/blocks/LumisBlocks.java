package com.lumi.lots.blocks;

import com.lumi.lots.blocks.BlockDropsHandler.DropAmountFortuneHandler;
import com.lumi.lots.blocks.BlockDropsHandler.DropAmountHandler;
import com.lumi.lots.blocks.BlockDropsHandler.DropMultiItemsHandler;
import com.lumi.lots.blocks.BlockDropsHandler.DropTypeHandler;
import com.lumi.lots.blocks.BlockPlaceHandler.OnBlockAddedHandler;
import com.lumi.lots.blocks.BlockToolHandler.PlayerRelativeBlockHardnessHandler;
import com.lumi.lots.blocks.BlockToolHandler.ToolEffectiveHandler;
import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.Random;

import static com.lumi.lots.LumisCore.tabs;
import com.lumi.lots.LumisCore.Bounds;

public class LumisBlocks extends Block {
    //Ticking values
    private final BlockTickHandler tickHandler;

    //Drop values
    private final DropAmountFortuneHandler dropAmountFortuneHandler;
    private final DropAmountHandler dropAmountHandler;
    private final DropTypeHandler dropTypeHandler;
    private final DropMultiItemsHandler dropMultipleItemsHandler;

    //Harvest values
    private final ToolEffectiveHandler checkEffectiveToolHandler;
    private final PlayerRelativeBlockHardnessHandler playerRelativeBlockHardnessHandler;
    private final Boolean useToolEffectiveHandler;

    //Place values
    private final OnBlockAddedHandler onBlockAddedHandler;

    //Colour values
    private final MapColor mapColour;

    //Collision values
    private final boolean disableCollision;

    //Static
    private static final Material[] materials = {Material.air,
            Material.anvil,
            Material.cactus,
            Material.cake,
            Material.carpet,
            Material.circuits,
            Material.clay,
            Material.cloth,
            Material.coral,
            Material.craftedSnow,
            Material.dragonEgg,
            Material.fire,
            Material.glass,
            Material.gourd,
            Material.grass,
            Material.ground,
            Material.ice,
            Material.iron,
            Material.lava,
            Material.leaves,
            Material.packedIce,
            Material.piston,
            Material.plants,
            Material.portal,
            Material.redstoneLight,
            Material.rock,
            Material.sand,
            Material.snow,
            Material.sponge,
            Material.tnt,
            Material.vine,
            Material.water,
            Material.web,
            Material.wood
    };

    private static final SoundType[] sounds = {
            Block.soundTypeAnvil,
            Block.soundTypeCloth,
            Block.soundTypeGlass,
            Block.soundTypeGrass,
            Block.soundTypeGravel,
            Block.soundTypeLadder,
            Block.soundTypeMetal,
            Block.soundTypePiston,
            Block.soundTypeSand,
            Block.soundTypeSnow,
            Block.soundTypeStone,
            Block.soundTypeWood
    };

    public LumisBlocks(
            int material,
            String name,
            float hardness,
            float resistance,
            String harvestTool,
            int harvestLevel,
            int sound,
            int creativeTab,
            boolean ticks,
            BlockTickHandler tickHandler,
            DropAmountFortuneHandler dropAmountFortuneHandler,
            DropAmountHandler dropAmountHandler,
            DropTypeHandler dropTypeHandler,
            DropMultiItemsHandler dropMultipleItemsHandler,
            ToolEffectiveHandler checkEffectiveToolHandler,
            PlayerRelativeBlockHardnessHandler playerRelativeBlockHardnessHandler,
            boolean useToolEffectiveHandler,
            OnBlockAddedHandler onBlockAddedHandler,
            MapColor mapColour,
            boolean disableCollision,
            Bounds blockBounds
    ) {
        //Basic values
        super(materials[material]);
        name = name.toLowerCase().replace(" ", "_");
        this.setBlockName(name);
        this.setBlockTextureName(String.format("lumis_lots:%s", name));
        this.setHardness(hardness);
        this.setResistance(resistance);
        this.setHarvestLevel(harvestTool, harvestLevel);
        this.setStepSound(sounds[sound]);
        if (creativeTab > -1) {
            this.setCreativeTab(tabs[creativeTab]);
        }

        //Ticking values
        if (ticks) {
            this.setTickRandomly(true);
        }
        this.tickHandler = tickHandler;

        //Drop values
        this.dropAmountFortuneHandler = dropAmountFortuneHandler;
        this.dropAmountHandler = dropAmountHandler;
        this.dropTypeHandler = dropTypeHandler;
        this.dropMultipleItemsHandler = dropMultipleItemsHandler;

        //Harvest values
        this.checkEffectiveToolHandler = checkEffectiveToolHandler;
        this.playerRelativeBlockHardnessHandler = playerRelativeBlockHardnessHandler;
        this.useToolEffectiveHandler = useToolEffectiveHandler;

        //Place values
        this.onBlockAddedHandler = onBlockAddedHandler;

        //Colour values
        this.mapColour = mapColour;

        //Collision + bounds values
        this.disableCollision = disableCollision;
        if (blockBounds != null) {
            this.setBlockBounds(blockBounds.x1, blockBounds.y1, blockBounds.z1, blockBounds.x2, blockBounds.y2, blockBounds.z2);
        }
    }

    @Override
    public void updateTick(World world, int x, int y, int z, Random rand) {
        if (tickHandler != null && !world.isRemote) {
            tickHandler.onTick(world, x, y, z, rand);
        }
    }

    @Override
    public int quantityDroppedWithBonus(int fortune, Random rand) {
        if (dropAmountFortuneHandler != null) {
            return dropAmountFortuneHandler.onDropFortune(fortune, rand);
        } else {
            return 1;
        }
    }

    @Override
    public int quantityDropped(Random rand) {
        if (dropAmountHandler != null) {
            return dropAmountHandler.onDrop(rand);
        } else {
            return 1;
        }
    }

    @Override
    public Item getItemDropped(int metadata, Random rand, int fortune) {
        if (dropTypeHandler != null) {
            return dropTypeHandler.onGetDropItem(metadata, rand, fortune);
        } else {
            return super.getItemDropped(metadata, rand, fortune);
        }
    }

    @Override
    public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune) {
        if (dropMultipleItemsHandler != null) {
            return dropMultipleItemsHandler.onGetMultiDropItems(world, x, y, z, metadata, fortune);
        } else {
            return super.getDrops(world, x, y, z, metadata, fortune);
        }
    }

    @Override
    public String getHarvestTool(int metadata) {
        if (useToolEffectiveHandler) {
            return null;
        } else {
            return super.getHarvestTool(metadata);
        }
    }

    @Override
    public int getHarvestLevel(int metadata) {
        if (useToolEffectiveHandler) {
            return 0;
        } else {
            return super.getHarvestLevel(metadata);
        }
    }

    @Override
    public boolean isToolEffective(String type, int metadata) {
        if (checkEffectiveToolHandler != null) {
            return checkEffectiveToolHandler.onCheckToolEffective(type, metadata);
        } else {
            return super.isToolEffective(type, metadata);
        }
    }

    @Override
    public float getPlayerRelativeBlockHardness(EntityPlayer player, World world, int x, int y, int z) {
        float originalValue = super.getPlayerRelativeBlockHardness(player, world, x, y, z);
         if (playerRelativeBlockHardnessHandler != null) {
             float newValue = playerRelativeBlockHardnessHandler.onGetPlayerRelativeBlockHardness(player, originalValue);
             if (newValue < 0.0F) {
                return originalValue;
             } else {
                return newValue;
             }
         } else {
             return originalValue;
         }
    }

    @Override
    public void onBlockAdded(World world, int x, int y, int z) {
        if (onBlockAddedHandler != null) {
            onBlockAddedHandler.onBlockAdded(world, x, y, z);
        } else {
            super.onBlockAdded(world, x, y, z);
        }
    }

    @Override
    public MapColor getMapColor(int p_149728_1_) {
        if (mapColour != null) {
            return mapColour;
        } else {
            return super.getMapColor(p_149728_1_);
        }
    }

    @Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z) {
        if (disableCollision) {
            return null;
        } else {
            return super.getCollisionBoundingBoxFromPool(world, x, y, z);
        }
    }

    @Override
    public boolean isOpaqueCube()
    {
        return false;
    }

    @Override
    public boolean renderAsNormalBlock()
    {
        return false;
    }

    @Override
    public int getRenderType()
    {
        return 1;
    }

    //getPlantType function in BlockBush.java
    //getPlant
    //getPlantMetadata
    //canBlockStay
    //checkAndDropBlock
    //updateTick
    //onNeighborBlockChange
    //canPlaceBlockOn
    //canPlaceBlockAt
    //
}
