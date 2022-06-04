package dev.xkmc.playerdifficulty.network;

import dev.xkmc.l2library.network.BaseConfig;
import dev.xkmc.l2library.network.PacketHandlerWithConfig;
import dev.xkmc.playerdifficulty.content.spawn.ArmorConfig;
import dev.xkmc.playerdifficulty.content.spawn.WeaponConfig;
import dev.xkmc.playerdifficulty.init.PlayerDifficulty;
import net.minecraft.resources.ResourceLocation;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Stream;

public class NetworkManager {

	public static final PacketHandlerWithConfig HANDLER = new PacketHandlerWithConfig(
			new ResourceLocation(PlayerDifficulty.MODID, "main"), 1, "difficulty_config"
	);

	public static Stream<Map.Entry<String, BaseConfig>> getConfigs(String id) {
		return HANDLER.CONFIGS.entrySet().stream()
				.filter(e -> {
					String path = new ResourceLocation(e.getKey()).getPath();
					String[] paths = path.split("/");
					return paths[0].equals(id);
				});
	}

	public static BaseConfig getConfig(String id) {
		return HANDLER.CONFIGS.get(PlayerDifficulty.MODID + ":" + id);
	}

	public static void register() {
		HANDLER.addAfterReloadListener(() -> {
			ArmorConfig.cache = null;
			WeaponConfig.cache = null;
		});
	}

}
