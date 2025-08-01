package entity;

import java.util.function.Supplier;

import entity.custom.AngryZombieEntity;
import entity.custom.NoIFrameArrowEntity;
import milkwater.milkmenagerie.MilkwatersMenagerie;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModEntities {
	public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
			DeferredRegister.create(BuiltInRegistries.ENTITY_TYPE, MilkwatersMenagerie.MODID);
	
	public static final Supplier<EntityType<AngryZombieEntity>> ANGRYZOMBIE =
			ENTITY_TYPES.register("angryzombie", () -> EntityType.Builder.of(AngryZombieEntity::new, MobCategory.MONSTER)
					.sized(1f, 2f).build("angryzombie"));
	
	public static final Supplier<EntityType<NoIFrameArrowEntity>> NO_IFRAME_ARROW =
			ENTITY_TYPES.register("noiframearrow", () -> EntityType.Builder.of(NoIFrameArrowEntity::new, MobCategory.MONSTER)
					.sized(1f, 2f).build("noiframearrow"));
	
	public static void register(IEventBus eventBus) {
		ENTITY_TYPES.register(eventBus);
	}
}
