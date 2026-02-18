package com.lumi.lots.blocks;

import com.lumi.lots.blocks.BlockDropsHandler.DropAmountFortuneHandler;
import com.lumi.lots.blocks.BlockDropsHandler.DropAmountHandler;
import com.lumi.lots.blocks.BlockDropsHandler.DropMultiItemsHandler;
import com.lumi.lots.blocks.BlockDropsHandler.DropTypeHandler;
import com.lumi.lots.blocks.BlockPlaceHandler.OnBlockAddedHandler;
import com.lumi.lots.blocks.BlockToolHandler.PlayerRelativeBlockHardnessHandler;
import com.lumi.lots.blocks.BlockToolHandler.ToolEffectiveHandler;
import net.minecraft.block.material.MapColor;

import com.lumi.lots.LumisCore.Bounds;

public class BlockBuilder {
    //Basic values
    private int material = 0;
    private String name = "default_value";
    private float hardness = 1.0F;
    private float resistance = 1.0F;
    private String harvestTool = "pickaxe";
    private int harvestLevel = 0;
    private int sound = 0;
    private int creativeTab = -1;

    //Ticking Values
    private boolean ticks = false;
    private BlockTickHandler tickHandler = null;

    //Drop values
    private DropAmountFortuneHandler dropAmountFortuneHandler = null;
    private DropAmountHandler dropAmountHandler = null;
    private DropTypeHandler dropTypeHandler = null;
    private DropMultiItemsHandler dropMultipleItemsHandler = null;

    //Harvest values
    private ToolEffectiveHandler checkEffectiveToolHandler = null;
    private PlayerRelativeBlockHardnessHandler playerRelativeBlockHardnessHandler = null;
    private boolean useToolEffectiveHandler = false;

    //Place values
    private OnBlockAddedHandler onBlockAddedHandler = null;

    //Colour values
    private MapColor mapColour = null;

    //Collision + bounds values
    private boolean setCollisionsDisabled = false;
    private Bounds blockBounds = null;

    public BlockBuilder setMaterial(int material) {
        this.material = material;
        return this;
    }

    public BlockBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public BlockBuilder setHardness(float hardness) {
        this.hardness = hardness;
        return this;
    }

    public BlockBuilder setResistance(float resistance) {
        this.resistance = resistance;
        return this;
    }

    public BlockBuilder setHarvestTool(String harvestTool) {
        this.harvestTool = harvestTool;
        return this;
    }

    public BlockBuilder setHarvestLevel(int harvestLevel) {
        this.resistance = harvestLevel;
        return this;
    }

    public BlockBuilder setSound(int sound) {
        this.sound = sound;
        return this;
    }

    public BlockBuilder setCreativeTab(int creativeTab) {
        this.creativeTab = creativeTab;
        return this;
    }

    public BlockBuilder setTicking(boolean ticks) {
        this.ticks = ticks;
        return this;
    }

    public BlockBuilder setTickHandler(BlockTickHandler tickHandler) {
        this.tickHandler = tickHandler;
        return this;
    }

    public BlockBuilder setFortuneAmountHandler(DropAmountFortuneHandler dropAmountFortuneHandler) {
        this.dropAmountFortuneHandler = dropAmountFortuneHandler;
        return this;
    }

    public BlockBuilder setDropAmountHandler(DropAmountHandler dropAmountHandler) {
        this.dropAmountHandler = dropAmountHandler;
        return this;
    }

    public BlockBuilder setDropTypeHandler(DropTypeHandler dropTypeHandler) {
        this.dropTypeHandler = dropTypeHandler;
        return this;
    }

    public BlockBuilder setDropMultipleItemsHandler(DropMultiItemsHandler dropMultipleItemsHandler) {
        this.dropMultipleItemsHandler = dropMultipleItemsHandler;
        return this;
    }

    public BlockBuilder setPlayerRelativeBlockHardnessHandler(PlayerRelativeBlockHardnessHandler playerRelativeBlockHardnessHandler) {
        this.playerRelativeBlockHardnessHandler = playerRelativeBlockHardnessHandler;
        return this;
    }

    public BlockBuilder setCheckEffectiveToolHandler(ToolEffectiveHandler checkEffectiveToolHandler) {
        this.checkEffectiveToolHandler = checkEffectiveToolHandler;
        return this;
    }

    public BlockBuilder setUseToolEffectiveHandler(boolean useToolEffectiveHandler) {
        this.useToolEffectiveHandler = useToolEffectiveHandler;
        return this;
    }

    public BlockBuilder setOnBlockAddedHandler(OnBlockAddedHandler onBlockAddedHandler) {
        this.onBlockAddedHandler = onBlockAddedHandler;
        return this;
    }

    public BlockBuilder setMapColour(MapColor mapColour) {
        this.mapColour = mapColour;
        return this;
    }

    public BlockBuilder setCollisionsDisabled(boolean setCollisionsDisabled) {
        this.setCollisionsDisabled = setCollisionsDisabled;
        return this;
    }

    public BlockBuilder setBlockBounds(Bounds blockBounds) {
        this.blockBounds = blockBounds;
        return this;
    }

    public LumisBlocks build() {
        return new LumisBlocks(
            material,
            name,
            hardness,
            resistance,
            harvestTool,
            harvestLevel,
            sound,
            creativeTab,
            ticks,
            tickHandler,
            dropAmountFortuneHandler,
            dropAmountHandler,
            dropTypeHandler,
            dropMultipleItemsHandler,
            checkEffectiveToolHandler,
            playerRelativeBlockHardnessHandler,
            useToolEffectiveHandler,
            onBlockAddedHandler,
            mapColour,
            setCollisionsDisabled,
            blockBounds
        );
    }
}
