package entity;

import java.util.function.Supplier;

import entity.custom.AngryZombieEntity;
import entity.custom.StarfuryStarEntity;
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
	
	// monster entities
	public static final Supplier<EntityType<AngryZombieEntity>> ANGRYZOMBIE =
			ENTITY_TYPES.register("angryzombie", () -> EntityType.Builder.of(AngryZombieEntity::new, MobCategory.MONSTER)
					.sized(1f, 2f).build("angryzombie"));
	
	// non monster entities
	public static final Supplier<EntityType<StarfuryStarEntity>> STARFURY_STAR =
			ENTITY_TYPES.register("starfurystar", () -> EntityType.Builder.of(StarfuryStarEntity::new, MobCategory.MISC)
					.sized(1f, 2f).build("starfurystar"));
	
	// ignore me
	public static void register(IEventBus eventBus) {
		ENTITY_TYPES.register(eventBus);
	}
}
