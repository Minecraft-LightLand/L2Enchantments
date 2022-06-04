package dev.xkmc.playerdifficulty.content.item;

import dev.xkmc.playerdifficulty.content.capability.DifficultyCap;
import dev.xkmc.playerdifficulty.init.data.DifficultyConfig;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import javax.annotation.ParametersAreNonnullByDefault;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class LevelChangeItem extends LevelCheckItem {

	public final int change;

	public LevelChangeItem(Properties props, int change) {
		super(props);
		this.change = change;
	}

	public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entity) {
		if (entity instanceof ServerPlayer player) {
			DifficultyCap playerLevel = DifficultyCap.get(player);
			float next = playerLevel.getDifficultyValue() + change * DifficultyConfig.COMMON.altDifficulty.get();
			playerLevel.setDifficultyValue(Mth.clamp(next, 0, DifficultyConfig.COMMON.maxDifficulty.get()));
			playerLevel.syncToClient();
		}
		return super.finishUsingItem(stack, level, entity);
	}

}
