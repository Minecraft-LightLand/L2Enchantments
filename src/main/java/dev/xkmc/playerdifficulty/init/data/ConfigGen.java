package dev.xkmc.playerdifficulty.init.data;

import dev.xkmc.l2library.network.BaseConfig;
import dev.xkmc.l2library.network.ConfigDataProvider;
import dev.xkmc.playerdifficulty.compat.CompatManager;
import dev.xkmc.playerdifficulty.content.spawn.ArmorConfig;
import dev.xkmc.playerdifficulty.content.spawn.WeaponConfig;
import net.minecraft.data.DataGenerator;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Items;

import java.util.Map;

public class ConfigGen extends ConfigDataProvider {

	public ConfigGen(DataGenerator generator) {
		super(generator, "./data/", "Player Difficulty Config");
	}

	@Override
	public void add(Map<String, BaseConfig> map) {
		// vanilla armor config
		{
			ArmorConfig armor = new ArmorConfig();
			// types
			armor.types.add(EntityType.ZOMBIE);
			armor.types.add(EntityType.ZOMBIE_VILLAGER);
			armor.types.add(EntityType.ZOMBIFIED_PIGLIN);
			armor.types.add(EntityType.DROWNED);
			armor.types.add(EntityType.HUSK);
			armor.types.add(EntityType.SKELETON);
			armor.types.add(EntityType.WITHER_SKELETON);
			armor.types.add(EntityType.STRAY);
			armor.types.add(EntityType.PIGLIN);
			armor.types.add(EntityType.PIGLIN_BRUTE);
			armor.types.add(EntityType.ILLUSIONER);
			armor.types.add(EntityType.PILLAGER);
			armor.types.add(EntityType.VINDICATOR);
			armor.types.add(EntityType.EVOKER);

			// vanilla tiered
			armor.addItems(10, 100, Items.LEATHER_HELMET, Items.LEATHER_CHESTPLATE, Items.LEATHER_LEGGINGS, Items.LEATHER_BOOTS);
			armor.addItems(20, 150, Items.IRON_HELMET, Items.IRON_CHESTPLATE, Items.IRON_LEGGINGS, Items.IRON_BOOTS);
			armor.addItems(40, 100, Items.GOLDEN_HELMET, Items.GOLDEN_CHESTPLATE, Items.GOLDEN_LEGGINGS, Items.GOLDEN_BOOTS);
			armor.addItems(60, 50, Items.DIAMOND_HELMET, Items.DIAMOND_CHESTPLATE, Items.DIAMOND_LEGGINGS, Items.DIAMOND_BOOTS);
			armor.addItems(80, 25, Items.NETHERITE_HELMET, Items.NETHERITE_CHESTPLATE, Items.NETHERITE_LEGGINGS, Items.NETHERITE_BOOTS);

			map.put("minecraft/difficulty_config/armor/vanilla", armor);
		}

		// vanilla weapon config
		{
			WeaponConfig weapon = new WeaponConfig();

			weapon.addType(EntityType.ZOMBIE, WeaponConfig.Type.MEELEE);
			weapon.addType(EntityType.ZOMBIE_VILLAGER, WeaponConfig.Type.MEELEE);
			weapon.addType(EntityType.ZOMBIFIED_PIGLIN, WeaponConfig.Type.MEELEE);
			weapon.addType(EntityType.DROWNED, WeaponConfig.Type.TRIDENT);
			weapon.addType(EntityType.HUSK, WeaponConfig.Type.MEELEE);
			weapon.addType(EntityType.SKELETON, WeaponConfig.Type.BOW);
			weapon.addType(EntityType.WITHER_SKELETON, WeaponConfig.Type.MEELEE);
			weapon.addType(EntityType.STRAY, WeaponConfig.Type.BOW);
			weapon.addType(EntityType.PIGLIN, WeaponConfig.Type.CROSSBOW);
			weapon.addType(EntityType.PIGLIN, WeaponConfig.Type.MEELEE);
			weapon.addType(EntityType.PIGLIN_BRUTE, WeaponConfig.Type.MEELEE);
			weapon.addType(EntityType.PILLAGER, WeaponConfig.Type.CROSSBOW);
			weapon.addType(EntityType.VINDICATOR, WeaponConfig.Type.MEELEE);

			weapon.addItem(WeaponConfig.Type.BOW, Items.BOW, 10, 400);
			weapon.addItem(WeaponConfig.Type.CROSSBOW, Items.CROSSBOW, 10, 400);
			weapon.addItem(WeaponConfig.Type.TRIDENT, Items.TRIDENT, 20, 1000);
			weapon.addItems(WeaponConfig.Type.MEELEE, 20, 150, Items.IRON_AXE, Items.IRON_PICKAXE, Items.IRON_SWORD);
			weapon.addItems(WeaponConfig.Type.MEELEE, 20, 100, Items.GOLDEN_AXE, Items.GOLDEN_PICKAXE, Items.GOLDEN_SWORD);
			weapon.addItems(WeaponConfig.Type.MEELEE, 40, 50, Items.DIAMOND_AXE, Items.DIAMOND_PICKAXE, Items.DIAMOND_SWORD);
			weapon.addItems(WeaponConfig.Type.MEELEE, 60, 25, Items.NETHERITE_AXE, Items.NETHERITE_PICKAXE, Items.NETHERITE_SWORD);

			map.put("minecraft/difficulty_config/weapon/vanilla", weapon);
		}

		CompatManager.onConfigGen(map);


	}

}
