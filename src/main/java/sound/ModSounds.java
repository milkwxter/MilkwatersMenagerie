package sound;

import java.util.function.Supplier;

import milkwater.milkmenagerie.MilkwatersMenagerie;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModSounds {
	public static final DeferredRegister<SoundEvent> SOUND_EVENTS =
			DeferredRegister.create(BuiltInRegistries.SOUND_EVENT, MilkwatersMenagerie.MODID);
	
	public static final Supplier<SoundEvent> GENERIC_ITEM_USE = registerSoundEvent("generic_item_use");
	public static final Supplier<SoundEvent> GENERIC_IMPACT = registerSoundEvent("generic_impact");
	public static final Supplier<SoundEvent> MAGIC_CAST = registerSoundEvent("magic_cast");
	
	private static Supplier<SoundEvent> registerSoundEvent(String name){
		ResourceLocation id = ResourceLocation.fromNamespaceAndPath(MilkwatersMenagerie.MODID, name);
		return SOUND_EVENTS.register(name, () -> SoundEvent.createVariableRangeEvent(id));
	}
	
	public static void register(IEventBus eventBus) {
		SOUND_EVENTS.register(eventBus);
	}
}
