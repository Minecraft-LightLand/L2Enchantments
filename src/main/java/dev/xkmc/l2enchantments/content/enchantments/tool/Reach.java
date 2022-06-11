package dev.xkmc.l2enchantments.content.enchantments.tool;

import dev.xkmc.l2enchantments.content.enchantments.core.AttributeEnchantment;
import dev.xkmc.l2enchantments.init.data.ModConfig;
import dev.xkmc.l2library.util.MathHelper;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ArmorItem;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.event.ItemAttributeModifierEvent;

import java.util.UUID;
import java.util.function.Supplier;

public class Reach extends ToolEnchant implements AttributeEnchantment {

	public static final UUID ID_ATK = MathHelper.getUUIDfromString("reach.attack");
	public static final UUID ID_RCH = MathHelper.getUUIDfromString("reach.reach");

	public static final Supplier<Double> VALUE = ModConfig.COMMON.reachAddition::get;

	public Reach() {
		super(REACH);
	}

	@Override
	public void addAttributes(int lv, ItemAttributeModifierEvent event) {
		if (event.getItemStack().getItem() instanceof ArmorItem armor) {
			if (armor.getSlot() != event.getSlotType()) {
				return;
			}
		}
		if (event.getSlotType() != EquipmentSlot.MAINHAND) {
			return;
		}
		event.addModifier(ForgeMod.ATTACK_RANGE.get(), new AttributeModifier(ID_ATK, "reach", VALUE.get() * lv, AttributeModifier.Operation.ADDITION));
		event.addModifier(ForgeMod.REACH_DISTANCE.get(), new AttributeModifier(ID_RCH, "reach", VALUE.get() * lv, AttributeModifier.Operation.ADDITION));
	}

}
