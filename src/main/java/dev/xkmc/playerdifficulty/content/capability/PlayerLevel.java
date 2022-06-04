package dev.xkmc.playerdifficulty.content.capability;

import dev.xkmc.l2library.capability.player.PlayerCapabilityHolder;
import dev.xkmc.l2library.capability.player.PlayerCapabilityNetworkHandler;
import dev.xkmc.l2library.capability.player.PlayerCapabilityTemplate;
import dev.xkmc.l2library.serial.SerialClass;
import dev.xkmc.l2library.util.ServerOnly;
import dev.xkmc.playerdifficulty.init.PlayerDifficulty;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;

@SerialClass
public class PlayerLevel extends PlayerCapabilityTemplate<PlayerLevel> {

	public static final Capability<PlayerLevel> CAPABILITY = CapabilityManager.get(new CapabilityToken<>() {
	});

	public static final PlayerCapabilityHolder<PlayerLevel> HOLDER = new PlayerCapabilityHolder<>(
			new ResourceLocation(PlayerDifficulty.MODID, "player_data"), CAPABILITY,
			PlayerLevel.class, PlayerLevel::new, PlayerCapabilityNetworkHandler::new
	);

	public static void register() {

	}

	@SerialClass.SerialField
	public int difficulty;

	public float getDifficulty() {
		return difficulty;
	}

	public void setDifficulty(float difficulty) {
		this.difficulty = Math.round(difficulty);
	}

	@ServerOnly
	public void syncToClient() {
		HOLDER.network.toClientSyncAll((ServerPlayer) player);
	}

}
