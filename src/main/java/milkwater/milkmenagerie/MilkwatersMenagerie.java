package milkwater.milkmenagerie;

import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import entity.ModEntities;
import entity.client.FireArrowRenderer;
import entity.client.StarfuryStarRenderer;
import item.ModItems;
import net.minecraft.ChatFormatting;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import particle.ModParticles;
import particle.custom.StarParticle;
import util.ModRarities;

// The value here should match an entry in the META-INF/neoforge.mods.toml file
@Mod(MilkwatersMenagerie.MODID)
public class MilkwatersMenagerie {
    // Do not touch
    public static final String MODID = "milkwatersmenagerie";
    public static final Logger LOGGER = LogUtils.getLogger();
    
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MODID);

    // Creates a creative tab with my stuff
    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> MILKWATERS_TAB = CREATIVE_MODE_TABS.register("example_tab", () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup.milkwatersmenagerie"))
            .withTabsBefore(CreativeModeTabs.COMBAT)
            .icon(() -> ModItems.STARFURY_ITEM.get().getDefaultInstance())
            .displayItems((parameters, output) -> {
                output.accept(ModItems.ZOMBIE_ARM_ITEM.get());
                output.accept(ModItems.SPEAR_ITEM.get());
                output.accept(ModItems.BLADED_GLOVE_ITEM.get());
                output.accept(ModItems.STARFURY_ITEM.get());
                output.accept(ModItems.TSUNAMI_ITEM.get());
                output.accept(ModItems.NIGHTS_EDGE_ITEM.get());
                output.accept(ModItems.FIRE_ARROW_ITEM.get());
            }).build());

    // The constructor for the mod class is the first code that is run when your mod is loaded.
    // FML will recognize some parameter types like IEventBus or ModContainer and pass them in automatically.
    public MilkwatersMenagerie(IEventBus modEventBus, ModContainer modContainer) {
        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);

        // Register the Deferred Registers to the mod event bus so EVERYTHING gets registered
        ModItems.ITEMS.register(modEventBus);
        ModEntities.register(modEventBus);
        ModParticles.register(modEventBus);
        CREATIVE_MODE_TABS.register(modEventBus);

        // Register ourselves for server and other game events we are interested in.
        // Note that this is necessary if and only if we want *this* class (MilkwatersMenagerie) to respond directly to events.
        // Do not add this line if there are no @SubscribeEvent-annotated functions in this class, like onServerStarting() below.
        // NeoForge.EVENT_BUS.register(this);

        // Register our mod's ModConfigSpec so that FML can create and load the config file for us
        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    private void commonSetup(FMLCommonSetupEvent event) {
        // Some common setup code
        LOGGER.info("HELLO FROM COMMON SETUP");
        
        // legacy support for mods that show rarity in a weird way
        ModRarities.ALL_CUSTOM_RARITIES.forEach(proxy -> {
            Rarity rarity = proxy.getValue();
            Style style = rarity.getStyleModifier().apply(Style.EMPTY);
            ChatFormatting formatting = ChatFormatting.valueOf(style.getColor().serialize().toUpperCase());
            rarity.color = formatting;
        });

        if (Config.LOG_DIRT_BLOCK.getAsBoolean()) {
            LOGGER.info("DIRT BLOCK >> {}", BuiltInRegistries.BLOCK.getKey(Blocks.DIRT));
        }

        LOGGER.info("{}{}", Config.MAGIC_NUMBER_INTRODUCTION.get(), Config.MAGIC_NUMBER.getAsInt());

        Config.ITEM_STRINGS.get().forEach((item) -> LOGGER.info("ITEM >> {}", item));
    }
    
    @EventBusSubscriber(modid = MODID, value = Dist.CLIENT)
    public static class ClientModEvents{
    	@SubscribeEvent
    	public static void onClientSetup(FMLClientSetupEvent event) {
    		event.enqueueWork(() -> {
                EntityRenderers.register(ModEntities.STARFURY_STAR.get(), StarfuryStarRenderer::new);
                EntityRenderers.register(ModEntities.FIRE_ARROW.get(), FireArrowRenderer::new);
            });
    	}
    	
    	@SubscribeEvent
    	public static void registerParticleFactories(RegisterParticleProvidersEvent event) {
    		event.registerSpriteSet(ModParticles.STAR.get(), StarParticle.Provider::new);
    	}
    }
}
