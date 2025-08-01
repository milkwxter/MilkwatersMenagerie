package event;

import entity.ModEntities;
import entity.client.AngryZombieModel;
import entity.custom.AngryZombieEntity;
import milkwater.milkmenagerie.MilkwatersMenagerie;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;

@EventBusSubscriber(modid = MilkwatersMenagerie.MODID)
public class ModEventBusEvents {
	@SubscribeEvent
	public static void registerLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
		event.registerLayerDefinition(AngryZombieModel.LAYER_LOCATION, AngryZombieModel::createBodyLayer);
	}
	
	@SubscribeEvent
	public static void registerAttributes(EntityAttributeCreationEvent event) {
		event.put(ModEntities.ANGRYZOMBIE.get(), AngryZombieEntity.createAttributes().build());
	}
}
