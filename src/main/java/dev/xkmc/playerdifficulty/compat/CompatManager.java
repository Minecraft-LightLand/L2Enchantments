package dev.xkmc.playerdifficulty.compat;

import dev.xkmc.l2library.network.BaseConfig;
import dev.xkmc.l2library.repack.registrate.providers.loot.RegistrateLootTableProvider;
import dev.xkmc.playerdifficulty.compat.apotheosis.AffixAttach;
import dev.xkmc.playerdifficulty.compat.champions.ChampionLootGen;
import dev.xkmc.playerdifficulty.compat.champions.SpawnReset;
import dev.xkmc.playerdifficulty.compat.scalinghealth.HealthScalingSpawn;
import dev.xkmc.playerdifficulty.compat.twilightforest.TFGen;
import dev.xkmc.playerdifficulty.content.capability.DifficultyCap;
import dev.xkmc.playerdifficulty.content.capability.PlayerLevel;
import dev.xkmc.playerdifficulty.init.data.DifficultyConfig;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.fml.ModList;

import java.util.Map;
import java.util.Optional;

public class CompatManager {

	public static void spawnChampion(DifficultyCap level, LivingEntity entity) {
		if (!DifficultyConfig.COMMON.championEnable.get())
			return;
		if (entity.level.isClientSide())
			return;
		if (ModList.get().isLoaded("champions")) {
			SpawnReset.onSpawn(level, entity);
		}
	}

	public static void spawnApotheosis(DifficultyCap level, LivingEntity entity) {
		if (!DifficultyConfig.COMMON.apotheosisEnable.get())
			return;
		if (entity.level.isClientSide())
			return;
		if (ModList.get().isLoaded("apotheosis")) {
			AffixAttach.onSpawn(level, entity);
		}
	}

	public static DifficultyCap getDifficultyCap(Player player) {
		Optional<DifficultyCap> ans = Optional.empty();
		if (ModList.get().isLoaded("scalinghealth")) {
			ans = HealthScalingSpawn.getSource(player);
		}
		return ans.orElseGet(() -> new PDDifficultyCap(PlayerLevel.HOLDER.get(player)));
	}

	public static void onConfigGen(Map<String, BaseConfig> map) {
		if (ModList.get().isLoaded("twilightforest")) {
			TFGen.onConfigGen(map);
		}
	}

	public static void onLootGen(RegistrateLootTableProvider pvd) {
		if (ModList.get().isLoaded("champions")) {
			ChampionLootGen.genLoot(pvd);
		}
	}

	public record PDDifficultyCap(PlayerLevel cap) implements DifficultyCap {

		@Override
		public float getDifficultyValue() {
			return cap.getDifficulty();
		}

		@Override
		public void setDifficultyValue(float value) {
			cap.setDifficulty(value);
		}

		@Override
		public void syncToClient() {
			cap.syncToClient();
		}
	}


}
