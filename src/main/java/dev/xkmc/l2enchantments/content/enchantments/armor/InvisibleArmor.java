package dev.xkmc.l2enchantments.content.enchantments.armor;

import dev.xkmc.l2enchantments.content.enchantments.core.AttributeEnchantment;
import dev.xkmc.l2enchantments.init.data.ModConfig;
import dev.xkmc.l2library.util.MathHelper;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraftforge.event.ItemAttributeModifierEvent;

import java.util.UUID;
import java.util.function.Supplier;

public class InvisibleArmor extends ArmorEnchant {

	public InvisibleArmor() {
		super(INVISIBLE_ARMOR);
	}

}
