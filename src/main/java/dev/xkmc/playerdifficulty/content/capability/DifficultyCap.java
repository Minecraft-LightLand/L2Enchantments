package dev.xkmc.playerdifficulty.content.capability;

import dev.xkmc.playerdifficulty.compat.CompatManager;
import dev.xkmc.playerdifficulty.init.data.DifficultyConfig;
import net.minecraft.world.entity.player.Player;

public interface DifficultyCap {

	static DifficultyCap get(Player player) {
		return CompatManager.getDifficultyCap(player);
	}

	float getDifficultyValue();

	void setDifficultyValue(float value);

	void syncToClient();

	default float getChampionChance(int tier, float chance) {
		int tierFactor = DifficultyConfig.COMMON.championsTierFactor.get();
		int tierSeparation = DifficultyConfig.COMMON.championsTierSeparation.get();
		float level = (getDifficultyValue() / tierFactor - tier) * tierSeparation;
		if (level >= 0)
			return 1 - (float) Math.pow(1 - chance, level + 1);
		return (float) Math.pow(chance, -level + 1);
	}

	default float getApotheosisChance(int tier, float chance) {
		int tierFactor = DifficultyConfig.COMMON.apotheosisTierFactor.get();
		int tierSeparation = DifficultyConfig.COMMON.apotheosisTierSeparation.get();
		float level = (getDifficultyValue() / tierFactor - tier) * tierSeparation;
		if (level >= 0)
			return 1 - (float) Math.pow(1 - chance, level + 1);
		return (float) Math.pow(chance, -level + 1);
	}
}
