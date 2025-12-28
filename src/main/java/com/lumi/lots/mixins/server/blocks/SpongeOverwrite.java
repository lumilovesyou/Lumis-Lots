package com.lumi.lots.mixins.server.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockSponge;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;

import java.util.*;

import static com.lumi.lots.LumisCore.config;
import static com.lumi.lots.LumisCore.wetSponge;

@Mixin(BlockSponge.class)
public class SpongeOverwrite extends Block {
    protected SpongeOverwrite(Material p_i45394_1_) {
        super(p_i45394_1_);
    }

    /**
     * @author LumiLovesYou
     * @reason Set map colour for sponge
     */
    @Override
    public MapColor getMapColor(int p_149728_1_) {
        if (config.spongeBackport) {
            return MapColor.yellowColor;
        }
        return super.getMapColor(p_149728_1_);
    }

    /**
     * @author LumiLovesYou
     * @reason Sponge absorb water functionality
     */
    @Override()
    public void onBlockAdded(World world, int x, int y, int z) {
        if (config.spongeBackport) {
            if (absorbWater(world, x, y, z)) {
                world.setBlock(x, y, z, wetSponge);
                world.playAuxSFX(2001, x, y, z, Block.getIdFromBlock(Blocks.water));
            }
            return;
        }
        super.onBlockAdded(world, x, y, z);
    }

    private boolean absorbWater(World world, int x, int y, int z) {
        Queue<int[]> queue = new LinkedList<>();
        List<int[]> water = new ArrayList<>();
        Set<Long> visited = new HashSet<>();

        queue.add(new int[]{x, y, z});
        visited.add(createHashFromCoordinates(x, y, z));

        int[][] directions = new int[][]{
            {1, 0, 0}, {-1, 0, 0}, {0, 1, 0}, {0, -1, 0}, {0, 0, 1}, {0, 0, -1}
        };

        while (!queue.isEmpty() && water.size() < 118) {
            int[] pos = queue.poll();
            Block currentBlock = world.getBlock(pos[0], pos[1], pos[2]);

            if (currentBlock == Blocks.water || currentBlock == Blocks.flowing_water) {
                water.add(pos);
                world.setBlockToAir(pos[0], pos[1], pos[2]);
            }

            for (int[] direction : directions) {
                int newX = pos[0] + direction[0];
                int newY = pos[1] + direction[1];
                int newZ = pos[2] + direction[2];
                long hash = createHashFromCoordinates(newX, newY, newZ);

                if (!visited.contains(hash) && Math.abs(x - newX) + Math.abs(y - newY) + Math.abs(z - newZ) <= 7) {
                    visited.add(hash);
                    Block newBlock = world.getBlock(newX, newY, newZ);
                    if (newBlock == Blocks.water || newBlock == Blocks.flowing_water) {
                        queue.add(new int[]{newX, newY, newZ});
                    }
                }
            }
        }

        return !water.isEmpty();
    }

    private long createHashFromCoordinates(int x, int y, int z) {
        return ((long)x << 40) | ((long)y << 20) | z;
    }
}
