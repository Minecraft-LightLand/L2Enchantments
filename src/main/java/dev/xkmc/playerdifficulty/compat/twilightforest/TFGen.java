package dev.xkmc.playerdifficulty.compat.twilightforest;

import dev.xkmc.l2library.network.BaseConfig;
import dev.xkmc.playerdifficulty.content.spawn.ArmorConfig;
import dev.xkmc.playerdifficulty.content.spawn.WeaponConfig;
import twilightforest.item.TFItems;

import java.util.Map;

public class TFGen {

	public static void onConfigGen(Map<String, BaseConfig> map) {
		// twilightforest armor config
		{
			ArmorConfig armor = new ArmorConfig();

			armor.addItems(20, 25, TFItems.ARCTIC_HELMET.get(), TFItems.ARCTIC_CHESTPLATE.get(),TFItems.ARCTIC_LEGGINGS.get(), TFItems.ARCTIC_BOOTS.get());
			armor.addItems(20, 25, TFItems.IRONWOOD_HELMET.get(), TFItems.IRONWOOD_CHESTPLATE.get(),TFItems.IRONWOOD_LEGGINGS.get(), TFItems.IRONWOOD_BOOTS.get());
			armor.addItems(40, 10, TFItems.STEELEAF_HELMET.get(), TFItems.STEELEAF_CHESTPLATE.get(),TFItems.STEELEAF_LEGGINGS.get(), TFItems.STEELEAF_BOOTS.get());
			armor.addItems(60, 10, TFItems.KNIGHTMETAL_HELMET.get(), TFItems.KNIGHTMETAL_CHESTPLATE.get(),TFItems.KNIGHTMETAL_LEGGINGS.get(), TFItems.KNIGHTMETAL_BOOTS.get());
			armor.addItems(80, 5, TFItems.YETI_HELMET.get(), TFItems.YETI_CHESTPLATE.get(),TFItems.YETI_LEGGINGS.get(), TFItems.YETI_BOOTS.get());
			armor.addItems(80, 5, TFItems.FIERY_HELMET.get(), TFItems.FIERY_CHESTPLATE.get(),TFItems.FIERY_LEGGINGS.get(), TFItems.FIERY_BOOTS.get());
			armor.addItems(100, 5, TFItems.NAGA_CHESTPLATE.get(), TFItems.NAGA_LEGGINGS.get());
			armor.addItems(100, 5, TFItems.PHANTOM_HELMET.get(), TFItems.PHANTOM_CHESTPLATE.get());

			map.put("twilightforest/difficulty_config/armor/armor", armor);
		}

		// twilightforest weapon config
		{
			WeaponConfig weapon = new WeaponConfig();

			weapon.addItems(WeaponConfig.Type.BOW, 60, 5,
					TFItems.ENDER_BOW.get(), TFItems.ICE_BOW.get(), TFItems.SEEKER_BOW.get(), TFItems.TRIPLE_BOW.get());
			weapon.addItems(WeaponConfig.Type.MEELEE, 40, 25,
					TFItems.IRONWOOD_SWORD.get(), TFItems.STEELEAF_SWORD.get(), TFItems.KNIGHTMETAL_SWORD.get(),
					TFItems.IRONWOOD_AXE.get(), TFItems.STEELEAF_AXE.get(), TFItems.KNIGHTMETAL_AXE.get());
			weapon.addItems(WeaponConfig.Type.MEELEE, 60, 5,
					TFItems.FIERY_SWORD.get(), TFItems.GIANT_SWORD.get(), TFItems.ICE_SWORD.get(),
					TFItems.GLASS_SWORD.get(), TFItems.BLOCK_AND_CHAIN.get());

			map.put("twilightforest/difficulty_config/weapon/weapon", weapon);

		}
	}
}
