package com.chaosthedude.everythingexplodes.network;

import com.chaosthedude.everythingexplodes.EverythingExplodes;

import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class PacketHandler {

	public static final SimpleNetworkWrapper instance = NetworkRegistry.INSTANCE.newSimpleChannel(EverythingExplodes.MODID);

	public static void init() {
		instance.registerMessage(PacketCycleExplosionMode.Handler.class, PacketCycleExplosionMode.class, 0, Side.SERVER);
		instance.registerMessage(PacketCycleExplosionModeBackward.Handler.class, PacketCycleExplosionModeBackward.class, 1, Side.SERVER);
	}

}
