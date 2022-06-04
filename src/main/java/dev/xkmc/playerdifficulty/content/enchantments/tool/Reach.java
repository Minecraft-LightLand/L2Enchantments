package dev.xkmc.playerdifficulty.content.enchantments.tool;

import dev.xkmc.l2library.util.MathHelper;
import dev.xkmc.playerdifficulty.content.enchantments.core.AttributeEnchantment;
import dev.xkmc.playerdifficulty.content.enchantments.core.EnchConfig;
import dev.xkmc.playerdifficulty.content.enchantments.sword.SwordEnchant;
import dev.xkmc.playerdifficulty.init.data.DifficultyConfig;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.event.ItemAttributeModifierEvent;

import java.util.UUID;
import java.util.function.Supplier;

public class Reach extends ToolEnchant implements AttributeEnchantment {

	public static final UUID ID = MathHelper.getUUIDfromString("reach");

	public static final Supplier<Double> VALUE = DifficultyConfig.COMMON.reachAddition::get;

	public Reach() {
		super(REACH);
	}

	@Override
	public void addAttributes(int lv, ItemAttributeModifierEvent event) {
		event.addModifier(ForgeMod.ATTACK_RANGE.get(), new AttributeModifier(ID, "reach", VALUE.get() * lv, AttributeModifier.Operation.ADDITION));
	}

}
