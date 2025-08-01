package entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import entity.custom.AngryZombieEntity;
import milkwater.milkmenagerie.MilkwatersMenagerie;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class AngryZombieModel<T extends AngryZombieEntity> extends HierarchicalModel<T> {
	// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
		public static final ModelLayerLocation LAYER_LOCATION =
				new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(MilkwatersMenagerie.MODID, "angryzombie"), "main");
		
		private final ModelPart root;
		private final ModelPart legr;
		private final ModelPart legl;
		private final ModelPart armr;
		private final ModelPart arml;
		private final ModelPart torso;
		private final ModelPart head;

		public AngryZombieModel(ModelPart root) {
			this.root = root;
			this.legr = root.getChild("legr");
			this.legl = root.getChild("legl");
			this.armr = root.getChild("armr");
			this.arml = root.getChild("arml");
			this.torso = root.getChild("torso");
			this.head = root.getChild("head");
		}

		public static LayerDefinition createBodyLayer() {
			MeshDefinition meshdefinition = new MeshDefinition();
			PartDefinition partdefinition = meshdefinition.getRoot();

			PartDefinition legr = partdefinition.addOrReplaceChild("legr", CubeListBuilder.create().texOffs(0, 16).addBox(1.0F, -12.0F, -1.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(-1.0F, 24.0F, 0.0F));

			PartDefinition legl = partdefinition.addOrReplaceChild("legl", CubeListBuilder.create().texOffs(0, 16).addBox(-3.0F, -12.0F, -1.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(-1.0F, 24.0F, 0.0F));

			PartDefinition armr = partdefinition.addOrReplaceChild("armr", CubeListBuilder.create(), PartPose.offset(7.0F, 12.0F, 0.0F));

			PartDefinition cube_r1 = armr.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(40, 16).addBox(-3.0F, -12.0F, -1.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -11.0F, -9.0F, -1.5708F, 0.0F, 0.0F));

			PartDefinition arml = partdefinition.addOrReplaceChild("arml", CubeListBuilder.create(), PartPose.offset(-5.0F, 12.0F, 0.0F));

			PartDefinition cube_r2 = arml.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(40, 16).addBox(-3.0F, -12.0F, -1.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -11.0F, -9.0F, -1.5708F, 0.0F, 0.0F));

			PartDefinition torso = partdefinition.addOrReplaceChild("torso", CubeListBuilder.create().texOffs(16, 16).addBox(-3.0F, -12.0F, -1.0F, 8.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(-1.0F, 12.0F, 0.0F));

			PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-3.0F, -8.0F, -5.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(-1.0F, 0.0F, 2.0F));

			return LayerDefinition.create(meshdefinition, 64, 64);
		}

		@Override
		public void setupAnim(AngryZombieEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
			this.root().getAllParts().forEach(ModelPart::resetPose);
			this.applyHeadRotation(netHeadYaw, headPitch);
			
			this.animateWalk(AngryZombieAnimations.ANIM_ANGRYZOMBIE_WALK, limbSwing, limbSwingAmount, 2f, 1f);
		}
		
		private void applyHeadRotation(float headYaw, float headPitch) {
			headYaw = Mth.clamp(headYaw, -30f, 30f);
			headPitch = Mth.clamp(headPitch, -30f, 30f);
			
			this.head.yRot = headYaw * ((float)Math.PI / 180f);
			this.head.xRot = headPitch * ((float)Math.PI / 180f);
		}

		@Override
		public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, int color) {
			legr.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
			legl.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
			armr.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
			arml.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
			torso.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
			head.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
		}
		
		@Override
		public ModelPart root() {
			return root;
		}
}
