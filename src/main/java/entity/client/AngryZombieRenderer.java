package entity.client;

import com.mojang.blaze3d.vertex.PoseStack;

import entity.custom.AngryZombieEntity;
import milkwater.milkmenagerie.MilkwatersMenagerie;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class AngryZombieRenderer extends MobRenderer<AngryZombieEntity, AngryZombieModel<AngryZombieEntity>>{
	public AngryZombieRenderer(EntityRendererProvider.Context context) {
		super(context, new AngryZombieModel<>(context.bakeLayer(AngryZombieModel.LAYER_LOCATION)), 0.5f);
	}

	@Override
	public ResourceLocation getTextureLocation(AngryZombieEntity entity) {
		return ResourceLocation.fromNamespaceAndPath(MilkwatersMenagerie.MODID, "textures/entity/angryzombie/angryzombie.png");
	}
	
	public void render(AngryZombieEntity entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
		super.render(entity, entityYaw, partialTicks, poseStack, buffer, packedLight);
	}
}
