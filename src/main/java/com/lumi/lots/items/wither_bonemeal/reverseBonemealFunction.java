package com.lumi.lots.items.wither_bonemeal;

import com.lumi.lots.items.wither_bonemeal.events.ReverseBonemealEvent;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;

import java.util.Random;

public class reverseBonemealFunction {
    public static boolean reverseBonemeal(ItemStack item, World world, int x, int y, int z, EntityPlayer player)
    {
        Block block = world.getBlock(x, y, z);

        ReverseBonemealEvent event = new ReverseBonemealEvent(player, world, block, x, y, z);
        if (MinecraftForge.EVENT_BUS.post(event))
        {
            return false;
        }
        //Remove flowers
        if (block == Blocks.yellow_flower || block == Blocks.red_flower) {
            if (!world.isRemote) {
                world.setBlock(x, y, z, Blocks.air);
                decrementItem(item, world);
            }
            return true;
        }

        //Turns tall grass into short grass and other tall plants into bush
        if (block instanceof IGrowable) {
            IGrowable igrowable = (IGrowable)block;
            int metadata = world.getBlockMetadata(x, y, z);

            if (block instanceof BlockDoublePlant) {
                if ((metadata & 8) != 0) { //Check if top block and shift down by one
                    y--;
                    metadata = world.getBlockMetadata(x, y, z);
                }

                //Check if tall grass/fern (bottom) or not
                if (metadata == 2) {
                    world.setBlock(x, y, z, Blocks.tallgrass, 1, 2);
                } else if (metadata == 3) {
                    world.setBlock(x, y, z, Blocks.tallgrass, 2, 2);
                } else {
                    world.setBlock(x, y, z, Blocks.tallgrass);
                }
                world.setBlock(x, y + 1, z, Blocks.air);
                decrementItem(item, world);

                return true;
            }

            //Turns grass & fern into bush
            if (block instanceof BlockTallGrass) {
                if (metadata > 0) {
                    if (!world.isRemote) {
                        world.setBlock(x, y, z, Blocks.tallgrass);
                        decrementItem(item, world);
                    }
                    return true;
                }
                return false;
            }

            //Removes grass & plants in an area
            if (block == Blocks.grass) {
                if (!world.isRemote) {
                    Random rand = new Random();
                    for (int i = 0; i < 128; i++) {
                        int x2 = x;
                        int y2 = y + 1;
                        int z2 = z;
                        int j = 0;

                        while (true) {
                            if (j < i / 16) {
                                x2 += rand.nextInt(3) - 1;
                                y2 += (rand.nextInt(3) - 1) * rand.nextInt(3) / 2;
                                z2 += rand.nextInt(3) - 1;

                                if (world.getBlock(x2, y2 - 1, z2) == Blocks.grass && !world.getBlock(x2, y2, z2).isNormalCube()) {
                                    j++;
                                    continue;
                                }
                            } else {
                                Material material = world.getBlock(x2, y2, z2).getMaterial();
                                if (material == Material.plants || material == Material.grass || material == Material.vine) {
                                    world.setBlock(x2, y2, z2, Blocks.air);
                                }
                            }
                            break;
                        }
                    }
                }
                decrementItem(item, world);
                return true;
            }

            //Reverse stages of plants
            if ((block instanceof BlockCrops || block instanceof BlockStem || block instanceof BlockCocoa) && metadata != 0) {
                if (!world.isRemote) {
                    if (igrowable.func_149852_a(world, world.rand, x, y, z)) {
                        int l = metadata - MathHelper.getRandomIntegerInRange(world.rand, 2, 5);
                        if (l < 0) {
                            l = 0;
                        }

                        world.setBlockMetadataWithNotify(x, y, z, l, 2);
                    }
                    decrementItem(item, world);
                }
                return true;
            }
        }
        return false;
    }

    private static void decrementItem(ItemStack item, World world) {
        if (!world.isRemote)
        {
            item.stackSize--;
        }
    }
}
