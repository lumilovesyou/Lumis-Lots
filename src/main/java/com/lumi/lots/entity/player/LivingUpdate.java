package com.lumi.lots.entity.player;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.event.entity.living.LivingEvent;

import static com.lumi.lots.LumisCore.ring;

public class LivingUpdate {
    @SubscribeEvent
    public void onLivingUpdate(LivingEvent.LivingUpdateEvent event) {
        //Return if not player and not server side
        if (!(event.entity instanceof EntityPlayer)) return;
        if (event.entity.worldObj.isRemote) return;

        //Get player
        EntityPlayer player = (EntityPlayer) event.entity;

        //Check if player has ring in hotbar
        for (int i = 0; i < 9; i++) {
            ItemStack item = player.inventory.getStackInSlot(i);
            if (item != null && item.getItem() == ring) {
                //Applies potion effect
                player.addPotionEffect(new PotionEffect(Potion.moveSpeed.id, 2, 0, true));
            }
        }
    }
}
