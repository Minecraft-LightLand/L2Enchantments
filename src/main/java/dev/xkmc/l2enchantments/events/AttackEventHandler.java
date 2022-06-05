package dev.xkmc.l2enchantments.events;

import dev.xkmc.l2enchantments.content.enchantments.sword.SwordEnchant;
import dev.xkmc.l2enchantments.init.ModEntryPoint;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.entity.player.CriticalHitEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@SuppressWarnings("unused")
public class AttackEventHandler {

	public enum Stage {
		PLAYER_ATTACK, CRITICAL_HIT, HURT, ACTUALLY_HURT, DAMAGE;
	}

	public static class AttackCache {

		public Stage stage;
		public AttackEntityEvent player;
		public CriticalHitEvent crit;
		public LivingAttackEvent attack;
		public LivingHurtEvent hurt;
		public LivingDamageEvent damage;

		public LivingEntity target;
		public LivingEntity attacker;
		public ItemStack weapon;

		public float strength = -1;
		public float damage_3, damage_4, damage_5;

		private void pushPlayer(AttackEntityEvent event) {
			stage = Stage.PLAYER_ATTACK;
			player = event;
			strength = event.getPlayer().getAttackStrengthScale(1);
		}

		private void pushCrit(CriticalHitEvent event) {
			stage = Stage.CRITICAL_HIT;
			crit = event;
		}

		private void pushAttack(LivingAttackEvent event) {
			stage = Stage.HURT;
			attack = event;
			target = attack.getEntityLiving();
			damage_3 = event.getAmount();
			if (weapon != null) {
				for (Map.Entry<Enchantment, Integer> ent : EnchantmentHelper.getEnchantments(weapon).entrySet()) {
					if (ent.getKey() instanceof SwordEnchant sword) {
						sword.onTargetAttacked(ent.getValue(), event, this);
					}
				}
			}
		}

		private void pushHurt(LivingHurtEvent event) {
			stage = Stage.ACTUALLY_HURT;
			hurt = event;
			damage_4 = event.getAmount();
			if (weapon != null) {
				for (Map.Entry<Enchantment, Integer> ent : EnchantmentHelper.getEnchantments(weapon).entrySet()) {
					if (ent.getKey() instanceof SwordEnchant sword) {
						damage_4 += sword.getAdditionalDamage(ent.getValue(), event, this);
					}
				}
				event.setAmount(damage_4);
				for (Map.Entry<Enchantment, Integer> ent : EnchantmentHelper.getEnchantments(weapon).entrySet()) {
					if (ent.getKey() instanceof SwordEnchant sword) {
						sword.onTargetHurt(ent.getValue(), event, this);
					}
				}
			}
		}

		private void pushDamage(LivingDamageEvent event) {
			stage = Stage.DAMAGE;
			damage = event;
			damage_5 = event.getAmount();
			if (weapon != null) {
				for (Map.Entry<Enchantment, Integer> ent : EnchantmentHelper.getEnchantments(weapon).entrySet()) {
					if (ent.getKey() instanceof SwordEnchant sword) {
						sword.onTargetDamage(ent.getValue(), event, this);
					}
				}
			}
		}

		private void setupAttackerProfile(LivingEntity entity, ItemStack stack) {
			attacker = entity;
			weapon = stack;
		}

	}

	private static final HashMap<UUID, AttackCache> CACHE = new HashMap<>();

	@SubscribeEvent
	public static void onPlayerAttack(AttackEntityEvent event) {
		AttackCache cache = new AttackCache();
		CACHE.put(event.getTarget().getUUID(), cache);
		cache.pushPlayer(event);
	}

	@SubscribeEvent
	public static void onCriticalHit(CriticalHitEvent event) {
		AttackCache cache = new AttackCache();
		CACHE.put(event.getTarget().getUUID(), cache);
		cache.pushCrit(event);
	}

	@SubscribeEvent(priority = EventPriority.HIGHEST)
	public static void onAttackPre(LivingAttackEvent event) {
		if (CACHE.size() > 100) {
			ModEntryPoint.LOGGER.error("attack cache too large: " + CACHE.size());
		}
		UUID id = event.getEntityLiving().getUUID();
		AttackCache cache = CACHE.get(id);
		if (cache.stage.ordinal() >= Stage.HURT.ordinal()) {
			cache = new AttackCache();
			CACHE.put(id, cache);
		}
		cache.pushAttack(event);
		DamageSource source = event.getSource();
		if (source.getEntity() instanceof LivingEntity entity) { // direct damage only
			ItemStack stack = entity.getMainHandItem();
			cache.setupAttackerProfile(entity, stack);
		}
	}

	@SubscribeEvent(priority = EventPriority.HIGHEST)
	public static void onActuallyHurtPre(LivingHurtEvent event) {
		AttackCache cache = CACHE.get(event.getEntityLiving().getUUID());
		if (cache != null)
			cache.pushHurt(event);
		else {
			ModEntryPoint.LOGGER.error("incorrect sequence at hurt: " + event.getEntityLiving());
		}
	}

	@SubscribeEvent(priority = EventPriority.HIGHEST)
	public static void onDamagePre(LivingDamageEvent event) {
		AttackCache cache = CACHE.get(event.getEntityLiving().getUUID());
		if (cache != null)
			cache.pushDamage(event);
		else {
			ModEntryPoint.LOGGER.error("incorrect sequence at damage: " + event.getEntityLiving());
		}
	}

	@SubscribeEvent
	public static void onDeath(LivingDeathEvent event) {

	}

	@SubscribeEvent
	public static void onServerTick(TickEvent.ServerTickEvent event) {
		CACHE.clear();
	}

}
