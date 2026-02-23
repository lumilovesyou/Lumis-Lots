package com.lumi.lots.mixins.client.jsonModel;

import net.dragon.jsonmodel.RenderJsonBlockModel;
import net.minecraft.block.Block;
import net.minecraft.world.IBlockAccess;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(RenderJsonBlockModel.class)
public class PatchJsonModels {
    /**
     * @author LumiLovesYou
     * @reason Patch crash
     */
    @Overwrite
    private static int sampleBrightnessByNormal(IBlockAccess w, int x, int y, int z, double[] n, Block b) {
        double ax = Math.abs(n[0]);
        double ay = Math.abs(n[1]);
        double az = Math.abs(n[2]);
        if (ay >= ax && ay >= az) {
            return w.getLightBrightnessForSkyBlocks(x, y + (n[1] > 0.0 ? 1 : -1), z, 0);
        } else if (ax >= ay && ax >= az) {
            return w.getLightBrightnessForSkyBlocks(x + (n[0] > 0.0 ? 1 : -1), y, z, 0);
        } else {
            return w.getLightBrightnessForSkyBlocks(x, y, z + (n[2] > 0.0 ? 1 : -1), 0);
        }
    }
}
