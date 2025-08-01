package entity.custom;

import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;

public class NoIFrameArrowEntity extends AbstractArrow {
	
	public NoIFrameArrowEntity(EntityType<? extends NoIFrameArrowEntity> entityType, Level level) {
		super(entityType, level);
	}
	
	private Holder<DamageType> arrowDamageType = this.level().registryAccess()
			.registryOrThrow(Registries.DAMAGE_TYPE)
			.getHolderOrThrow(DamageTypes.ARROW);
	
	@Override
	protected void onHitEntity(EntityHitResult result) {
	    super.onHitEntity(result);
	    Entity target = result.getEntity();
	    if (target instanceof LivingEntity living) {
	    	// do the damage in a... way
	        living.hurt(new DamageSource(arrowDamageType, this.getOwner(), this), (float) this.getBaseDamage());
	        
	        // make him vulnerable manually
	        living.invulnerableTime = 0;
	        living.hurtTime = 0;
	    }
	    this.discard();
	}

	@Override
	protected ItemStack getDefaultPickupItem() {
		// TODO Auto-generated method stub
		return null;
	}
}
