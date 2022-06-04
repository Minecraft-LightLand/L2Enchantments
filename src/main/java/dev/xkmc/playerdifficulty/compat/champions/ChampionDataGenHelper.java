package dev.xkmc.playerdifficulty.compat.champions;

import net.minecraft.advancements.critereon.MinMaxBounds;
import net.minecraft.world.level.storage.loot.LootContext;
import top.theillusivec4.champions.api.IAffix;
import top.theillusivec4.champions.common.loot.LootItemChampionPropertyCondition;

import java.util.Set;
import java.util.stream.Collectors;

public class ChampionDataGenHelper {

	public static LootItemChampionPropertyCondition get(MinMaxBounds.Ints tier, Set<IAffix> set, MinMaxBounds.Ints match, MinMaxBounds.Ints count) {
		return ChampionDataGenAccessor.CHAMPION_BUILDER.create(LootContext.EntityTarget.THIS,
				tier, ChampionDataGenAccessor.AFFIX_BUILDER.create(
						set.stream().map(IAffix::getIdentifier).collect(Collectors.toSet()),
						match, count));
	}

	public static LootItemChampionPropertyCondition get(MinMaxBounds.Ints tier) {
		return ChampionDataGenAccessor.CHAMPION_BUILDER.create(LootContext.EntityTarget.THIS,
				tier, ChampionDataGenAccessor.ANY);
	}

	public static LootItemChampionPropertyCondition getTierAtLeast(int least) {
		return get(MinMaxBounds.Ints.atLeast(least));
	}

	public static LootItemChampionPropertyCondition getTierExact(int tier) {
		return get(MinMaxBounds.Ints.exactly(tier));
	}

	public static LootItemChampionPropertyCondition getTierBounds(int low, int hi) {
		return get(MinMaxBounds.Ints.between(low, hi));
	}

	public static LootItemChampionPropertyCondition getAffix(IAffix affix) {
		return get(MinMaxBounds.Ints.ANY, Set.of(affix), MinMaxBounds.Ints.atLeast(1), MinMaxBounds.Ints.ANY);
	}

}
