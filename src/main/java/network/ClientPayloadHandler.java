package network;

import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import player.ManaHelper;

public class ClientPayloadHandler {

    public static void handleManaSync(ManaSyncPayload payload, IPayloadContext context) {
        context.enqueueWork(() -> {
            Minecraft mc = Minecraft.getInstance();
            if (mc.player != null) {
                ManaHelper.setMana(mc.player, payload.mana());
            }
        }).exceptionally(e -> {
            context.disconnect(Component.translatable("mymod.networking.failed", e.getMessage()));
            return null;
        });
    }
}