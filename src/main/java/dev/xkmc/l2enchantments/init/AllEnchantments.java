package dev.xkmc.l2enchantments.init;

import dev.xkmc.l2library.repack.registrate.util.entry.RegistryEntry;
import dev.xkmc.l2enchantments.content.enchantments.core.BaseEnchantment;
import dev.xkmc.l2enchantments.content.enchantments.sword.*;
import dev.xkmc.l2enchantments.content.enchantments.tool.LifeSync;
import dev.xkmc.l2enchantments.content.enchantments.tool.Reach;
import dev.xkmc.l2enchantments.content.enchantments.tool.Remnant;
import dev.xkmc.l2enchantments.content.enchantments.tool.Robust;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

import java.util.function.Supplier;

import static dev.xkmc.l2enchantments.init.ModEntry.REGISTRATE;

public class AllEnchantments {

	public static final RegistryEntry<AntiMagic> ANTI_MAGIC = reg("anti_magic", AntiMagic::new);
	public static final RegistryEntry<SoulSlash> SOUL_SLASH = reg("soul_slash", SoulSlash::new);
	public static final RegistryEntry<StackingDamage> STACK_DMG = reg("stacking_damage", StackingDamage::new);
	public static final RegistryEntry<TracingDamage> TRACK_ENT = reg("tracing_damage", TracingDamage::new);
	public static final RegistryEntry<Fragile> FRAGILE = reg("fragile", Fragile::new);
	public static final RegistryEntry<LightSwing> LIGHT_SWING = reg("light_swing", LightSwing::new);
	public static final RegistryEntry<HeavySwing> HEAVY_SWING = reg("heavy_swing", HeavySwing::new);

	public static final RegistryEntry<Remnant> REMNANT = reg("remnant", Remnant::new);
	public static final RegistryEntry<Robust> ROBUST = reg("robust", Robust::new);
	public static final RegistryEntry<Reach> REACH = reg("reach", Reach::new);
	public static final RegistryEntry<LifeSync> LIFE_SYNC = reg("life_sync", LifeSync::new);

	public static void register() {

	}

	private static <T extends BaseEnchantment> RegistryEntry<T> reg(String id, Supplier<T> sup) {
		return REGISTRATE.enchantment(id, EnchantmentCategory.BREAKABLE, (a, b, c) -> sup.get()).defaultLang().register();

	}

}
