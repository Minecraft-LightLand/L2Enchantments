package dev.xkmc.l2enchantments.content.enchantments.core;

import net.minecraftforge.event.ItemAttributeModifierEvent;

public interface AttributeEnchantment {

	void addAttributes(int lv, ItemAttributeModifierEvent event);

}
