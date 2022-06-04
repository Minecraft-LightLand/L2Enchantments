package dev.xkmc.playerdifficulty.content.enchantments.sword;

import dev.xkmc.playerdifficulty.events.AttackEventHandler;
import dev.xkmc.playerdifficulty.init.data.DifficultyConfig;
import net.minecraftforge.event.entity.living.LivingAttackEvent;

import java.util.function.Supplier;

public class SoulSlash extends SwordEnchant {

	public static final Supplier<Double> CHANCE_PER_LEVEL = DifficultyConfig.COMMON.soulSlashChance::get;

	public SoulSlash() {
		super(SOUL_SLASH);
	}

	@Override
	public void onTargetAttacked(int lv, LivingAttackEvent event, AttackEventHandler.AttackCache attackCache) {
		if (RANDOM.nextFloat() < lv * CHANCE_PER_LEVEL.get()) event.getSource().bypassArmor();
	}
}
