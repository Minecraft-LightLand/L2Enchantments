package dev.xkmc.playerdifficulty.content.enchantments.sword;

import dev.xkmc.playerdifficulty.content.enchantments.core.EnchConfig;
import dev.xkmc.playerdifficulty.events.AttackEventHandler;
import dev.xkmc.playerdifficulty.init.data.DifficultyConfig;
import net.minecraftforge.event.entity.living.LivingAttackEvent;

import java.util.function.Supplier;

public class AntiMagic extends SwordEnchant {

	public static final Supplier<Double> CHANCE_PER_LEVEL = DifficultyConfig.COMMON.antiMagicChance::get;

	public AntiMagic() {
		super(ANTI_MAGIC);
	}

	@Override
	public void onTargetAttacked(int lv, LivingAttackEvent event, AttackEventHandler.AttackCache attackCache) {
		if (RANDOM.nextFloat() < lv * CHANCE_PER_LEVEL.get()) event.getSource().bypassMagic();
	}

}
