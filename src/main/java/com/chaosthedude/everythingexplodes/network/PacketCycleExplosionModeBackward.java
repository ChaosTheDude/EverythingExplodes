package com.chaosthedude.everythingexplodes.network;

import com.chaosthedude.everythingexplodes.mode.ExplosionModes;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketCycleExplosionModeBackward implements IMessage {
	
	public PacketCycleExplosionModeBackward() {
	}

	@Override
	public void fromBytes(ByteBuf buf) {
	}

	@Override
	public void toBytes(ByteBuf buf) {
	}

	public static class Handler implements IMessageHandler<PacketCycleExplosionModeBackward, IMessage> {
		@Override
		public IMessage onMessage(PacketCycleExplosionModeBackward packet, MessageContext ctx) {
			final EntityPlayer player = ctx.getServerHandler().playerEntity;
			ExplosionModes.cycleExplosionMode(player, false);

			return null;
		}
	}

}
