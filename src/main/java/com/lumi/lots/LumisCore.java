package com.lumi.lots;

import com.lumi.lots.audio.music.DisplayPlayingTrackName;
import com.lumi.lots.audio.music.overwrite.Music;
import com.lumi.lots.blocks.BlockBuilder;
import com.lumi.lots.blocks.BlockDropsHandler.DropMultiItemsHandler;
import com.lumi.lots.blocks.BlockPlaceHandler.OnBlockAddedHandler;
import com.lumi.lots.blocks.BlockTickHandler;
import com.lumi.lots.blocks.overwrite.Leaves;
import com.lumi.lots.config.Config;
import com.lumi.lots.gui.MovementHandler;
import com.lumi.lots.gui.TextFieldFocusChecks.TextFieldFocus;
import com.lumi.lots.items.ItemBuilder;
import com.lumi.lots.items.ItemMetaDataHandler;
import com.lumi.lots.items.ItemUseHandler.OnItemUseHandler;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.ReflectionHelper;
import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.WeightedRandomFishable;
import net.minecraft.world.World;
import net.minecraftforge.common.FishingHooks;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Random;

import static com.lumi.lots.items.wither_bonemeal.reverseBonemealFunction.reverseBonemeal;

@Mod(modid = LumisCore.MOD_ID, version = LumisCore.MOD_VERSION)
public class LumisCore
{
    //Setup stuffs
    public static final String MOD_ID = "lumis_lots";
    public static final String MOD_VERSION = "@VERSION@";
    private static final Logger logger = LogManager.getLogger(MOD_ID);
    public static Config config;

    //Shared variables
    public static Random lumiRand = new Random();
    public boolean etFuturumInstalled;
    public static final CreativeTabs[] tabs = {CreativeTabs.tabAllSearch, CreativeTabs.tabBlock, CreativeTabs.tabBrewing, CreativeTabs.tabCombat, CreativeTabs.tabDecorations, CreativeTabs.tabFood, CreativeTabs.tabInventory, CreativeTabs.tabMaterials, CreativeTabs.tabMisc, CreativeTabs.tabRedstone, CreativeTabs.tabTools, CreativeTabs.tabTransport};

    public class Bounds {
        public float x1, y1, z1, x2, y2, z2;

        Bounds(float x1, float y1, float z1, float x2, float y2, float z2) {
            this.x1 = x1;
            this.y1 = y1;
            this.z1 = z1;
            this.x2 = x2;
            this.y2 = y2;
            this.z2 = z2;
        }
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        System.out.println("Lumi says \"Hello Forge world!\"");
        logger.info("Lumi says \"Hello Forge world!\"");

        if (event.getSide().isClient()) {
            if (config.invMovement) {
                //Inventory movement
                FMLCommonHandler.instance().bus().register(new TextFieldFocus());
                FMLCommonHandler.instance().bus().register(new MovementHandler());
            }

            //Music display
            if (config.displayTrackName) {
                MinecraftForge.EVENT_BUS.register(new DisplayPlayingTrackName());
            }

            //Customised music cooldown
            if (config.customMusicCooldown) {
                Minecraft mc = Minecraft.getMinecraft();
                try {
                    Field tickerField = ReflectionHelper.findField(Minecraft.class, "mcMusicTicker", "field_147126_aw");
                    tickerField.setAccessible(true);
                    Field modifiersField = Field.class.getDeclaredField("modifiers");
                    modifiersField.setAccessible(true);
                    modifiersField.setInt(tickerField, tickerField.getModifiers() & ~Modifier.FINAL);
                    tickerField.set(mc, new Music(mc));
                } catch (NoSuchFieldException | IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }

            //Sponge overwrite and fishing loot table
            if (config.spongeBackport) {
                Block sponge = Blocks.sponge;
                sponge.setHarvestLevel("shears", 0);
                sponge.setCreativeTab(tabs[1]);
                sponge.setResistance(0.6f);
                sponge.setStepSound(Block.soundTypeCloth);

                FishingHooks.addTreasure(new WeightedRandomFishable(new ItemStack(wetSponge), 1));
            }
        }

        //Leaves broken by hoes
        MinecraftForge.EVENT_BUS.register(new Leaves());
    }

    @Mod.Instance(MOD_ID)
    public static LumisCore INSTANCE;

    public static Block compostingDirt;
    public static Block compostedDirt;
    public static Block veiledLady;
    public static Block wetSponge;

    public static Item witherBone;
    public static Item witherBonemeal;
    public static Item ring;

    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        etFuturumInstalled = Loader.isModLoaded("etfuturum");
    }

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        //Config
        config = Config.load();
        config.save();

        //Blocks
        compostingDirt = new BlockBuilder()
                .setName("Composting Dirt")
                .setMaterial(15)
                .setSound(4)
                .setCreativeTab(4)
                .setResistance(0.5f)
                .setHardness(0.5f)
                .setHarvestTool("shovel")
                .setTicking(true)
                .setMapColour(MapColor.brownColor)
                .setTickHandler(new BlockTickHandler() {
                    @Override
                    public void onTick(World world, int x, int y, int z, Random rand) {
                        if (world.isAirBlock(x, y + 1, z)) {
                            boolean nearbyWater = false;
                            int chance = 0;
                            for (int i = x - 1; i <= x + 1; i++) {
                                for (int j = z - 1; j <= z + 1; j++) {
                                    if (world.getBlock(i, y, j).isAssociatedBlock(Blocks.water)) {
                                        nearbyWater = true;
                                        break;
                                    }
                                }
                                if (nearbyWater) {
                                    break;
                                }
                            }
                            if (world.isRaining() || nearbyWater) {
                                chance = rand.nextInt(10);
                            } else {
                                chance = rand.nextInt(25);
                            }
                            if (chance == 0) {
                                world.setBlock(x, y, z, compostedDirt);
                            }
                        }
                    }
                })
                .build();

        compostedDirt = new BlockBuilder()
                .setName("Composted Dirt")
                .setMaterial(15)
                .setSound(3)
                .setCreativeTab(4)
                .setResistance(0.5f)
                .setHardness(0.5f)
                .setHarvestTool("shovel")
                .setMapColour(MapColor.woodColor)
                .setDropMultipleItemsHandler(new DropMultiItemsHandler() {
                    @Override
                    public ArrayList<ItemStack> onGetMultiDropItems(World world, int x, int y, int z, int metadata, int fortune) {
                        ArrayList<ItemStack> drops = new ArrayList<>();
                        drops.add(new ItemStack(Blocks.dirt, 1, 0));
                        for (int i = 0; i < ((3 + world.rand.nextInt(3)) + Math.floor(fortune*1.5)); i++) {
                            drops.add(new ItemStack(Items.dye, 1, 15));
                        }
                        return drops;
                    }
                })
                .build();

        veiledLady = new BlockBuilder()
                //Do tick events soon!
                //Add block placement requirements
                //Make break if block below broken
                //Make bonemeal usable on
                //Add custom model too!
                .setName("Veiled Lady")
                .setMaterial(20)
                .setSound(3)
                .setCreativeTab(4)
                .setHardness(0.0f)
                .setResistance(0.0f)
                .setCollisionsDisabled(true)
                .setBlockBounds(new Bounds(0.3F, 0.0F, 0.3F, 0.7F, 0.7F, 0.7F))
                .setCubeOpaque(false)
                .setRenderType(1)
                .setCollisionsDisabled(true)
                .build();

        wetSponge = new BlockBuilder()
                .setName("Wet Sponge")
                .setMaterial(8)
                .setSound(1)
                .setCreativeTab(config.spongeBackport ? 8 : -1)
                .setResistance(0.6f)
                .setHardness(0.6f)
                .setHarvestTool("shears")
                .setMapColour(MapColor.greenColor)
                .setOnBlockAddedHandler(new OnBlockAddedHandler() {
                    @Override
                    public void onBlockAdded(World world, int x, int y, int z) {
                        if (world.provider.isHellWorld) {
                            world.setBlock(x, y, z, Blocks.sponge);
                            world.playAuxSFX(2007, x, y, z, 0);
                        }
                    }
                })
                .build();

        GameRegistry.registerBlock(compostingDirt, "composting_dirt");
        GameRegistry.registerBlock(compostedDirt, "composted_dirt");
        GameRegistry.registerBlock(veiledLady, "veiled_lady");
        GameRegistry.registerBlock(wetSponge, "wet_sponge");

        //Items
        witherBone = new ItemBuilder()
                .setName("Wither Bone")
                .setCreativeTab(8)
                .build();

        witherBonemeal = new ItemBuilder()
                .setName("Wither Bonemeal")
                .setCreativeTab(7)
                .setItemUseHandler(new OnItemUseHandler() {
                    @Override
                    public boolean onItemUse(ItemStack item, EntityPlayer player, World world, int x, int y, int z, int p_77648_7_, float p_77648_8_, float p_77648_9_, float p_77648_10_) {
                        if (!player.canPlayerEdit(x, y, z, p_77648_7_, item)) return false;
                        if (reverseBonemeal(item, world, x, y, z, player)) {
                            if (world.isRemote) {
                                Block block = world.getBlock(x, y, z);

                                if (block.getMaterial() != Material.air) {
                                    block.setBlockBoundsBasedOnState(world, x, y, z);

                                    for (int i1 = 0; i1 < 15; ++i1) {
                                        double d0 = lumiRand.nextGaussian() * 0.02D;
                                        double d1 = lumiRand.nextGaussian() * 0.02D;
                                        double d2 = lumiRand.nextGaussian() * 0.02D;
                                        world.spawnParticle("witchMagic", ((float)x + lumiRand.nextFloat()), (double)y + (double)lumiRand.nextFloat() * block.getBlockBoundsMaxY(), ((float)z + lumiRand.nextFloat()), d0, d1, d2);
                                    }
                                }
                                else {
                                    for (int i1 = 0; i1 < 15; ++i1) {
                                        double d0 = lumiRand.nextGaussian() * 0.02D;
                                        double d1 = lumiRand.nextGaussian() * 0.02D;
                                        double d2 = lumiRand.nextGaussian() * 0.02D;
                                        world.spawnParticle("witchMagic", ((float)x + lumiRand.nextFloat()), (double)y + (double)lumiRand.nextFloat() * 1.0f, ((float)z + lumiRand.nextFloat()), d0, d1, d2);
                                    }
                                }
                            }

                            return true;
                        }
                        return false;
                    }
                })
                .build();

        ////Rings
        ring = new ItemBuilder()
                .setName("ring")
                .setHasSubtypes(true)
                .setCombinedMetadataHandler(new ItemMetaDataHandler.CombinedMetadataHandler() {
                    final IIcon[] icons = new IIcon[4];

                    @Override
                    public void onRegisterIcons(IIconRegister register) {
                        icons[0] = register.registerIcon("lumis_lots:rings/ring_gold_diamond");
                        icons[1] = register.registerIcon("lumis_lots:rings/ring_gold_emerald");
                        icons[2] = register.registerIcon("lumis_lots:rings/ring_iron_diamond");
                        icons[3] = register.registerIcon("lumis_lots:rings/ring_iron_emerald");
                    }

                    @Override
                    public IIcon onGetIconFromMetadata(int metadata) {
                        if (metadata < 0 || metadata >= icons.length) metadata = 0;
                        return icons[metadata];
                    }

                    @Override
                    public String onGetUnlocalisedName(ItemStack stack) {
                        int meta = stack.getItemDamage();
                        switch (meta) {
                            case 0: return "item.ring_gold_diamond";
                            case 1: return "item.ring_gold_emerald";
                            case 2: return "item.ring_iron_diamond";
                            case 3: return "item.ring_iron_emerald";
                            default: return "item.ring_unassigned";
                        }
                    }
                })
                .build();
        ////

        GameRegistry.registerItem(witherBone, "wither_bone");
        GameRegistry.registerItem(witherBonemeal, "wither_bonemeal");
        GameRegistry.registerItem(ring, "ring");

        //Tags
        Block[] compostableBlocks = {Blocks.pumpkin, Blocks.melon_block, Blocks.cactus, Blocks.hay_block, Blocks.vine, Blocks.waterlily, Blocks.red_mushroom, Blocks.brown_mushroom, Blocks.yellow_flower};
        for (Block block : compostableBlocks) {
            OreDictionary.registerOre("compostable", new ItemStack(block));
        }
        for (int i = 0; i < 4; i++) { //Tree variants
            OreDictionary.registerOre("compostable", new ItemStack(Blocks.sapling, 1, i));
            OreDictionary.registerOre("compostable", new ItemStack(Blocks.leaves, 1, i));
            OreDictionary.registerOre("compostable", new ItemStack(Blocks.leaves2, 1, i));
        }
        for (int i = 0; i < 9; i++) { //Flower variants
            OreDictionary.registerOre("compostable", new ItemStack(Blocks.red_flower, 1, i));
        }
        for (int i = 0; i < 3; i++) { //Grass variants
            OreDictionary.registerOre("compostable", new ItemStack(Blocks.tallgrass, 1, i));
        }
        for (int i = 0; i < 6; i++) { //Double plant varials
            OreDictionary.registerOre("compostable", new ItemStack(Blocks.double_plant, 1, i));
        }
        Item[] compostableItems = {Items.rotten_flesh, Items.apple, Items.bread, Items.chicken, Items.porkchop, Items.beef, Items.egg, Items.wheat, Items.wheat_seeds, Items.melon_seeds, Items.nether_wart, Items.pumpkin_seeds, Items.melon, Items.potato, Items.baked_potato, Items.reeds};
        for (Item item : compostableItems) {
            OreDictionary.registerOre("compostable", new ItemStack(item));
        }
        for (int i = 0; i < 4; i++) { //Fish variants
            OreDictionary.registerOre("compostable", new ItemStack(Items.fish, 1, i));
        }

        //Makes wither bonemeal work like black dye
        OreDictionary.registerOre("dyeBlack", new ItemStack(witherBonemeal, 1));

        //Recipes
        GameRegistry.addRecipe(new ShapedOreRecipe(
            new ItemStack(compostingDirt, 1),
            "AAA", "ABA", "AAA",
            'A', "compostable",
            'B', Blocks.dirt
        ));
        GameRegistry.addRecipe(new ShapelessOreRecipe(
            new ItemStack(witherBonemeal, 2),
            witherBone
        ));
        GameRegistry.addSmelting(
            new ItemStack(wetSponge, 1),
            new ItemStack(Blocks.sponge, 1),
            0.15F
        );
    }
}
