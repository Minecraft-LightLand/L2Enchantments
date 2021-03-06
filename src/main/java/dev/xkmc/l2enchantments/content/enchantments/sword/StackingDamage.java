package dev.xkmc.l2enchantments.content.enchantments.sword;

import dev.xkmc.l2enchantments.events.AttackSeriesListener;
import dev.xkmc.l2enchantments.init.data.ModConfig;
import dev.xkmc.l2library.init.events.AttackEventHandler;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

import java.util.function.Supplier;

public class StackingDamage extends SwordEnchant {

	public static final Supplier<Double> PERCENT_PER_LEVEL = ModConfig.COMMON.stackingRatio::get;

	private static final String KEY = "StackedDamage";

	public StackingDamage( ) {
		super(STACK_DMG);
	}

	@Override
	public double getAdditionalDamage(int lv, LivingHurtEvent event, AttackEventHandler.AttackCache attackCache) {
		double old = attackCache.weapon.getOrCreateTag().getDouble(KEY);
		attackCache.weapon.getOrCreateTag().putDouble(KEY, 0);
		return old;
	}

	@Override
	public void onTargetDamage(int lv, LivingDamageEvent event, AttackEventHandler.AttackCache attackCache) {
		double old = attackCache.weapon.getOrCreateTag().getDouble(KEY);
		double current = (attackCache.damage_4 - attackCache.damage_5) * PERCENT_PER_LEVEL.get() * lv;
		if (current > old)
			attackCache.weapon.getOrCreateTag().putDouble(KEY, current);
	}
}
