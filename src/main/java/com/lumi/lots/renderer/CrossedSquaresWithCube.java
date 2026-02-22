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
    public IBlockAccess blockAccess;

    @Override
    public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderer) {
        renderer.renderBlockAsItem(block, metadata, modelId);
    }

    @Override
    public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer) {
        //Stolen right from renderCrossSquares
        Tessellator tessellator = Tessellator.instance;
        tessellator.setBrightness(block.getMixedBrightnessForBlock(this.blockAccess, x, y, z));
        int l = block.colorMultiplier(this.blockAccess, x, y, z);
        float f = (float)(l >> 16 & 255) / 255.0F;
        float f1 = (float)(l >> 8 & 255) / 255.0F;
        float f2 = (float)(l & 255) / 255.0F;

        tessellator.setColorOpaque_F(f, f1, f2);
        IIcon iicon = renderer.getBlockIconFromSideAndMetadata(block, 0, this.blockAccess.getBlockMetadata(x, y, z));

        if (renderer.hasOverrideBlockTexture())
        {
            iicon = renderer.overrideBlockTexture;
        }

        double d3 = (double)iicon.getMinU();
        double d4 = (double)iicon.getMinV();
        double d5 = (double)iicon.getMaxU();
        double d6 = (double)iicon.getMaxV();
        double d7 = 0.45D * (double)1.0F;
        double d8 = (double)x + 0.5D - d7;
        double d9 = (double)x + 0.5D + d7;
        double d10 = (double)z + 0.5D - d7;
        double d11 = (double)z + 0.5D + d7;
        tessellator.addVertexWithUV(d8, (double)y + (double)1.0F, d10, d3, d4);
        tessellator.addVertexWithUV(d8, (double)y + 0.0D, d10, d3, d6);
        tessellator.addVertexWithUV(d9, (double)y + 0.0D, d11, d5, d6);
        tessellator.addVertexWithUV(d9, (double)y + (double)1.0F, d11, d5, d4);
        tessellator.addVertexWithUV(d9, (double)y + (double)1.0F, d11, d3, d4);
        tessellator.addVertexWithUV(d9, (double)y + 0.0D, d11, d3, d6);
        tessellator.addVertexWithUV(d8, (double)y + 0.0D, d10, d5, d6);
        tessellator.addVertexWithUV(d8, (double)y + (double)1.0F, d10, d5, d4);
        tessellator.addVertexWithUV(d8, (double)y + (double)1.0F, d11, d3, d4);
        tessellator.addVertexWithUV(d8, (double)y + 0.0D, d11, d3, d6);
        tessellator.addVertexWithUV(d9, (double)y + 0.0D, d10, d5, d6);
        tessellator.addVertexWithUV(d9, (double)y + (double)1.0F, d10, d5, d4);
        tessellator.addVertexWithUV(d9, (double)y + (double)1.0F, d10, d3, d4);
        tessellator.addVertexWithUV(d9, (double)y + 0.0D, d10, d3, d6);
        tessellator.addVertexWithUV(d8, (double)y + 0.0D, d11, d5, d6);
        tessellator.addVertexWithUV(d8, (double)y + (double)1.0F, d11, d5, d4);
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
