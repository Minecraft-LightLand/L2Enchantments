package dev.xkmc.playerdifficulty.content.enchantments.sword;

import dev.xkmc.playerdifficulty.content.enchantments.core.EnchConfig;
import dev.xkmc.playerdifficulty.events.AttackEventHandler;
import dev.xkmc.playerdifficulty.init.data.DifficultyConfig;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

import java.util.function.Supplier;

public class StackingDamage extends SwordEnchant {

	public static final Supplier<Double> PERCENT_PER_LEVEL = DifficultyConfig.COMMON.stackingRatio::get;

	private static final String KEY = "StackedDamage";

	public StackingDamage( ) {
		super(STACK_DMG);
	}

	@Override
	public int getAdditionalDamage(int lv, LivingHurtEvent event, AttackEventHandler.AttackCache attackCache) {
		double old = attackCache.weapon.getOrCreateTag().getDouble(KEY);
		attackCache.weapon.getOrCreateTag().putDouble(KEY, 0);
		return (int) Math.floor(old);
	}

	@Override
	public void onTargetDamage(int lv, LivingDamageEvent event, AttackEventHandler.AttackCache attackCache) {
		double old = attackCache.weapon.getOrCreateTag().getDouble(KEY);
		double current = (attackCache.damage_1 - attackCache.damage_2) * PERCENT_PER_LEVEL.get();
		if (current > old)
			attackCache.weapon.getOrCreateTag().putDouble(KEY, current);
	}
}
