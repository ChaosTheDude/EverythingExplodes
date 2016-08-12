package com.chaosthedude.everythingexplodes.client;

import com.chaosthedude.everythingexplodes.EverythingExplodes;
import com.chaosthedude.everythingexplodes.proxy.CommonProxy;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;

public class ClientProxy extends CommonProxy {
	
	@Override
	public void registerEvents() {
		MinecraftForge.EVENT_BUS.register(new ClientEvents());
	}
	
	@Override
	public void registerKeyBindings() {
		ClientRegistry.registerKeyBinding(EverythingExplodes.toggleExplosionMode);
	}

}
