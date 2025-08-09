package event;

import milkwater.milkmenagerie.MilkwatersMenagerie;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RenderGuiLayerEvent;

@EventBusSubscriber(modid = MilkwatersMenagerie.MODID, value = Dist.CLIENT)
public class ManaOverlayEvents {

	@SubscribeEvent
    public static void onRenderOverlay(RenderGuiLayerEvent.Pre event) {
        var mc = Minecraft.getInstance();
        if (mc.player == null || mc.screen != null) return;

        GuiGraphics guiGraphics = event.getGuiGraphics();
        overlay.ManaOverlay.renderGameOverlayEvent(guiGraphics);
    }
    
}
