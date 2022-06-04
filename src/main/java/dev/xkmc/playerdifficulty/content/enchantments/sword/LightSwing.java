package dev.xkmc.playerdifficulty.content.enchantments.sword;

import dev.xkmc.l2library.util.MathHelper;
import dev.xkmc.playerdifficulty.content.enchantments.core.AttributeEnchantment;
import dev.xkmc.playerdifficulty.content.enchantments.core.DurabilityEnchantment;
import dev.xkmc.playerdifficulty.content.enchantments.core.EnchConfig;
import dev.xkmc.playerdifficulty.init.data.DifficultyConfig;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraftforge.event.ItemAttributeModifierEvent;

import java.util.UUID;
import java.util.function.Supplier;

public class LightSwing extends SwordEnchant implements DurabilityEnchantment, AttributeEnchantment {

	public static final UUID ID_ATK = MathHelper.getUUIDfromString("light_swing.attack");
	public static final UUID ID_SPEED = MathHelper.getUUIDfromString("light_swing.speed");

	public static final Supplier<Double> ATK_FACTOR = DifficultyConfig.COMMON.lightSwingDamage::get;
	public static final Supplier<Double> SPEED_FACTOR = DifficultyConfig.COMMON.lightSwingSpeed::get;
	public static final Supplier<Double> DAMAGE_FACTOR = DifficultyConfig.COMMON.lightSwingDurabilityFactor::get;

	public LightSwing() {
		super(LIGHT_SWING);
	}

	@Override
	public double durabilityFactor(int lv, double damage) {
		return 1 + lv * DAMAGE_FACTOR.get();
	}

	@Override
	public void addAttributes(int lv, ItemAttributeModifierEvent event) {
		event.addModifier(Attributes.ATTACK_DAMAGE, new AttributeModifier(ID_ATK, "light_swing", -ATK_FACTOR.get() * lv, AttributeModifier.Operation.ADDITION));
		event.addModifier(Attributes.ATTACK_SPEED, new AttributeModifier(ID_ATK, "light_swing", SPEED_FACTOR.get() * lv, AttributeModifier.Operation.ADDITION));
	}
}
