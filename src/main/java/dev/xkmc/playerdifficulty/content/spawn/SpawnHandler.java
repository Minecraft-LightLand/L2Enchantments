package dev.xkmc.playerdifficulty.content.spawn;

import dev.xkmc.playerdifficulty.content.capability.DifficultyCap;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;

import java.util.Optional;
import java.util.Random;

public class SpawnHandler {

	public static void onSpawn(DifficultyCap cap, LivingEntity e) {
		if (e instanceof Mob mob) {
			float diff = cap.getDifficultyValue();
			ArmorConfig.getInstance().fillEntity(mob, diff, new Random());
			Optional.ofNullable(WeaponConfig.getInstance()).ifPresent(x -> x.fillEntity(mob, diff, new Random()));
		}
	}

}
