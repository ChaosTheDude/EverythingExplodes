package com.chaosthedude.everythingexplodes.event;

import com.chaosthedude.everythingexplodes.config.EverythingExplodesConfig;
import com.chaosthedude.everythingexplodes.mode.ExplosionMode;
import com.chaosthedude.everythingexplodes.mode.ExplosionModes;
import com.chaosthedude.everythingexplodes.util.Util;

import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;
import net.minecraftforge.fml.relauncher.Side;

public class EverythingExplodesEvents {

	@SubscribeEvent
	public void onPlayerTick(PlayerTickEvent event) {
		if (event.side == Side.SERVER && event.phase == Phase.END) {
			final World world = event.player.worldObj;
			final Entity exploder = EverythingExplodesConfig.damagePlayer ? null : event.player;
			final ExplosionMode explosionMode = ExplosionModes.getExplosionMode(event.player);
			if (explosionMode.shouldExplode(event.player)) {
				Util.createExplosion(world, exploder, event.player.getPosition());
			} else if (explosionMode == ExplosionModes.COLLIDE) {
				for (Entity entity : Util.getCollidedEntities(event.player)) {
					Util.createExplosion(world, exploder, entity.getPosition());
				}
			}
		}
	}

	@SubscribeEvent
	public void onPlayerInteract(PlayerInteractEvent event) {
		if (event.getSide() == Side.SERVER && ExplosionModes.getExplosionMode(event.getEntityPlayer()) == ExplosionModes.INTERACT) {
			final Entity exploder = EverythingExplodesConfig.damagePlayer ? null : event.getEntityPlayer();
			Util.createExplosion(event.getWorld(), exploder, event.getPos());
		}
	}

}
