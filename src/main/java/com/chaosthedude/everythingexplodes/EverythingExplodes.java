package com.chaosthedude.everythingexplodes;

import org.lwjgl.input.Keyboard;

import com.chaosthedude.everythingexplodes.config.EverythingExplodesConfig;
import com.chaosthedude.everythingexplodes.event.EverythingExplodesEvents;
import com.chaosthedude.everythingexplodes.network.PacketHandler;
import com.chaosthedude.everythingexplodes.proxy.CommonProxy;

import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = EverythingExplodes.MODID, name = EverythingExplodes.NAME, version = EverythingExplodes.VERSION, acceptedMinecraftVersions = "[1.10,1.10.2]")

public class EverythingExplodes {

	public static final String MODID = "EverythingExplodes";
	public static final String NAME = "Everything Explodes";
	public static final String VERSION = "1.0.0";

	public static KeyBinding toggleExplosionMode = new KeyBinding("keybind.cycleExplosionMode", Keyboard.KEY_X, "key.categories.gameplay");

	@SidedProxy(clientSide = "com.chaosthedude.everythingexplodes.client.ClientProxy", serverSide = "com.chaosthedude.everythingexplodes.proxy.CommonProxy")
	public static CommonProxy proxy;

	@EventHandler
	public void init(FMLPreInitializationEvent event) {
		EverythingExplodesConfig.loadConfig(event.getSuggestedConfigurationFile());

		MinecraftForge.EVENT_BUS.register(new EverythingExplodesEvents());

		proxy.registerEvents();
		proxy.registerKeyBindings();

		PacketHandler.init();
	}

}