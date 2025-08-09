package overlay;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import player.ManaHelper;

public class ManaOverlay {
	public static void renderGameOverlayEvent(GuiGraphics poseStack) {
		var mc = Minecraft.getInstance();
        if (mc.player == null || mc.screen != null) return;

        Player player = mc.player;
        int mana = ManaHelper.getMana(player);
        int maxMana = ManaHelper.getMaxMana(player);
        String text = "Mana: " + mana + "/" + maxMana;

        int xOffset = 3;
        int yPlacement = 10;
        int textColor = 0x00BFFF;

        int windowWidth = mc.getWindow().getGuiScaledWidth();
        int fontWidth = mc.font.width(text);
        int xPlacement = windowWidth - fontWidth - xOffset;

        poseStack.drawString(mc.font, Component.literal(text), xPlacement, yPlacement, textColor);
	}
}
