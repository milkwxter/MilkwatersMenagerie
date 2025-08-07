package entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;

import entity.custom.YoyoProjectileEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;

public class YoyoProjectileRenderer extends EntityRenderer<YoyoProjectileEntity> {
    private final ItemRenderer itemRenderer;

    public YoyoProjectileRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.itemRenderer = context.getItemRenderer();
    }

    @Override
    public void render(YoyoProjectileEntity entity, float yaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        poseStack.pushPose();

        // apply spinning rotation
        float spin = (entity.tickCount + partialTicks) * 20.0F;
        poseStack.mulPose(Axis.ZP.rotationDegrees(spin));

        // render the yo-yo projectile
        ItemStack yoyoStack = entity.getSourceYoyo();
        if (yoyoStack == null || yoyoStack.isEmpty()) 
    	{
        	poseStack.popPose();
        	return;
    	}
        itemRenderer.renderStatic(yoyoStack, ItemDisplayContext.FIXED, packedLight, OverlayTexture.NO_OVERLAY, poseStack, buffer, entity.level(), entity.getId());
        
        poseStack.popPose();
        
        Entity owner = entity.getOwner();
        if (owner != null) {
            renderLineBetween(entity, owner, poseStack, buffer, packedLight);
        }
        
        super.render(entity, yaw, partialTicks, poseStack, buffer, packedLight);
    }
    
    private void renderLineBetween(Entity from, Entity to, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
		// get the current pose and camera position
		PoseStack.Pose pose = poseStack.last();
	    Vec3 camPos = Minecraft.getInstance().gameRenderer.getMainCamera().getPosition();
		
		// get all coordinates
		Vec3 fromPos = from.position().subtract(camPos);
		Vec3 toPos   = to.position().subtract(camPos);
		float x1 = (float)(fromPos.x);
		float y1 = (float)(fromPos.y);
		float z1 = (float)(fromPos.z);
		float x2 = (float)(toPos.x);
		float y2 = (float)(toPos.y + 0.25f);
		float z2 = (float)(toPos.z);
		
		// grab a line‐drawing consumer
		VertexConsumer consumer = bufferSource.getBuffer(RenderType.lines());
		
		// common parameters
		int color    = 0xFFFFFFFF;
		int overlay  = OverlayTexture.NO_OVERLAY;
		
		// first vertex
		consumer
		.addVertex(pose, x1, y1, z1)
		.setColor(color)
		.setUv(0f, 0f)
		.setOverlay(overlay)
		.setLight(packedLight)
		.setNormal(pose, 0f, 1f, 0f);
		
		// second vertex
		consumer
		.addVertex(pose, x2, y2, z2)
		.setColor(color)
		.setUv(1f, 1f)
		.setOverlay(overlay)
		.setLight(packedLight)
		.setNormal(pose, 0f, 1f, 0f);
	}


    @Override
    public ResourceLocation getTextureLocation(YoyoProjectileEntity entity) {
        // Not used since we're rendering an item
        return null;
    }
}

