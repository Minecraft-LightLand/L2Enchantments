package dev.xkmc.playerdifficulty.init.data;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;
import org.apache.commons.lang3.tuple.Pair;

public class DifficultyConfig {

	public static class Common {

		public final ForgeConfigSpec.IntValue maxDifficulty;
		public final ForgeConfigSpec.IntValue altDifficulty;

		public final ForgeConfigSpec.DoubleValue antiMagicChance;
		public final ForgeConfigSpec.DoubleValue soulSlashChance;
		public final ForgeConfigSpec.DoubleValue stackingRatio;
		public final ForgeConfigSpec.DoubleValue tracingRatio;
		public final ForgeConfigSpec.DoubleValue fragileDamageFactor;
		public final ForgeConfigSpec.DoubleValue fragileDurabilityFactor;
		public final ForgeConfigSpec.DoubleValue lightSwingDamage;
		public final ForgeConfigSpec.DoubleValue lightSwingSpeed;
		public final ForgeConfigSpec.DoubleValue lightSwingDurabilityFactor;
		public final ForgeConfigSpec.DoubleValue heavySwingDamage;
		public final ForgeConfigSpec.DoubleValue heavySwingSpeed;
		public final ForgeConfigSpec.DoubleValue heavySwingDurabilityFactor;
		public final ForgeConfigSpec.DoubleValue reachAddition;
		public final ForgeConfigSpec.DoubleValue lifeSync;
		public final ForgeConfigSpec.DoubleValue windSweepIncrement;

		public final ForgeConfigSpec.DoubleValue enchantLevelFactor;
		public final ForgeConfigSpec.DoubleValue armorChanceFactor;
		public final ForgeConfigSpec.DoubleValue weaponChanceFactor;
		public final ForgeConfigSpec.DoubleValue dropChance;

		public final ForgeConfigSpec.BooleanValue championEnable;
		public final ForgeConfigSpec.IntValue championsTierFactor;
		public final ForgeConfigSpec.IntValue championsTierSeparation;

		public final ForgeConfigSpec.BooleanValue apotheosisEnable;
		public final ForgeConfigSpec.IntValue apotheosisTierFactor;
		public final ForgeConfigSpec.IntValue apotheosisTierSeparation;
		public final ForgeConfigSpec.DoubleValue apotheosisTierChance;

		public final ForgeConfigSpec.DoubleValue scalingHealthFactor;

		Common(ForgeConfigSpec.Builder builder) {
			builder.push("difficulty");
			maxDifficulty = builder.comment("Maximum Difficulty")
					.defineInRange("maxDifficulty", 250, 5, 10000);
			altDifficulty = builder.comment("Change in difficulty when using up or down item")
					.defineInRange("altDifficulty", 10, 1, 10000);
			builder.pop();

			builder.push("enchantments");
			antiMagicChance = builder.comment("Anti Magic chance")
					.defineInRange("antiMagicChance", 0.1, 0, 1);

			soulSlashChance = builder.comment("Soul Slash chance")
					.defineInRange("soulSlashChance", 0.1, 0, 1);

			stackingRatio = builder.comment("Stacking damage enchantment damage increment multiplier")
					.defineInRange("stackingRatio", 0.1, 0, 1);

			tracingRatio = builder.comment("Tracing damage enchantment increment base multiplier")
					.defineInRange("tracingRatio", 0.02, 0, 1);

			fragileDamageFactor = builder.comment("Fragile enchantment damage multiplier")
					.defineInRange("fragileDamageFactor", 0.1, 0, 1);
			fragileDurabilityFactor = builder.comment("Fragile enchantment durability multiplier")
					.defineInRange("fragileDurabilityFactor", 0.5, 0, 100);

			lightSwingDamage = builder.comment("Light swing enchantment damage reduction")
					.defineInRange("lightSwingDamage", 1d, 0, 100);
			lightSwingSpeed = builder.comment("Light swing enchantment speed increment")
					.defineInRange("lightSwingSpeed", 0.2, 0, 100);
			lightSwingDurabilityFactor = builder.comment("Light swing enchantment durability cost multiplier")
					.defineInRange("lightSwingDurabilityFactor", 0.5, 0, 100);

			heavySwingDamage = builder.comment("Heavy swing enchantment damage increment")
					.defineInRange("heavySwingDamage", 1d, 0, 100);
			heavySwingSpeed = builder.comment("Heavy swing enchantment speed reduction")
					.defineInRange("heavySwingSpeed", 0.2, 0, 100);
			heavySwingDurabilityFactor = builder.comment("Heavy swing enchantment durability multiplier")
					.defineInRange("heavySwingDurabilityFactor", 0.5, 0, 100);

			reachAddition = builder.comment("Reach enchantment value increment")
					.defineInRange("reachAddition", 0.5d, 0, 4);

			lifeSync = builder.comment("Threshold reduction for lifeSync per level")
					.defineInRange("lifeSync", 0.2d, 0, 1);

			windSweepIncrement = builder.comment("Wind Sweep hit box increment")
					.defineInRange("windSweep", 0.5, 0, 100);

			builder.pop();

			builder.push("equipment");
			enchantLevelFactor = builder.comment("Enchantment level factor")
					.defineInRange("enchantLevelFactor", 0.4, 0.001, 1);
			armorChanceFactor = builder.comment("armor spawn chance factor")
					.defineInRange("armorChanceFactor", 0.01, 0.001, 1);
			weaponChanceFactor = builder.comment("weapon spawn chance factor")
					.defineInRange("weaponChanceFactor", 0.02, 0.001, 1);
			dropChance = builder.comment("equipment drop chance")
					.defineInRange("dropChance", 0.2, 0.085, 1);
			builder.pop();

			builder.push("champion");
			championEnable = builder.comment("enable champion module in general")
					.define("championEnable", true);
			championsTierFactor = builder.comment("How many difficulties to match one tier")
					.defineInRange("championsTierFactor", 50, 1, 100);
			championsTierSeparation = builder.comment("To what power does tier spawn chance to differ")
					.defineInRange("championsTierSeparation", 2, 1, 10);
			builder.pop();

			builder.push("apotheosis");
			apotheosisEnable = builder.comment("enable apotheosis module in general (note: apotheosis deadly module is not completed yet)")
					.define("apotheosisEnable", false);
			apotheosisTierFactor = builder.comment("How many difficulties to match one tier")
					.defineInRange("apotheosisTierFactor", 50, 1, 100);
			apotheosisTierSeparation = builder.comment("To what power does tier spawn chance to differ")
					.defineInRange("apotheosisTierSeparation", 2, 1, 10);
			apotheosisTierChance = builder.comment("Successive chance to generate next tier item")
					.defineInRange("apotheosisTierChance", 0.2, 0.01, 0.9);
			builder.pop();

			builder.push("scalingHealth");
			scalingHealthFactor = builder.comment("ScalingHealth difficulty to PlayerDifficulty difficulty ratio")
					.defineInRange("scalingHealthFactor", 1, 0.001, 1000);
			builder.pop();

		}

	}

	public static final ForgeConfigSpec COMMON_SPEC;
	public static final Common COMMON;

	static {
		final Pair<Common, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(Common::new);
		COMMON_SPEC = specPair.getRight();
		COMMON = specPair.getLeft();
	}

	/**
	 * Registers any relevant listeners for config
	 */
	public static void init() {
		ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, DifficultyConfig.COMMON_SPEC);
	}


}
