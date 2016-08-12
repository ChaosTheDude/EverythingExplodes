package com.chaosthedude.everythingexplodes.client;

import com.chaosthedude.everythingexplodes.EverythingExplodes;
import com.chaosthedude.everythingexplodes.mode.ExplosionModes;
import com.chaosthedude.everythingexplodes.network.PacketCycleExplosionMode;
import com.chaosthedude.everythingexplodes.network.PacketCycleExplosionModeBackward;
import com.chaosthedude.everythingexplodes.network.PacketHandler;
import com.chaosthedude.everythingexplodes.util.Util;

import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.RenderTickEvent;

public class ClientEvents {

	private static final Minecraft mc = Minecraft.getMinecraft();

	@SubscribeEvent
	public void onRenderTick(RenderTickEvent event) {
		if (mc.thePlayer == null || mc.currentScreen != null || mc.gameSettings.hideGUI || mc.gameSettings.showDebugInfo) {
			return;
		}

		Util.drawStringOnHUD(mc.fontRendererObj, ExplosionModes.Client.getExplosionModeString(), 1, 1, 0xffffff, true);
		Util.drawStringsOnHUD(mc.fontRendererObj, Util.parseStringIntoLength(ExplosionModes.Client.getExplosionMode().getLocalizedDescription(), 25), 1, 11, 0xffffff, true);
	}

	@SubscribeEvent
	public void onClientTick(ClientTickEvent event) {
		if (EverythingExplodes.toggleExplosionMode.isPressed()) {
			ExplosionModes.Client.cycleExplosionMode(!mc.thePlayer.isSneaking());
			if (!mc.thePlayer.isSneaking()) {
				PacketHandler.instance.sendToServer(new PacketCycleExplosionMode());
			} else {
				PacketHandler.instance.sendToServer(new PacketCycleExplosionModeBackward());
			}
		}
	}

}
