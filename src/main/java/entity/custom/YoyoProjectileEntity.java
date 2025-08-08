package entity.custom;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import entity.ModEntities;
import item.Base_YoyoItem;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.syncher.SynchedEntityData.Builder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import sound.ModSounds;

public class YoyoProjectileEntity extends Entity {
	// necessary variables
    private Player owner;
    private ItemStack yoyoStack;
    
    // constructor variables
    private Item item;
    private double maxDistance;
    private double yoyoDamage;
    
    // how long has the yoyo been spawned for
    private int ticksAlive = 0;
    
    private boolean bouncingBack = false;
    
    private static final EntityDataAccessor<Optional<UUID>> OWNER_UUID =
    	    SynchedEntityData.defineId(YoyoProjectileEntity.class, EntityDataSerializers.OPTIONAL_UUID);
    
    private static final EntityDataAccessor<ItemStack> YOYO_STACK =
    	    SynchedEntityData.defineId(YoyoProjectileEntity.class, EntityDataSerializers.ITEM_STACK);


    public YoyoProjectileEntity(EntityType<?> type, Level level) {
        super(type, level);
        this.noPhysics = true;
    }

    public YoyoProjectileEntity(Level level, Player owner, ItemStack sourceYoyo) {
    	super(ModEntities.YOYO_PROJECTILE.get(), level);
        this.owner = owner;
        this.yoyoStack = sourceYoyo.copy();
        
        this.entityData.set(OWNER_UUID, Optional.of(owner.getUUID()));
        this.entityData.set(YOYO_STACK, sourceYoyo.copy());
        
        item = yoyoStack.getItem();
        maxDistance = item instanceof Base_YoyoItem yoyo ? yoyo.getMaxYoyoDistance() : 1.0;
        yoyoDamage = item instanceof Base_YoyoItem yoyo ? yoyo.getYoyoDamage() : 1.0;
    }

    @Override
    public void tick() {
        super.tick();
        ticksAlive++;
        
        // get the owner of this entity
        LivingEntity owner = this.getOwner();
        if (owner == null) {
            discard();
            return;
        }
        
        // get some values
        Vec3 ownerEyePos = owner.position().add(0, owner.getEyeHeight(), 0);
        Vec3 targetPos = ownerEyePos.add(owner.getLookAngle().normalize().scale(maxDistance));
        Vec3 currentPos = position();

        // tell the yoyo where to move
        updateMotion(ownerEyePos, targetPos, currentPos);
        move(MoverType.SELF, getDeltaMovement());

        // find nearby enemies
        List<Entity> hitEntities = this.level().getEntities(this, getBoundingBox().inflate(0.5),
            e -> e instanceof LivingEntity && e != this.getOwner());

        // if we are not bouncing and enemies are nearby
        if (!hitEntities.isEmpty() && !bouncingBack) {
            handleHit(hitEntities, currentPos);
        }

        // after 2 seconds, the yoyo entity dies
        if (ticksAlive == 40) {
        	if (level().isClientSide()) {
        		this.spawnItemParticles();
            }
        	level().playSound(null, this.getX(), this.getY(), this.getZ(), ModSounds.GENERIC_IMPACT.get(), SoundSource.NEUTRAL, 1.0F, 1.0F);
        }
        if (ticksAlive > 41) {
            discard();
            return;
        }
    }
    
    private void updateMotion(Vec3 ownerEyePos, Vec3 targetPos, Vec3 currentPos) {
        Vec3 desiredPos = bouncingBack ? ownerEyePos : targetPos;
        Vec3 toTarget = desiredPos.subtract(currentPos);
        double distance = toTarget.length();

        if (bouncingBack && distance <= 1.0) {
            bouncingBack = false;
        }
        
        double baseSpeed = 0.7;
        double speed = Math.max(baseSpeed, distance * 0.6);

        Vec3 currentMotion = getDeltaMovement();
        Vec3 desiredMotion = toTarget.normalize().scale(speed);
        
        double smoothing = 0.25;
        Vec3 newMotion = currentMotion.scale(1.0 - smoothing).add(desiredMotion.scale(smoothing));

        setDeltaMovement(newMotion);
    }
    
    private void handleHit(List<Entity> hitEntities, Vec3 currentPos) {
        // special effects if we hit atleast one guy
        boolean hitSomeone = false;

        // damage each entity near the yoyo
        for (Entity entity : hitEntities) {
            if (entity instanceof LivingEntity living && living.getHealth() > 0) {
            	Vec3 knockbackDir = position().subtract(living.position()).normalize();
            	
                living.hurt(level().damageSources().playerAttack(owner), (float) this.yoyoDamage);
                living.knockback(0.4f, knockbackDir.x(), knockbackDir.z());
                
                hitSomeone = true;
                bouncingBack = true;
            }
        }
        
        // if we hit someone play a sound
        if (hitSomeone) {
        	if (!level().isClientSide) {
                level().playSound(null, this.getX(), this.getY(), this.getZ(), ModSounds.GENERIC_IMPACT.get(), SoundSource.NEUTRAL, 1.0F, 1.0F);
            }
        }
        
        // spawn particles
        if (level().isClientSide() && hitSomeone) {
        	this.spawnItemParticles();
        }
    }
    
    private void spawnItemParticles() {
    	ItemStack stack = this.getSourceYoyo();
        if (stack.isEmpty()) {
        	System.out.println("ERROR! YOYO STACK WAS EMPTY!");
        	return;
        }

        for (int i = 0; i < 8; i++) {
            level().addParticle(new ItemParticleOption(ParticleTypes.ITEM, stack),
            		this.getX(), this.getY() + getBbHeight() / 2, this.getZ(),
                (random.nextDouble() - 0.5) * 0.2,
                (random.nextDouble()) * 0.2,
                (random.nextDouble() - 0.5) * 0.2);
        }
    }
    
    @Override
    protected void readAdditionalSaveData(CompoundTag tag) {}
    @Override
    protected void addAdditionalSaveData(CompoundTag tag) {}

	@Override
	protected void defineSynchedData(Builder builder) {
		builder.define(OWNER_UUID, Optional.empty());
		builder.define(YOYO_STACK, ItemStack.EMPTY);
	}

	public Player getOwner() {
		if (owner != null) return owner;

	    Optional<UUID> uuidOpt = entityData.get(OWNER_UUID);
	    if (uuidOpt.isEmpty()) return null;

	    UUID uuid = uuidOpt.get();
	    Entity resolved = level().isClientSide
	        ? ((ClientLevel) level()).getPlayerByUUID(uuid)
	        : ((ServerLevel) level()).getEntity(uuid);

	    if (resolved instanceof Player player) {
	        this.owner = player;
	        return player;
	    }

	    return null;
	}

	public ItemStack getSourceYoyo() {
		return entityData.get(YOYO_STACK);
	}
}