package item;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;

public class Custom_StarfuryItem extends SwordItem{
	public Custom_StarfuryItem(Tier tier, Item.Properties properties) {
        super(tier, properties);
    }
	
	@Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
		if (!target.level().isClientSide) {
	        Arrow arrow = new Arrow(target.level(), target.getX(), target.getY() + 6.0, target.getZ(), new ItemStack(Items.ARROW), attacker.getMainHandItem());
	        arrow.setDeltaMovement(0.0, -1.0, 0.0);
	        arrow.setOwner(attacker);
	        arrow.setBaseDamage(5.0);
	        
	        arrow.setDeltaMovement(0, -1, 0);
	        
	        target.level().addFreshEntity(arrow);
	    }

        return super.hurtEnemy(stack, target, attacker);
    }
}
