package dev.xkmc.l2enchantments.content.enchantments.tool;

import dev.xkmc.l2enchantments.content.enchantments.core.DurabilityEnchantment;

public class Robust extends ToolEnchant implements DurabilityEnchantment {

	public Robust() {
		super(ROBUST);
	}

	@Override
	public double durabilityFactor(int lv, double damage) {
		return 1 / Math.sqrt(damage);
	}
}
