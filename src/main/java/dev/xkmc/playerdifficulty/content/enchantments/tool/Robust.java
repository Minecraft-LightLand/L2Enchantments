package dev.xkmc.playerdifficulty.content.enchantments.tool;

import dev.xkmc.playerdifficulty.content.enchantments.core.DurabilityEnchantment;
import dev.xkmc.playerdifficulty.content.enchantments.core.EnchConfig;

public class Robust extends ToolEnchant implements DurabilityEnchantment {

	public Robust() {
		super(ROBUST);
	}

	@Override
	public double durabilityFactor(int lv, double damage) {
		return 1 / Math.sqrt(damage);
	}
}
