package com.lumi.lots.mixins.client.renderer;

import com.lumi.lots.LumisCore;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderBlocks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


@Mixin(RenderBlocks.class)
public class RenderBlocksOverwrite {
    /**
     * @author LumiLovesYou
     * @reason To hide an item in the player's hand
     */
    @Inject(
        method = "func_147800_a(Lnet/minecraft/block/Block;IF)V",
        at = @At("HEAD"),
        cancellable = true,
        remap = false
    )
    private static void renderBlockAsItem(Block p_147800_1_, int p_147800_2_, float p_147800_3_, CallbackInfo ci) {
        if (LumisCore.config.firstPersonModel) {
            Minecraft mc = Minecraft.getMinecraft();
            if (mc.currentScreen == null || !mc.inGameHasFocus) {
                return;
            }

            ci.cancel();
        }
    }


}
