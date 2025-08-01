package entity.client;

import entity.custom.NoIFrameArrowEntity;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class NoIFrameArrowRenderer extends ArrowRenderer<NoIFrameArrowEntity> {
	public NoIFrameArrowRenderer(EntityRendererProvider.Context context) {
        super(context);
    }
	
	@Override
    public ResourceLocation getTextureLocation(NoIFrameArrowEntity entity) {
		return ResourceLocation.fromNamespaceAndPath("minecraft", "textures/entity/projectiles/arrow.png");
    }
}
