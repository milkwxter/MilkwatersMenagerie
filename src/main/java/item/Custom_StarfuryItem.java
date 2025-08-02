package item;

import entity.ModEntities;
import entity.custom.StarfuryStarEntity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;

public class Custom_StarfuryItem extends SwordItem{
	public Custom_StarfuryItem(Tier tier, Item.Properties properties) {
        super(tier, properties);
    }
	
	@Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
		// only spawn a star if the player is ready to swing again
		if (attacker instanceof Player player) {
			if(player.getAttackStrengthScale(0f) < 1.0f) {
				return super.hurtEnemy(stack, target, attacker);
			}
		}
		
		// if the player is ready to swing, spawn a falling star
		if (!target.level().isClientSide) {
			StarfuryStarEntity star = new StarfuryStarEntity(ModEntities.STARFURY_STAR.get(), target.level());
		    star.setPos(target.getX(), target.getY() + 7.0, target.getZ());
		    
		    star.setDeltaMovement(0.0, -0.65, 0.0);
		    
		    star.setOwner(attacker);
		    
		    target.level().addFreshEntity(star);
	    }

        return super.hurtEnemy(stack, target, attacker);
    }
}
