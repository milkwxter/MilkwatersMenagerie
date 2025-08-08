package entity.client;

import org.joml.Matrix4f;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;

import entity.custom.YoyoProjectileEntity;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
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
        
        // render the yo-yo projectile
        ItemStack yoyoStack = entity.getSourceYoyo();
        if (yoyoStack == null || yoyoStack.isEmpty()) 
    	{
        	poseStack.popPose();
        	return;
    	}
        
        // stop legendary tooltips from messing up
        if (Minecraft.getInstance().screen != null || Minecraft.getInstance().gameRenderer.getMainCamera() == null) {
        	itemRenderer.renderStatic(yoyoStack, ItemDisplayContext.FIXED, packedLight, OverlayTexture.NO_OVERLAY, poseStack, buffer, entity.level(), entity.getId());
        	poseStack.popPose();
            return;
        }

        // apply spinning rotation
        float spin = (entity.tickCount + partialTicks) * 20.0F;
        poseStack.mulPose(Axis.ZP.rotationDegrees(spin));
        
        itemRenderer.renderStatic(yoyoStack, ItemDisplayContext.FIXED, packedLight, OverlayTexture.NO_OVERLAY, poseStack, buffer, entity.level(), entity.getId());
        
        poseStack.popPose();
        
        Entity owner = entity.getOwner();
        if (owner != null) {
            // render a rope between the entity and his owner
        	Vec3 offset = new Vec3(0.2, -0.2, 0.0); // Right shoulder, slightly down
        	renderRopeFromCamera(entity, owner, partialTicks, poseStack, buffer, packedLight, offset);
        }
        
        super.render(entity, yaw, partialTicks, poseStack, buffer, packedLight);
    }

    @Override
    public ResourceLocation getTextureLocation(YoyoProjectileEntity entity) {
        // Not used since we're rendering an item
        return null;
    }
    
    public static void renderRopeFromCamera(Entity target, Entity player, float partialTicks, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, Vec3 cameraOffset) {
		if (target == null) return;
		
		Camera camera = Minecraft.getInstance().gameRenderer.getMainCamera();
        Vec3 cameraPos = camera.getPosition();

        double px = Mth.lerp(partialTicks, player.xOld, player.getX());
        double py = Mth.lerp(partialTicks, player.yOld, player.getY()) + player.getEyeHeight();
        double pz = Mth.lerp(partialTicks, player.zOld, player.getZ());

        Vec3 playerPos = new Vec3(px, py, pz);

        // Apply offset relative to player’s facing direction
        Vec3 facing = player.getLookAngle().normalize();
        Vec3 start = playerPos
            .add(facing.cross(new Vec3(0, 1, 0)).scale(cameraOffset.x))
            .add(new Vec3(0, cameraOffset.y, 0))
            .add(facing.scale(cameraOffset.z));

        // Interpolate target position
        double tx = Mth.lerp(partialTicks, target.xOld, target.getX());
        double ty = Mth.lerp(partialTicks, target.yOld, target.getY()) + target.getBbHeight() / 2.0;
        double tz = Mth.lerp(partialTicks, target.zOld, target.getZ());
        Vec3 end = new Vec3(tx, ty, tz);

        // Create a fresh PoseStack for world-space rendering
        PoseStack poseStack2 = new PoseStack();
        poseStack2.translate(-cameraPos.x, -cameraPos.y, -cameraPos.z);

        Matrix4f matrix = poseStack2.last().pose();
        VertexConsumer consumer = bufferSource.getBuffer(RenderType.lines());
		
		consumer.addVertex(matrix, (float) start.x, (float) start.y, (float) start.z)
		.setColor(255, 255, 255, 255)
		.setLight(packedLight)
		.setNormal(0f, 1f, 0f);
		
		consumer.addVertex(matrix, (float) end.x, (float) end.y, (float) end.z)
		.setColor(255, 255, 255, 255)
		.setLight(packedLight)
		.setNormal(0f, 1f, 0f);
	}
}

