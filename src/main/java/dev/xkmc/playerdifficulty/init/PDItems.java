package dev.xkmc.playerdifficulty.init;

import dev.xkmc.l2library.repack.registrate.util.entry.ItemEntry;
import dev.xkmc.playerdifficulty.content.item.LevelChangeItem;
import dev.xkmc.playerdifficulty.content.item.LevelCheckItem;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("unused")
public class PDItems {


	public static class Tab extends CreativeModeTab {

		public Tab(String id) {
			super(PlayerDifficulty.MODID + "." + id);
		}

		@NotNull
		@Override
		public ItemStack makeIcon() {
			return CHECK.asStack();
		}
	}


	public static final ItemEntry<LevelCheckItem> CHECK;
	public static final ItemEntry<LevelChangeItem> UP, DOWN;

	public static final Tab TAB_MAIN = new Tab("material");

	static {
		PlayerDifficulty.REGISTRATE.creativeModeTab(() -> TAB_MAIN);
		FoodProperties prop = new FoodProperties.Builder().nutrition(20).saturationMod(1.2f).alwaysEat().build();
		CHECK = PlayerDifficulty.REGISTRATE.item("check", LevelCheckItem::new).register();
		UP = PlayerDifficulty.REGISTRATE.item("up", p -> new LevelChangeItem(p.food(prop), 1)).register();
		DOWN = PlayerDifficulty.REGISTRATE.item("down", p -> new LevelChangeItem(p.food(prop), -1)).register();
	}

	public static void register() {

	}

}
