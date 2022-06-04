package dev.xkmc.playerdifficulty.compat.champions;

import dev.xkmc.l2library.serial.ExceptionHandler;
import dev.xkmc.playerdifficulty.init.PlayerDifficulty;
import top.theillusivec4.champions.common.util.EntityManager;

import java.lang.reflect.Field;

public class ChampionsEntityManagerAccessor {

	private static Field minTier, maxTier;

	static {
		try {
			Class<EntityManager.EntitySettings> cls = EntityManager.EntitySettings.class;
			minTier = cls.getDeclaredField("minTier");
			maxTier = cls.getDeclaredField("maxTier");
			minTier.setAccessible(true);
			maxTier.setAccessible(true);
		} catch (Exception e) {
			PlayerDifficulty.LOGGER.fatal("Failed to access Champion fields");
		}
	}

	public static Integer getMinTier(EntityManager.EntitySettings settings) {
		return ExceptionHandler.get(() -> (Integer) minTier.get(settings));
	}

	public static Integer getMaxTier(EntityManager.EntitySettings settings) {
		return ExceptionHandler.get(() -> (Integer) maxTier.get(settings));
	}

}
