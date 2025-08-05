package milkwater.milkmenagerie;

import java.util.Arrays;

import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import entity.ModEntities;
import entity.client.FireArrowRenderer;
import entity.client.StarfuryStarRenderer;
import item.ModItems;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.block.Block;
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
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import particle.ModParticles;
import particle.custom.StarParticle;

// The value here should match an entry in the META-INF/neoforge.mods.toml file
@Mod(MilkwatersMenagerie.MODID)
public class MilkwatersMenagerie {
    // Do not touch
    public static final String MODID = "milkwatersmenagerie";
    public static final Logger LOGGER = LogUtils.getLogger();
    
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(MODID);
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MODID);

    
    public static final DeferredBlock<Block> ROTTENFLESH_BLOCK = BLOCKS.registerSimpleBlock("rotten_flesh_block");

    // Creates a creative tab with my stuff
    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> MILKWATERS_TAB = CREATIVE_MODE_TABS.register("example_tab", () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup.milkwatersmenagerie"))
            .withTabsBefore(CreativeModeTabs.COMBAT)
            .icon(() -> ModItems.ROTTENFLESH_BLOCK_ITEM.get().getDefaultInstance())
            .displayItems((parameters, output) -> {
                output.accept(ModItems.ROTTENFLESH_BLOCK_ITEM.get());
                output.accept(ModItems.ZOMBIE_ARM_ITEM.get());
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
        BLOCKS.register(modEventBus);
        ModItems.ITEMS.register(modEventBus);
        ModEntities.register(modEventBus);
        ModParticles.register(modEventBus);
        CREATIVE_MODE_TABS.register(modEventBus);
        
        System.out.println(Arrays.toString(Rarity.values()));

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
