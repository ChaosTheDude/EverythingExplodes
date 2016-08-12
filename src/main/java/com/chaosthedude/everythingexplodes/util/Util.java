package com.chaosthedude.everythingexplodes.util;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.chaosthedude.everythingexplodes.config.EverythingExplodesConfig;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class Util {

	public static List<Entity> getCollidedEntities(EntityLivingBase entityLiving) {
		List<Entity> collidedEntities = new ArrayList<Entity>();
		if (entityLiving != null) {
			for (Entity loadedEntity : entityLiving.worldObj.loadedEntityList) {
				if (loadedEntity != null && loadedEntity != entityLiving
						&& entityLiving.getEntityBoundingBox().intersectsWith(loadedEntity.getEntityBoundingBox())) {
					collidedEntities.add(loadedEntity);
				}
			}
		}

		return collidedEntities;
	}

	public static void createExplosion(World world, Entity entity, BlockPos pos) {
		world.createExplosion(entity, pos.getX(), pos.getY(), pos.getZ(), EverythingExplodesConfig.explosionStrength, true);
	}

	public static UUID getPlayerUUID(EntityPlayer player) {
		if (player != null && player.getGameProfile() != null) {
			return player.getGameProfile().getId();
		}

		return null;
	}
	
	public static List<String> parseStringIntoLength(String s, int length) {
		List<String> parsedStrings = new ArrayList<String>();
		String line = "";
		boolean addedPrev = false;
		for (String word : splitByWords(s)) {
			line = line.concat(" " + word).trim();

			if (line.length() > length) {
				parsedStrings.add(line);
				line = "";
				addedPrev = true;
			} else {
				addedPrev = false;
			}
		}
		if (!addedPrev) {
			parsedStrings.add(line);
		}

		return parsedStrings;
	}
	
	public static String[] splitByWords(String s) {
		return s.split(" ");
	}
	
	@SideOnly(Side.CLIENT)
	public static void drawStringOnHUD(FontRenderer fontRenderer, String string, int xOffset, int yOffset, int color, boolean shadow) {
		fontRenderer.drawString(string, 2 + xOffset, 2 + yOffset, color, shadow);
	}
	
	@SideOnly(Side.CLIENT)
	public static void drawStringsOnHUD(FontRenderer fontRenderer, List<String> strings, int xOffset, int yOffset, int color, boolean shadow) {
		for (String s : strings) {
			drawStringOnHUD(fontRenderer, s, xOffset, yOffset, color, shadow);
			yOffset += 9;
		}
	}
	
	@SideOnly(Side.CLIENT)
	public static String localize(String unlocString, Object... args) {
		return I18n.format(unlocString, args);
	}

}
