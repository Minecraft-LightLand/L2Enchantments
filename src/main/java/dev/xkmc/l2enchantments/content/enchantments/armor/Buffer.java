package dev.xkmc.l2enchantments.content.enchantments.armor;

import net.minecraft.world.damagesource.DamageSource;

public class Buffer extends ArmorEnchant {

	public Buffer() {
		super(BUFFER);
	}

	@Override
	public int getDamageProtection(int lv, DamageSource source) {
		return source == DamageSource.FLY_INTO_WALL || source == DamageSource.FALLING_BLOCK || source == DamageSource.FALLING_STALACTITE ? lv * 3 : 0;
	}
}
