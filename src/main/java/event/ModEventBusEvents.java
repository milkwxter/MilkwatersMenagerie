package event;

import entity.ModEntities;
import entity.client.AngryZombieModel;
import entity.custom.AngryZombieEntity;
import milkwater.milkmenagerie.MilkwatersMenagerie;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.phys.EntityHitResult;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.event.entity.ProjectileImpactEvent;
import net.minecraft.world.phys.HitResult;

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
	
	@SubscribeEvent
	public static void onArrowImpact(ProjectileImpactEvent event) {
	    Entity projectile = event.getEntity();

	    // Only proceed if it's a vanilla Arrow with your custom tag
	    if (projectile instanceof Arrow arrow && arrow.getPersistentData().getBoolean("RemoveIFrames")) {

	        // Check if we hit an entity
	        if (event.getRayTraceResult().getType() == HitResult.Type.ENTITY) {
	            EntityHitResult hitResult = (EntityHitResult) event.getRayTraceResult();
	            Entity hitEntity = hitResult.getEntity();

	            if (hitEntity instanceof LivingEntity living) {
	                living.invulnerableTime = 0;
	            }
	        }
	    }
	}
}
