package com.chaosthedude.everythingexplodes.mode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.chaosthedude.everythingexplodes.EverythingExplodes;
import com.chaosthedude.everythingexplodes.network.PacketCycleExplosionMode;
import com.chaosthedude.everythingexplodes.network.PacketHandler;
import com.chaosthedude.everythingexplodes.util.Util;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ExplosionModes {
	
	public static final List<ExplosionMode> REGISTRY = new ArrayList<ExplosionMode>();

	public static final ExplosionMode DISABLED = new ExplosionMode("string.EverythingExplodes.disabled.name", "string.EverythingExplodes.disabled.desc") {
		@Override
		public String getLocalizedDescription() {
			return Util.localize(unlocDescription, EverythingExplodes.toggleExplosionMode.getDisplayName());
		}
	};

	public static final ExplosionMode GROUND = new ExplosionMode("string.EverythingExplodes.onGround.name", "string.EverythingExplodes.onGround.desc") {
		@Override
		public boolean shouldExplode(EntityPlayer player) {
			return player.onGround;
		}
	};

	public static final ExplosionMode COLLIDE = new ExplosionMode("string.EverythingExplodes.onCollide.name", "string.EverythingExplodes.onCollide.desc");

	public static final ExplosionMode INTERACT = new ExplosionMode("string.EverythingExplodes.onInteract.name", "string.EverythingExplodes.onInteract.desc");

	public static final ExplosionMode ALWAYS = new ExplosionMode("string.EverythingExplodes.always.name", "string.EverythingExplodes.always.desc") {
		@Override
		public boolean shouldExplode(EntityPlayer player) {
			return true;
		}
	};

	public static final Map<UUID, ExplosionMode> EXPLOSION_MODES = new HashMap<UUID, ExplosionMode>();

	public static ExplosionMode cycleExplosionMode(EntityPlayer player, boolean forward) {
		ExplosionMode currentExplosionMode = getExplosionMode(player);
		boolean setNext = false;
		if (forward) {
			for (int i = 0; i < REGISTRY.size(); i++) {
				ExplosionMode explosionMode = REGISTRY.get(i);
				if (explosionMode == currentExplosionMode) {
					setNext = true;
				} else if (setNext) {
					return setExplosionMode(player, explosionMode);
				}
			}
		
			return setExplosionMode(player, DISABLED);
		} else {
			for (int i = REGISTRY.size() - 1; i >= 0; i--) {
				ExplosionMode explosionMode = REGISTRY.get(i);
				if (explosionMode == currentExplosionMode) {
					setNext = true;
				} else if (setNext) {
					return setExplosionMode(player, explosionMode);
				}
			}
			
			return setExplosionMode(player, ALWAYS);
		}
	}

	public static ExplosionMode setExplosionMode(EntityPlayer player, ExplosionMode explosionMode) {
		EXPLOSION_MODES.put(Util.getPlayerUUID(player), explosionMode);
		return explosionMode;
	}

	public static ExplosionMode getExplosionMode(EntityPlayer player) {
		final UUID playerUUID = Util.getPlayerUUID(player);
		if (!hasExplosionMode(player)) {
			setExplosionMode(player, DISABLED);
		}

		return EXPLOSION_MODES.get(playerUUID);
	}

	public static boolean hasExplosionMode(EntityPlayer player) {
		return EXPLOSION_MODES.containsKey(Util.getPlayerUUID(player));
	}
	
	private ExplosionMode registerExplosionMode(String unlocString, String unlocDescription) {
		return new ExplosionMode(unlocString, unlocDescription);
	}

	@SideOnly(Side.CLIENT)
	public static class Client {

		private static ExplosionMode clientExplosionMode = DISABLED;

		public static ExplosionMode cycleExplosionMode(boolean forward) {
			boolean setNext = false;
			if (forward) {
				for (int i = 0; i < REGISTRY.size(); i++) {
					ExplosionMode explosionMode = REGISTRY.get(i);
					if (explosionMode == clientExplosionMode) {
						setNext = true;
					} else if (setNext) {
						return setExplosionMode(explosionMode);
					}
				}

				return setExplosionMode(DISABLED);
			} else {
				for (int i = REGISTRY.size() - 1; i >= 0; i--) {
					ExplosionMode explosionMode = REGISTRY.get(i);
					if (explosionMode == clientExplosionMode) {
						setNext = true;
					} else if (setNext) {
						return setExplosionMode(explosionMode);
					}
				}

				return setExplosionMode(ALWAYS);
			}
		}

		public static ExplosionMode setExplosionMode(ExplosionMode explosionMode) {
			clientExplosionMode = explosionMode;
			return explosionMode;
		}

		public static ExplosionMode getExplosionMode() {
			return clientExplosionMode;
		}

		public static String getExplosionModeString() {
			return Util.localize("string.EverythingExplodes.explosionMode.name") +  ": " + getExplosionMode().getLocalizedName();
		}

	}

}
