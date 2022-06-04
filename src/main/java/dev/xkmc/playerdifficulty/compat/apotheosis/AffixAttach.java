package dev.xkmc.playerdifficulty.compat.apotheosis;

import dev.xkmc.playerdifficulty.content.capability.DifficultyCap;
import dev.xkmc.playerdifficulty.init.data.DifficultyConfig;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import shadows.apotheosis.deadly.loot.LootCategory;
import shadows.apotheosis.deadly.loot.LootController;
import shadows.apotheosis.deadly.loot.LootRarity;

import javax.annotation.Nullable;
import java.util.Random;
import java.util.function.Consumer;

public class AffixAttach {

	public static void onSpawn(DifficultyCap level, LivingEntity entity) {
		if (!(entity instanceof Mob mob)) return;
		for (EquipmentSlot slot : EquipmentSlot.values()) {
			if (mob.hasItemInSlot(slot)) {
				ItemStack stack = mob.getItemBySlot(slot);
				if (stack.isEmpty())
					continue;
				LootCategory category = LootCategory.forItem(stack);
				if (category == null)
					continue;
				setLoot(level, stack, new_stack -> {
					mob.setItemSlot(slot, new_stack);
					mob.setDropChance(slot, 1);
				});
			}
		}
	}

	private static final Random RAND = new Random();

	private static void setLoot(DifficultyCap level, ItemStack stack, Consumer<ItemStack> setter) {
		LootRarity rarity = getRarity(level);
		if (rarity == null)
			return;
		ItemStack new_stack = LootController.createLootItem(stack, rarity, RAND);
		setter.accept(new_stack);
	}

	@Nullable
	private static LootRarity getRarity(DifficultyCap level) {
		float base_chance = (float) (double) DifficultyConfig.COMMON.apotheosisTierChance.get();
		LootRarity result = null;
		for (LootRarity rarity : LootRarity.values()) {
			float chance = level.getApotheosisChance(rarity.ordinal() + 1, base_chance);
			if (!(RAND.nextFloat() < chance)) {
				return result;
			}
			result = rarity;
		}
		return result;
	}

}
