package dev.xkmc.l2enchantments.content.enchantments.sword;

import dev.xkmc.l2enchantments.content.enchantments.core.DurabilityEnchantment;
import dev.xkmc.l2enchantments.events.AttackEventHandler;
import dev.xkmc.l2enchantments.init.data.ModConfig;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

import java.util.function.Supplier;

public class Fragile extends SwordEnchant implements DurabilityEnchantment {

	public static final Supplier<Double> PERCENT_PER_LEVEL = ModConfig.COMMON.fragileDamageFactor::get;
	public static final Supplier<Double> DAMAGE_FACTOR = ModConfig.COMMON.fragileDurabilityFactor::get;

	public Fragile() {
		super(FRAGILE);
	}

	@Override
	public double getAdditionalDamage(int lv, LivingHurtEvent event, AttackEventHandler.AttackCache attackCache) {
		return event.getAmount() * PERCENT_PER_LEVEL.get() * lv;
	}

	@Override
	public double durabilityFactor(int lv, double damage) {
		return 1 + lv * DAMAGE_FACTOR.get();
	}
}
