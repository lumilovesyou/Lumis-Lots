package com.lumi.lots.audio.music.overwrite;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.MusicTicker;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.util.MathHelper;

import static com.lumi.lots.LumisCore.config;
import static com.lumi.lots.LumisCore.lumiRand;

public class Music extends MusicTicker {
    private final Minecraft mc;
    private ISound currentMusic;
    private int cooldown = 0;

    public Music(Minecraft mc) {
        super(mc);
        this.mc = mc;
    }

    @Override
    public void update()
    {
        MusicTicker.MusicType musicType = mc.func_147109_W();

        if (currentMusic != null)
        {
            if (!musicType.getMusicTickerLocation().equals(currentMusic.getPositionedSoundLocation()))
            {
                mc.getSoundHandler().stopSound(currentMusic);
                cooldown = MathHelper.getRandomIntegerInRange(lumiRand, config.musicCooldownTimeRange[0], config.musicCooldownTimeRange[1] / 2);
            }

            if (!mc.getSoundHandler().isSoundPlaying(currentMusic))
            {
                currentMusic = null;
                cooldown = Math.min(MathHelper.getRandomIntegerInRange(lumiRand, config.musicCooldownTimeRange[1], config.musicCooldownTimeRange[2]), cooldown);
            }
        }

        if (currentMusic == null && cooldown-- <= 0)
        {
            currentMusic = PositionedSoundRecord.func_147673_a(musicType.getMusicTickerLocation());
            mc.getSoundHandler().playSound(currentMusic);
            cooldown = Integer.MAX_VALUE;
        }
    }
}
