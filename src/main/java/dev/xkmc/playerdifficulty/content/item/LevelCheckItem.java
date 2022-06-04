package dev.xkmc.playerdifficulty.content.item;

import dev.xkmc.l2library.util.Proxy;
import dev.xkmc.playerdifficulty.content.capability.CapProxy;
import dev.xkmc.playerdifficulty.content.capability.PlayerLevel;
import dev.xkmc.playerdifficulty.init.data.LangData;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class LevelCheckItem extends Item {

	public LevelCheckItem(Properties props) {
		super(props);
	}

	@Override
	public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> list, TooltipFlag flag) {
		if (level != null && level.isClientSide() && Proxy.getPlayer() != null && PlayerLevel.HOLDER.isProper(Proxy.getPlayer())) {
			list.add(LangData.DIFFICULTY.get(Math.round(CapProxy.getHandler().getDifficultyValue())));
		}
		super.appendHoverText(stack, level, list, flag);
	}

	@Override
	public boolean isFoil(ItemStack stack) {
		return true;
	}

}
