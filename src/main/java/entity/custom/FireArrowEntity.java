package entity.custom;

import javax.annotation.Nullable;

import entity.ModEntities;
import item.ModItems;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;

public class FireArrowEntity extends AbstractArrow {

    public FireArrowEntity(EntityType<? extends FireArrowEntity> type, Level level) {
        super(type, level);
    }
    
    public FireArrowEntity(Level level, LivingEntity owner, ItemStack pickupItemStack, @Nullable ItemStack firedFromWeapon) {
        super(ModEntities.FIRE_ARROW.get(), owner, level, pickupItemStack, firedFromWeapon);
    }
    
    public FireArrowEntity(Level level, double x, double y, double z, ItemStack pickupItemStack, @Nullable ItemStack firedFromWeapon) {
        super(ModEntities.FIRE_ARROW.get(), x, y, z, level, pickupItemStack, firedFromWeapon);
    }

	@Override
    protected void onHitEntity(EntityHitResult result) {
        super.onHitEntity(result);
        Entity target = result.getEntity();
        if (target.fireImmune()) return;
        if (target instanceof LivingEntity living) {
            living.setRemainingFireTicks(80);
        }
    }

    @Override
    public void tick() {
        super.tick();
        if (this.level().isClientSide()) {
            level().addParticle(ParticleTypes.FLAME,
                this.getX(), this.getY(), this.getZ(),
                0.0D, 0.0D, 0.0D);
        }
    }

    @Override
    protected ItemStack getDefaultPickupItem() {
        return new ItemStack(ModItems.FIRE_ARROW_ITEM.get()); // Replace with your item registry
    }
}

