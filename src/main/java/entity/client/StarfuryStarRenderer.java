package entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import entity.custom.StarfuryStarEntity;
import milkwater.milkmenagerie.MilkwatersMenagerie;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FastColor;

public class StarfuryStarRenderer extends EntityRenderer<StarfuryStarEntity> {
    public StarfuryStarRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public ResourceLocation getTextureLocation(StarfuryStarEntity entity) {
        return ResourceLocation.fromNamespaceAndPath(MilkwatersMenagerie.MODID, "textures/entity/starfury_projectile_texture.png");
    }
    
    @Override
    public void render(StarfuryStarEntity entity, float yaw, float partialTicks, PoseStack poseStack,
                       MultiBufferSource bufferSource, int packedLight) {
        poseStack.pushPose();
        poseStack.translate(0, 0.25, 0); // adjust vertical offset
        poseStack.mulPose(this.entityRenderDispatcher.cameraOrientation());

        VertexConsumer consumer = bufferSource.getBuffer(RenderType.entityCutoutNoCull(getTextureLocation(entity)));
        PoseStack.Pose pose = poseStack.last();

        float size = 0.5F;
        int color = FastColor.ARGB32.color(255, 255, 255, 255);
        int fullBright = LightTexture.pack(15, 15);

        // Lower left
        consumer.addVertex(pose.pose(), -size, -size, 0)
                .setColor(color)
                .setUv(0, 1)
                .setOverlay(OverlayTexture.NO_OVERLAY)
                .setLight(fullBright)
                .setNormal(0, 1, 0);

        // Upper left
        consumer.addVertex(pose.pose(), -size, size, 0)
                .setColor(color)
                .setUv(0, 0)
                .setOverlay(OverlayTexture.NO_OVERLAY)
                .setLight(fullBright)
                .setNormal(0, 1, 0);

        // Upper right
        consumer.addVertex(pose.pose(), size, size, 0)
                .setColor(color)
                .setUv(1, 0)
                .setOverlay(OverlayTexture.NO_OVERLAY)
                .setLight(fullBright)
                .setNormal(0, 1, 0);

        // Lower right
        consumer.addVertex(pose.pose(), size, -size, 0)
                .setColor(color)
                .setUv(1, 1)
                .setOverlay(OverlayTexture.NO_OVERLAY)
                .setLight(fullBright)
                .setNormal(0, 1, 0);

        poseStack.popPose();
        super.render(entity, yaw, partialTicks, poseStack, bufferSource, packedLight);
    }
}
