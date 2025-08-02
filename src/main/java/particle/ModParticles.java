package particle;

import java.util.function.Supplier;

import milkwater.milkmenagerie.MilkwatersMenagerie;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModParticles {
	// ignore me
	public static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES =
			DeferredRegister.create(BuiltInRegistries.PARTICLE_TYPE, MilkwatersMenagerie.MODID);
	public static void register(IEventBus eventBus) {
		PARTICLE_TYPES.register(eventBus);
	}
	
	// my custom particles
	public static final Supplier<SimpleParticleType> STAR =
			PARTICLE_TYPES.register("star_particles", () -> new SimpleParticleType(true));
}
