package com.lumi.lots.renderer;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.init.Blocks;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;

import static com.lumi.lots.LumisCore.cswcRenderType;

public class CrossedSquaresWithCube implements ISimpleBlockRenderingHandler {

    @Override
    public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderer) {
        renderer.renderBlockAsItem(block, metadata, modelId);
    }

    @Override
    public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer) {
        //Stolen right from renderCrossSquares as a base, will work on soon
        Tessellator tessellator = Tessellator.instance;
        tessellator.setBrightness(block.getMixedBrightnessForBlock(world, x, y, z));
        int l = block.colorMultiplier(world, x, y, z);
        float f = (float)(l >> 16 & 255) / 255.0F;
        float f1 = (float)(l >> 8 & 255) / 255.0F;
        float f2 = (float)(l & 255) / 255.0F;

        if (EntityRenderer.anaglyphEnable)
        {
            float f3 = (f * 30.0F + f1 * 59.0F + f2 * 11.0F) / 100.0F;
            float f4 = (f * 30.0F + f1 * 70.0F) / 100.0F;
            float f5 = (f * 30.0F + f2 * 70.0F) / 100.0F;
            f = f3;
            f1 = f4;
            f2 = f5;
        }

        tessellator.setColorOpaque_F(f, f1, f2);
        IIcon iicon = renderer.getBlockIconFromSideAndMetadata(block, 0, world.getBlockMetadata(x, y, z));
        //renderer.drawCrossedSquares(iicon, x, y, z, 1.0F);

        double pointA = (double)iicon.getMinU();
        double pointB = (double)iicon.getMinV();
        double pointC = (double)iicon.getMaxU();
        double pointD = (double)iicon.getMaxV();
        double pointE = 0.5D; //Width of the plane at an angle of the block

        //0.5 is offset from centre of block
        double pointF = x + 0.5D;
        double pointG = x + 0.5D;
        double pointH = z + 0.25D - pointE;
        double pointI = z + 0.25D + pointE;
        tessellator.addVertexWithUV(pointF, y + (double)1.0F, pointH, pointA, pointB);
        tessellator.addVertexWithUV(pointF, y + 0.0D, pointH, pointA, pointD);
        tessellator.addVertexWithUV(pointG, y + 0.0D, pointI, pointC, pointD);
        tessellator.addVertexWithUV(pointG, y + (double)1.0F, pointI, pointC, pointB);
        tessellator.addVertexWithUV(pointG, y + (double)1.0F, pointI, pointA, pointB);
        tessellator.addVertexWithUV(pointG, y + 0.0D, pointI, pointA, pointD);
        tessellator.addVertexWithUV(pointF, y + 0.0D, pointH, pointC, pointD);
        tessellator.addVertexWithUV(pointF, y + (double)1.0F, pointH, pointC, pointB);

        return true;
    }

    @Override
    public boolean shouldRender3DInInventory(int modelId) {
        return false;
    }

    @Override
    public int getRenderId() {
        return cswcRenderType;
    }
}
