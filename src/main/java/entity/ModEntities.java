package entity;

import java.util.function.Supplier;

import entity.custom.FireArrowEntity;
import entity.custom.StarfuryStarEntity;
import entity.custom.WandOfSparkingProjectileEntity;
import entity.custom.YoyoProjectileEntity;
import milkwater.milkmenagerie.MilkwatersMenagerie;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModEntities {
	// ignore me
	public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
			DeferredRegister.create(BuiltInRegistries.ENTITY_TYPE, MilkwatersMenagerie.MODID);
	
	// non monster entities
	public static final Supplier<EntityType<StarfuryStarEntity>> STARFURY_STAR =
			ENTITY_TYPES.register("starfurystar", () -> EntityType.Builder.of(StarfuryStarEntity::new, MobCategory.MISC)
					.sized(1f, 2f)
					.build("starfurystar"));
	public static final Supplier<EntityType<FireArrowEntity>> FIRE_ARROW =
			ENTITY_TYPES.register("firearrow", () -> EntityType.Builder.<FireArrowEntity>of(FireArrowEntity::new, MobCategory.MISC)
					.sized(.5f, .5f)
					.clientTrackingRange(4)
		            .updateInterval(20)
		            .build("firearrow"));
	public static final Supplier<EntityType<YoyoProjectileEntity>> YOYO_PROJECTILE =
			ENTITY_TYPES.register("yoyoprojectile", () -> EntityType.Builder.<YoyoProjectileEntity>of(YoyoProjectileEntity::new, MobCategory.MISC)
					.sized(.5f, .5f)
				    .setShouldReceiveVelocityUpdates(true)
				    .clientTrackingRange(4)
		            .updateInterval(1)
					.build("yoyoprojectile"));
	public static final Supplier<EntityType<WandOfSparkingProjectileEntity>> SPARK =
			ENTITY_TYPES.register("spark", () -> EntityType.Builder.<WandOfSparkingProjectileEntity>of(WandOfSparkingProjectileEntity::new, MobCategory.MISC)
					.sized(.2f, .2f)
					.build("spark"));
	
	// ignore me
	public static void register(IEventBus eventBus) {
		ENTITY_TYPES.register(eventBus);
	}
}
