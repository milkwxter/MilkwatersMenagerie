package entity.custom;

import java.util.List;

import net.minecraft.network.syncher.SynchedEntityData.Builder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import particle.ModParticles;

public class StarfuryStarEntity extends ThrowableProjectile {
	// my vars
	private int explosionRadius = 2;
	
	// TODO: Have sex with men
	
	// functions
	public StarfuryStarEntity(EntityType<? extends ThrowableProjectile> type, Level level) {
        super(type, level);
    }
	
	@Override
    public void tick() {
        super.tick();
        
        this.setDeltaMovement(this.getDeltaMovement().add(0, -0.05, 0));
        
        if (this.onGround() || this.level().getEntities(this, this.getBoundingBox()).stream().anyMatch(e -> e != this.getOwner())) {
            explode();
            this.discard();
        }
    }

    private void explode() {
    	// no clients allowed
    	if (level().isClientSide) return;
    	
    	// find enemies within range of explosion
        List<LivingEntity> affectedEntities = level().getEntitiesOfClass(LivingEntity.class, 
            new AABB(
                this.getX() - explosionRadius, this.getY() - explosionRadius, this.getZ() - explosionRadius,
                this.getX() + explosionRadius, this.getY() + explosionRadius, this.getZ() + explosionRadius
            )
        );
        
        // do something to each one
        for (LivingEntity entity : affectedEntities) {
            if (entity.isAlive() && entity != this.getOwner()) {
                entity.invulnerableTime = 0;
                entity.hurtTime = 0;
                entity.hurt(level().damageSources().generic(), 5.0f);
            }
        }
        
        explodeEffects();
    }
    
    private void explodeEffects() {
    	// no clients allowed
    	if (level().isClientSide) return;
    	
    	ServerLevel serverLevel = (ServerLevel) level();
    	
        serverLevel.sendParticles(ModParticles.STAR.get(),
            this.getX(), this.getY(), this.getZ(),
            12,
            0.4, 0.4, 0.4,
            0.15
        );
        
        serverLevel.playSound(null, this.blockPosition(), SoundEvents.FIREWORK_ROCKET_BLAST, SoundSource.NEUTRAL, 1.0F, 1.2F);
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        super.onHitEntity(result);
        explode();
        this.discard();
    }

    @Override
    protected void onHit(HitResult result) {
        super.onHit(result);
        explode();
        this.discard();
    }

	@Override
	protected void defineSynchedData(Builder builder) {
		// nothing for now
	}
}
