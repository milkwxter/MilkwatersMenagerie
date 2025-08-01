package entity.custom;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;

public class AngryZombieEntity extends Monster{
	// constructor
	public AngryZombieEntity(EntityType<? extends AngryZombieEntity> entityType, Level level) {
		super(entityType, level);
	}

	@Override
	protected void registerGoals() {
		// make entity swim in water
		this.goalSelector.addGoal(0, new FloatGoal(this));
		
		// make entity stroll around randomly
		this.goalSelector.addGoal(1, new WaterAvoidingRandomStrollGoal(this, 1.0d));
		
		// make entity look around randomly
		this.goalSelector.addGoal(2, new RandomLookAroundGoal(this));
	}
	
	public static AttributeSupplier.Builder createAttributes(){
		return Monster.createMonsterAttributes()
				.add(Attributes.MAX_HEALTH, 30d)
				.add(Attributes.MOVEMENT_SPEED, 0.22d)
				.add(Attributes.FOLLOW_RANGE, 35d);
	}
}
