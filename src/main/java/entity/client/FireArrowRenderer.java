package entity.client;

import entity.custom.FireArrowEntity;
import milkwater.milkmenagerie.MilkwatersMenagerie;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class FireArrowRenderer extends ArrowRenderer<FireArrowEntity> {

    public FireArrowRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public ResourceLocation getTextureLocation(FireArrowEntity entity) {
        return ResourceLocation.fromNamespaceAndPath(MilkwatersMenagerie.MODID, "textures/entity/fire_arrow_projectile_texture.png");
    }
}
