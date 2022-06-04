package dev.xkmc.playerdifficulty.compat.champions;

import dev.xkmc.l2library.serial.ExceptionHandler;
import net.minecraft.advancements.critereon.MinMaxBounds;
import net.minecraft.world.level.storage.loot.LootContext;
import top.theillusivec4.champions.common.loot.LootItemChampionPropertyCondition;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.Set;

public class ChampionDataGenAccessor {

	static Holder ANY;
	static ChampionConstruct CHAMPION_BUILDER;
	static AffixConstruct AFFIX_BUILDER;

	static {
		try {
			String str_affix = "top.theillusivec4.champions.common.loot.LootItemChampionPropertyCondition$AffixesPredicate";
			Class<?> cls = LootItemChampionPropertyCondition.class;
			Class<?> cls_affix = cls.getClassLoader().loadClass(str_affix);
			Field fld_affix = cls_affix.getDeclaredField("ANY");
			fld_affix.setAccessible(true);
			ANY = new Holder(fld_affix.get(null));
			Constructor<?> cons = cls.getDeclaredConstructor(LootContext.EntityTarget.class, MinMaxBounds.Ints.class, cls_affix);
			cons.setAccessible(true);
			CHAMPION_BUILDER = (a, b, c) -> ExceptionHandler.get(() -> (LootItemChampionPropertyCondition) cons.newInstance(a, b, c.val));
			Constructor<?> cons_afx = cls_affix.getDeclaredConstructor(Set.class, MinMaxBounds.Ints.class, MinMaxBounds.Ints.class);
			cons_afx.setAccessible(true);
			AFFIX_BUILDER = (a, b, c) -> ExceptionHandler.get(() -> new Holder(cons_afx.newInstance(a, b, c)));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	static class Holder {

		private final Object val;

		private Holder(Object val) {
			this.val = val;
		}
	}

	interface ChampionConstruct {

		LootItemChampionPropertyCondition create(LootContext.EntityTarget target, MinMaxBounds.Ints tiers, Holder affix);

	}

	interface AffixConstruct {

		Holder create(Set<String> set, MinMaxBounds.Ints matches, MinMaxBounds.Ints count);

	}

}
