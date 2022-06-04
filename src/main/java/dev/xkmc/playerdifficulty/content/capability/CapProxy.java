package dev.xkmc.playerdifficulty.content.capability;

import dev.xkmc.l2library.util.Proxy;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class CapProxy {

	@OnlyIn(Dist.CLIENT)
	public static DifficultyCap getHandler() {
		return DifficultyCap.get(Proxy.getClientPlayer());
	}

}
