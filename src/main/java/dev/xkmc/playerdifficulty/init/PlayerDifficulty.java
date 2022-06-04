package dev.xkmc.playerdifficulty.init;

import dev.xkmc.l2library.base.LcyRegistrate;
import dev.xkmc.l2library.repack.registrate.providers.ProviderType;
import dev.xkmc.l2library.serial.handler.Handlers;
import dev.xkmc.playerdifficulty.content.capability.PlayerLevel;
import dev.xkmc.playerdifficulty.content.enchantments.core.EnchantmentIngredient;
import dev.xkmc.playerdifficulty.events.AttackEventHandler;
import dev.xkmc.playerdifficulty.events.ItemStackEventHandler;
import dev.xkmc.playerdifficulty.events.MobSpawnEventHandler;
import dev.xkmc.playerdifficulty.init.data.ConfigGen;
import dev.xkmc.playerdifficulty.init.data.DifficultyConfig;
import dev.xkmc.playerdifficulty.init.data.LangData;
import dev.xkmc.playerdifficulty.init.data.LootGen;
import dev.xkmc.playerdifficulty.network.NetworkManager;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ParticleFactoryRegisterEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;
import net.minecraftforge.network.NetworkConstants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod("playerdifficulty")
public class PlayerDifficulty {

	public static final String MODID = "playerdifficulty";
	public static final Logger LOGGER = LogManager.getLogger();
	public static final LcyRegistrate REGISTRATE = new LcyRegistrate(MODID);

	private static void registerRegistrates(IEventBus bus) {
		PDItems.register();
		PDEnchantments.register();
		Handlers.register();
		NetworkManager.register();
		DifficultyConfig.init();
		PlayerLevel.register();


		REGISTRATE.addDataGenerator(ProviderType.LANG, LangData::genLang);
		REGISTRATE.addDataGenerator(ProviderType.LOOT, LootGen::genLoot);
	}

	private static void registerForgeEvents() {
		MinecraftForge.EVENT_BUS.register(MobSpawnEventHandler.class);
		MinecraftForge.EVENT_BUS.register(AttackEventHandler.class);
		MinecraftForge.EVENT_BUS.register(ItemStackEventHandler.class);
	}

	private static void registerModBusEvents(IEventBus bus) {
		bus.addListener(PlayerDifficulty::setup);
		bus.addListener(PDClient::clientSetup);
		bus.addListener(EventPriority.LOWEST, PlayerDifficulty::gatherData);
		bus.addListener(PlayerDifficulty::onParticleRegistryEvent);

	}

	private static void registerCommands() {
	}

	public PlayerDifficulty() {
		LOGGER.info("Player Difficulty Loading");
		NetworkConstants.init();
		FMLJavaModLoadingContext ctx = FMLJavaModLoadingContext.get();
		IEventBus bus = ctx.getModEventBus();
		registerModBusEvents(bus);
		bus.register(this);
		DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> PDClient.onCtorClient(bus, MinecraftForge.EVENT_BUS));
		registerRegistrates(bus);
		registerForgeEvents();
		registerCommands();
	}

	private static void setup(final FMLCommonSetupEvent event) {
		event.enqueueWork(() -> {
		});
	}

	public static void gatherData(GatherDataEvent event) {
		event.getGenerator().addProvider(new ConfigGen(event.getGenerator()));
	}

	public static void onParticleRegistryEvent(ParticleFactoryRegisterEvent event) {
	}

	@SubscribeEvent
	public void registerRecipeSerializers(RegistryEvent.Register<RecipeSerializer<?>> event) {
		CraftingHelper.register(new ResourceLocation(MODID, "enchantment"), EnchantmentIngredient.Serializer.INSTANCE);
	}

}
