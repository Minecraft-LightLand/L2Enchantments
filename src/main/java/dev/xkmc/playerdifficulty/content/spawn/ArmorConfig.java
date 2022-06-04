package dev.xkmc.playerdifficulty.content.spawn;

import dev.xkmc.l2library.network.BaseConfig;
import dev.xkmc.l2library.serial.SerialClass;
import dev.xkmc.playerdifficulty.init.data.DifficultyConfig;
import dev.xkmc.playerdifficulty.network.NetworkManager;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

@SerialClass
public class ArmorConfig extends BaseConfig {

	public static ArmorConfig cache;

	public static ArmorConfig getInstance() {
		if (cache != null) return cache;
		List<ArmorConfig> configs = NetworkManager.getConfigs("armor").map(e -> (ArmorConfig) e.getValue()).toList();
		ArrayList<Entry> list = BaseConfig.collectList(configs, e -> e.items);
		HashSet<EntityType<?>> types = BaseConfig.collectSet(configs, e -> e.types);
		ArmorConfig ans = new ArmorConfig();
		ans.items = list;
		ans.types = types;
		cache = ans;
		return ans;
	}

	public Item getItem(EquipmentSlot type, Mob e, float level, Random r) {
		List<Entry> list = new ArrayList<>();
		int sum = 0;
		for (Entry entry : items) {
			if (entry.matches(type, e) && entry.level <= level) {
				list.add(entry);
				sum += entry.weight;
			}
		}
		if (sum == 0) {
			return null;
		}
		int rand = r.nextInt(sum);
		for (Entry entry : list) {
			if (rand < entry.weight) {
				return entry.item;
			}
			rand -= entry.weight;
		}
		return null;
	}

	public ItemStack getItemStack(EquipmentSlot type, Mob e, float level, int enchant, Random r) {
		Item item = getItem(type, e, level, r);
		if (item == null) {
			return ItemStack.EMPTY;
		}
		ItemStack stack = item.getDefaultInstance();
		EnchantmentHelper.enchantItem(r, stack, enchant, true);
		return stack;
	}

	private boolean fit(Mob entity) {
		return types.contains(entity.getType());
	}

	public void fillEntity(Mob entity, float level, Random r) {
		if (!fit(entity))
			return;
		double enchant_factor = DifficultyConfig.COMMON.enchantLevelFactor.get();
		double armor_chance = DifficultyConfig.COMMON.armorChanceFactor.get();
		double dropChance = DifficultyConfig.COMMON.dropChance.get();
		for (EquipmentSlot slot : EquipmentSlot.values()) {
			if (slot.getType() != EquipmentSlot.Type.ARMOR)
				continue;
			if (!entity.getItemBySlot(slot).isEmpty())
				continue;
			if (r.nextDouble() > armor_chance * level)
				continue;
			ItemStack stack = getItemStack(slot, entity, level, (int) (level * enchant_factor), r);
			entity.setItemSlot(slot, stack);
			entity.setDropChance(slot, (float) dropChance);
		}
	}

	public void addItem(Item item, float level, int weight) {
		items.add(new Entry(item, level, weight));
	}

	public void addItems(float level, int weight, Item... items) {
		for (Item i : items) {
			addItem(i, level, weight);
		}
	}

	@SerialClass.SerialField
	public ArrayList<Entry> items = new ArrayList<>();

	@SerialClass.SerialField
	public HashSet<EntityType<?>> types = new HashSet<>();

	@SerialClass
	public static class Entry {

		@SerialClass.SerialField
		public float level;

		@SerialClass.SerialField
		public int weight;

		@SerialClass.SerialField
		public Item item;

		@Deprecated
		public Entry() {

		}

		public Entry(Item item, float level, int weight) {
			this.item = item;
			this.level = level;
			this.weight = weight;
		}

		public boolean matches(EquipmentSlot slot, Mob e) {
			if (slot.getType() == EquipmentSlot.Type.ARMOR) {
				if (item instanceof ArmorItem) {
					return ((ArmorItem) item).getSlot() == slot;
				}
				return false;
			}
			return false;
		}

	}

}
