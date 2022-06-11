package dev.xkmc.l2enchantments.events;

import dev.xkmc.l2enchantments.content.enchantments.sword.SwordEnchant;
import dev.xkmc.l2library.init.events.AttackEventHandler;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;

import java.util.Map;

public class AttackSeriesListener implements AttackEventHandler.AttackListener {

	@Override
	public void onAttack(AttackEventHandler.AttackCache cache, ItemStack weapon) {
		if (weapon != null) {
			for (Map.Entry<Enchantment, Integer> ent : EnchantmentHelper.getEnchantments(weapon).entrySet()) {
				if (ent.getKey() instanceof SwordEnchant sword) {
					sword.onTargetAttacked(ent.getValue(), cache.attack, cache);
				}
			}
		}
	}

	@Override
	public void onHurt(AttackEventHandler.AttackCache cache, ItemStack weapon) {
		if (weapon != null) {
			for (Map.Entry<Enchantment, Integer> ent : EnchantmentHelper.getEnchantments(weapon).entrySet()) {
				if (ent.getKey() instanceof SwordEnchant sword) {
					cache.damage_4 += sword.getAdditionalDamage(ent.getValue(), cache.hurt, cache);
				}
			}
			cache.hurt.setAmount(cache.damage_4);
			for (Map.Entry<Enchantment, Integer> ent : EnchantmentHelper.getEnchantments(weapon).entrySet()) {
				if (ent.getKey() instanceof SwordEnchant sword) {
					sword.onTargetHurt(ent.getValue(), cache.hurt, cache);
				}
			}
		}
	}

	@Override
	public void onDamage(AttackEventHandler.AttackCache cache, ItemStack weapon) {
		if (weapon != null) {
			for (Map.Entry<Enchantment, Integer> ent : EnchantmentHelper.getEnchantments(weapon).entrySet()) {
				if (ent.getKey() instanceof SwordEnchant sword) {
					sword.onTargetDamage(ent.getValue(), cache.damage, cache);
				}
			}
		}
	}

}
