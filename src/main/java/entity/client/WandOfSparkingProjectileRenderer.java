package entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import entity.custom.WandOfSparkingProjectileEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;

public class WandOfSparkingProjectileRenderer extends EntityRenderer<WandOfSparkingProjectileEntity> {

    public WandOfSparkingProjectileRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public ResourceLocation getTextureLocation(WandOfSparkingProjectileEntity entity) {
        return null;
    }
    
    @Override
    public void render(WandOfSparkingProjectileEntity entity, float yaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
    	entity.level().addParticle(ParticleTypes.FLAME,
                entity.getX(), entity.getY(), entity.getZ(),
                0.0, 0.0, 0.0);
    	
    	super.render(entity, yaw, partialTicks, poseStack, buffer, packedLight);
    }
}
