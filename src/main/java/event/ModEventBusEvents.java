package event;

import milkwater.milkmenagerie.MilkwatersMenagerie;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.phys.EntityHitResult;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.ProjectileImpactEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import player.ManaHelper;
import net.minecraft.world.phys.HitResult;

@EventBusSubscriber(modid = MilkwatersMenagerie.MODID)
public class ModEventBusEvents {
	
	@SubscribeEvent
	public static void onArrowImpact(ProjectileImpactEvent event) {
	    Entity projectile = event.getEntity();
	    
	    if (projectile instanceof AbstractArrow arrow && arrow.getPersistentData().getBoolean("RemoveIFrames")) {

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
	
	@SubscribeEvent
	public static void onPlayerJoin(PlayerEvent.PlayerLoggedInEvent event) {
	    ManaHelper.setMana(event.getEntity(), 12);
	}
	
	@SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent.Pre event) {
        if (!(event.getEntity() instanceof Player player)) return;
        if (player.level().isClientSide) return;

        if (player.tickCount % 20 == 0) {
            ManaHelper.regenMana(player, 1);
        }
    }
}
