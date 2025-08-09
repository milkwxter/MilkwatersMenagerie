package entity.custom;

import java.util.List;

import entity.ModEntities;
import net.minecraft.network.syncher.SynchedEntityData.Builder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class WandOfSparkingProjectileEntity extends Projectile {
	private int ticksAlive = 0;
    private static final int MAX_TICKS = 12;
    
    public WandOfSparkingProjectileEntity(EntityType<? extends WandOfSparkingProjectileEntity> type, Level level) {
        super(type, level);
        this.noPhysics = false;
    }

    public WandOfSparkingProjectileEntity(Level level, LivingEntity shooter) {
        this(ModEntities.SPARK.get(), level);
        this.setPos(shooter.getX(), shooter.getEyeY() - 0.1, shooter.getZ());
        Vec3 look = shooter.getLookAngle().normalize().scale(0.5);
        this.setDeltaMovement(look);
    }
    
    @Override
    public void tick() {
        super.tick();
        ticksAlive++;
        if (ticksAlive > MAX_TICKS) {
            discard();
            return;
        }

        this.move(MoverType.SELF, this.getDeltaMovement());
        
        AABB bounds = this.getBoundingBox().inflate(0.2);
        List<Entity> entities = level().getEntities(this, bounds, e -> e instanceof LivingEntity && e != this.getOwner());
        for (Entity target : entities) {
        	if (target instanceof LivingEntity living && living.getHealth() > 0) {
        		Vec3 knockbackDir = position().subtract(living.position()).normalize();
        		living.hurt(level().damageSources().playerAttack((Player) this.getOwner()), 3.0f);
        		living.knockback(0.4f, knockbackDir.x(), knockbackDir.z());
        		living.igniteForSeconds(4);
                discard();
                return;
        	}
        }
    }

	@Override
	protected void defineSynchedData(Builder builder) {
	}
}
