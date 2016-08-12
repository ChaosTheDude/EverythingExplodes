package com.chaosthedude.everythingexplodes.config;

import java.io.File;

import com.chaosthedude.everythingexplodes.EverythingExplodes;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class EverythingExplodesConfig {

	public static Configuration config;

	public static float explosionStrength = 3.0F;

	public static boolean damagePlayer = true;

	public static void loadConfig(File configFile) {
		config = new Configuration(configFile);

		config.load();
		init();

		MinecraftForge.EVENT_BUS.register(new ChangeListener());
	}

	public static void init() {
		String comment;

		comment = "Explosion strength multiplier. Default: 3.0";
		explosionStrength = loadFloat("explosion.strength", comment, explosionStrength, Configuration.CATEGORY_GENERAL);

		comment = "Whether or not the player who causes the explosion should take damage from it. Default: true";
		damagePlayer = loadBool("player.explosionDamage", comment, damagePlayer, Configuration.CATEGORY_GENERAL);

		if (config.hasChanged()) {
			config.save();
		}
	}

	public static float loadFloat(String name, String comment, float def, String category) {
		Property prop = config.get(category, name, def);
		prop.setComment(comment);
		float val = (float) prop.getDouble(def);
		if (val <= 0) {
			val = def;
			prop.set(def);
		}

		return val;
	}

	public static boolean loadBool(String name, String comment, boolean def, String category) {
		Property prop = config.get(category, name, def);
		prop.setComment(comment);
		return prop.getBoolean(def);
	}

	public static class ChangeListener {
		@SubscribeEvent
		public void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent eventArgs) {
			if (eventArgs.getModID().equals(EverythingExplodes.MODID)) {
				init();
			}
		}
	}

}
