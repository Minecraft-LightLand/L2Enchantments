package dev.xkmc.playerdifficulty.compat.champions;

import com.google.common.collect.ImmutableSortedMap;
import dev.xkmc.playerdifficulty.content.capability.DifficultyCap;
import net.minecraft.world.entity.LivingEntity;
import top.theillusivec4.champions.Champions;
import top.theillusivec4.champions.api.IAffix;
import top.theillusivec4.champions.api.IChampion;
import top.theillusivec4.champions.common.capability.ChampionCapability;
import top.theillusivec4.champions.common.integration.scalinghealth.ScalingHealthPlugin;
import top.theillusivec4.champions.common.rank.Rank;
import top.theillusivec4.champions.common.rank.RankManager;
import top.theillusivec4.champions.common.util.ChampionBuilder;
import top.theillusivec4.champions.common.util.ChampionHelper;
import top.theillusivec4.champions.common.util.EntityManager;

import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class SpawnReset {

	public static void onSpawn(DifficultyCap level, LivingEntity entity) {
		ChampionCapability.getCapability(entity).ifPresent((champion) -> {
			if (champion.getServer().getRank().isEmpty()) {
				spawn(level, champion);
			}
		});
	}

	/**
	 * Copied from ChampionBuilder
	 */
	private static void spawn(DifficultyCap level, IChampion champion) {
		LivingEntity entity = champion.getLivingEntity();
		Rank newRank = createRank(level, entity);
		if (champion.getServer().getRank().isPresent() && champion.getServer().getRank().get().getTier() > newRank.getTier())
			return;
		champion.getServer().setRank(newRank);
		ChampionBuilder.applyGrowth(entity, (float) newRank.getGrowthFactor());
		List<IAffix> newAffixes = ChampionBuilder.createAffixes(newRank, champion);
		champion.getServer().setAffixes(newAffixes);
		newAffixes.forEach((affix) -> affix.onInitialSpawn(champion));
	}

	private static final Random RAND = new Random();

	/**
	 * Copied and modified from ChampionBuilder
	 */
	private static Rank createRank(DifficultyCap playerLevel, LivingEntity livingEntity) {
		if (!ChampionHelper.checkPotential(livingEntity)) {
			return RankManager.getLowestRank();
		}
		ImmutableSortedMap<Integer, Rank> ranks = RankManager.getRanks();
		if (ranks.isEmpty()) {
			Champions.LOGGER.error("No rank configuration found! Please check the 'champions-ranks.toml' file in the 'serverconfigs'.");
			return RankManager.getLowestRank();
		}
		Integer[] tierRange = new Integer[]{null, null};
		EntityManager.getSettings(livingEntity.getType()).ifPresent((entitySettings) -> {
			tierRange[0] = ChampionsEntityManagerAccessor.getMinTier(entitySettings);
			tierRange[1] = ChampionsEntityManagerAccessor.getMaxTier(entitySettings);
		});
		Integer firstTier = tierRange[0] != null ? tierRange[0] : ranks.firstKey();
		int maxTier = tierRange[1] != null ? tierRange[1] : -1;

		Iterator<Integer> iter = ranks.navigableKeySet().tailSet(firstTier, false).iterator();
		Rank result = ranks.get(firstTier);
		if (result == null) {
			Champions.LOGGER.error("Tier {} cannot be found in {}! Assigning lowest available rank to {}", firstTier, ranks, livingEntity);
			return RankManager.getLowestRank();
		} else {
			while (iter.hasNext() && (result.getTier() < maxTier || maxTier == -1)) {
				Rank rank = ranks.get(iter.next());
				if (rank == null) {
					return result;
				}
				float chance = rank.getChance();
				chance = playerLevel.getChampionChance(rank.getTier(), chance);
				if (!(RAND.nextFloat() < chance)) {
					return result;
				}
				result = rank;
			}
			return result;
		}
	}
}
