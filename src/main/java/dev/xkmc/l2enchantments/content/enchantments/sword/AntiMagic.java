package dev.xkmc.l2enchantments.content.enchantments.sword;

import dev.xkmc.l2enchantments.events.AttackEventHandler;
import dev.xkmc.l2enchantments.init.data.ModConfig;
import net.minecraftforge.event.entity.living.LivingAttackEvent;

import java.util.function.Supplier;

public class AntiMagic extends SwordEnchant {

	public static final Supplier<Double> CHANCE_PER_LEVEL = ModConfig.COMMON.antiMagicChance::get;

	public AntiMagic() {
		super(ANTI_MAGIC);
	}

	@Override
	public void onTargetAttacked(int lv, LivingAttackEvent event, AttackEventHandler.AttackCache attackCache) {
		if (RANDOM.nextFloat() < lv * CHANCE_PER_LEVEL.get()) event.getSource().bypassMagic();
	}

}