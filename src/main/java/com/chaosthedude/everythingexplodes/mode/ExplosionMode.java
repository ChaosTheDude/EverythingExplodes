package com.chaosthedude.everythingexplodes.mode;

import com.chaosthedude.everythingexplodes.util.Util;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ExplosionMode {
	
	public String unlocName;
	public String unlocDescription;
	
	public ExplosionMode(String unlocName, String unlocDescription) {
		this.unlocName = unlocName;
		this.unlocDescription = unlocDescription;

		ExplosionModes.REGISTRY.add(this);
	}
	
	public boolean shouldExplode(EntityPlayer player) {
		return false;
	}
	
	@SideOnly(Side.CLIENT)
	public String getLocalizedName() {
		return Util.localize(unlocName);
	}
	
	@SideOnly(Side.CLIENT)
	public String getLocalizedDescription() {
		return Util.localize(unlocDescription);
	}

}
