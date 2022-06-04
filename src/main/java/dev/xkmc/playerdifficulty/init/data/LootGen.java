package dev.xkmc.playerdifficulty.init.data;

import dev.xkmc.l2library.repack.registrate.providers.loot.RegistrateLootTableProvider;
import dev.xkmc.playerdifficulty.compat.CompatManager;

public class LootGen {

	public static void genLoot(RegistrateLootTableProvider pvd) {
		CompatManager.onLootGen(pvd);
	}

}
