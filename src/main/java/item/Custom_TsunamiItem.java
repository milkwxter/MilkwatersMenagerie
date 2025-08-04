package item;

import java.util.List;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

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
	    	List<ItemStack> list = draw(stack, ammo, player);
	    	ServerLevel serverLevel = (ServerLevel) level;
	    	
	        for (int i = -2; i <= 2; i++) {
	        	float verticalOffset = i * 0.5F;
	        	this.shootVertical(serverLevel, player, player.getUsedItemHand(), stack, list, drawStrength * 2.0F, 1.0F, drawStrength == 1.0F, verticalOffset);
	        }
	        
	        player.awardStat(Stats.ITEM_USED.get(this));
	    }

	    ammo.shrink(1);

	    level.playSound(null, shooter.getX(), shooter.getY(), shooter.getZ(),
	        SoundEvents.ARROW_SHOOT, SoundSource.PLAYERS, 1.0F, 1.0F);
	}
	
	public void shootVertical(ServerLevel level, LivingEntity shooter, InteractionHand hand, ItemStack weapon, List<ItemStack> projectileItems, float velocity, float inaccuracy, boolean isCrit, float offsetY) {
		for (int i = 0; i < projectileItems.size(); i++) {
	        ItemStack itemstack = projectileItems.get(i);
	        if (!itemstack.isEmpty()) {
	            Projectile projectile = this.createProjectile(level, shooter, weapon, itemstack, isCrit);
	            
	            projectile.setPos(shooter.getX(), shooter.getEyeY() + offsetY, shooter.getZ());
	            projectile.getPersistentData().putBoolean("RemoveIFrames", true);
	            this.shootProjectile(shooter, projectile, i, velocity, inaccuracy, offsetY, null);
	            
	            level.addFreshEntity(projectile);
	            weapon.hurtAndBreak(this.getDurabilityUse(itemstack), shooter, LivingEntity.getSlotForHand(hand));
	            if (weapon.isEmpty()) {
	                break;
	            }
	        }
	    }
	}
	
}
