package dev.xkmc.l2enchantments.init;

import dev.xkmc.l2enchantments.content.enchantments.core.EnchantmentIngredient;
import dev.xkmc.l2enchantments.events.AttackEventHandler;
import dev.xkmc.l2enchantments.events.ItemStackEventHandler;
import dev.xkmc.l2enchantments.init.data.ModConfig;
import dev.xkmc.l2library.base.LcyRegistrate;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod("l2enchantments")
public class ModEntry {

	public static final String MODID = "l2enchantments";
	public static final Logger LOGGER = LogManager.getLogger();
	public static final LcyRegistrate REGISTRATE = new LcyRegistrate(MODID);

	private static void registerRegistrates(IEventBus bus) {
		AllEnchantments.register();
		ModConfig.init();
	}

	private static void registerForgeEvents() {
		MinecraftForge.EVENT_BUS.register(AttackEventHandler.class);
		MinecraftForge.EVENT_BUS.register(ItemStackEventHandler.class);
	}

	private static void registerModBusEvents(IEventBus bus) {
		bus.addListener(ModEntry::setup);
		bus.addListener(ModClient::clientSetup);
	}

	public ModEntry() {
		FMLJavaModLoadingContext ctx = FMLJavaModLoadingContext.get();
		IEventBus bus = ctx.getModEventBus();
		registerModBusEvents(bus);
		bus.register(this);
		DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> ModClient.onCtorClient(bus, MinecraftForge.EVENT_BUS));
		registerRegistrates(bus);
		registerForgeEvents();
	}

	private static void setup(final FMLCommonSetupEvent event) {
		event.enqueueWork(() -> {
		});
	}

	@SubscribeEvent
	public void registerRecipeSerializers(RegistryEvent.Register<RecipeSerializer<?>> event) {
		CraftingHelper.register(new ResourceLocation(MODID, "enchantment"), EnchantmentIngredient.Serializer.INSTANCE);
	}

}
