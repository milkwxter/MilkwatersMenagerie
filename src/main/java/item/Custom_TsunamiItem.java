package item;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class Custom_TsunamiItem extends BowItem {
	// le constructor
	public Custom_TsunamiItem(Properties properties) {
        super(properties);
    }
	
	@Override
	public void releaseUsing(ItemStack stack, Level level, LivingEntity shooter, int timeLeft) {
		// only player can use it (for now)
	    if (!(shooter instanceof Player player)) return;
	    
	    ItemStack weaponStack = shooter.getMainHandItem();
	    if (!(weaponStack.getItem() instanceof BowItem)) {
	        weaponStack = shooter.getOffhandItem();
	    }

	    // find what ammo he is using
	    ItemStack ammo = player.getProjectile(stack);
	    if (ammo.isEmpty()) return;
	    
	    // how long did he charge the bow?
	    int chargeDuration = this.getUseDuration(stack, shooter) - timeLeft;
	    float drawStrength = getPowerForTime(chargeDuration);
	    if (drawStrength < 0.1F) return;
	    
	    // on the server side, do this
	    if (!level.isClientSide) {
	        ArrowItem arrowItem = (ArrowItem) (ammo.getItem() instanceof ArrowItem ? ammo.getItem() : Items.ARROW);

	        for (int i = -2; i <= 2; i++) {
	            Arrow arrow = (Arrow) arrowItem.createArrow(level, ammo, shooter, weaponStack);
	            
	            arrow.getPersistentData().putBoolean("RemoveIFrames", true);
	            
	            arrow.pickup = Arrow.Pickup.CREATIVE_ONLY;
	            arrow.setOwner(player);
	            arrow.setPos(player.getX(), player.getEyeY() - 0.1, player.getZ());

	            Vec3 direction = shooter.getLookAngle().normalize();
	            double spreadOffsetY = i * 0.1;
	            
	            float velocity = drawStrength * 2.0F;
	            
	            arrow.shoot(direction.x, direction.y + spreadOffsetY, direction.z, velocity, 1.0F);

	            level.addFreshEntity(arrow);
	        }
	        
	        stack.hurtAndBreak(1, shooter, weaponStack.getEquipmentSlot());
	        player.awardStat(Stats.ITEM_USED.get(this));
	    }

	    ammo.shrink(1);

	    level.playSound(null, shooter.getX(), shooter.getY(), shooter.getZ(),
	        SoundEvents.ARROW_SHOOT, SoundSource.PLAYERS, 1.0F, 1.0F);
	}

}
