package dev.xkmc.playerdifficulty.compat.scalinghealth;

import dev.xkmc.playerdifficulty.content.capability.DifficultyCap;
import dev.xkmc.playerdifficulty.init.data.DifficultyConfig;
import net.minecraft.world.entity.player.Player;
import net.silentchaos512.scalinghealth.capability.DifficultySourceCapability;
import net.silentchaos512.scalinghealth.capability.IDifficultySource;
import net.silentchaos512.scalinghealth.capability.IPlayerData;
import net.silentchaos512.scalinghealth.client.ClientHandler;

import java.util.Optional;

public class HealthScalingSpawn {

	public static Optional<DifficultyCap> getSource(Player player) {
		return player.getCapability(DifficultySourceCapability.INSTANCE).map(e -> new HSDifficultyCap(player, e));
	}

	public record HSDifficultyCap(Player player, IDifficultySource cap) implements DifficultyCap {

		private static float getFactor() {
			return (float) (double) DifficultyConfig.COMMON.scalingHealthFactor.get();
		}

		@Override
		public float getDifficultyValue() {
			if(player.level.isClientSide()){
				return ClientHandler.playerDifficulty * getFactor();
			}
			return cap.getDifficulty() * getFactor();
		}

		@Override
		public void setDifficultyValue(float value) {
			cap.setDifficulty(value / getFactor());
		}

		@Override
		public void syncToClient() {
			IPlayerData.sendUpdatePacketTo(player);
		}
	}

}
