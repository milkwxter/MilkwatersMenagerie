package player;

import milkwater.milkmenagerie.MilkwatersMenagerie;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.PacketDistributor;
import network.ManaSyncPayload;

public class ManaHelper {
    private static final String MANA_TAG = MilkwatersMenagerie.MODID + ":mana";
    private static int MAX_MANA = 100;

    public static int getMana(Player player) {
    	return player.getPersistentData().getInt(MANA_TAG);
    }
    
    public static int getMaxMana(Player player) {
        return MAX_MANA;
    }

    public static void setMana(Player player, int value) {
        int clamped = Math.max(0, Math.min(value, MAX_MANA));
        player.getPersistentData().putInt(MANA_TAG, clamped);

        if (player instanceof ServerPlayer serverPlayer) {
            PacketDistributor.sendToPlayer(serverPlayer, new ManaSyncPayload(clamped));
        }
    }

    public static boolean consumeMana(Player player, int amount) {
        int current = getMana(player);
        if (current >= amount) {
            setMana(player, current - amount);
            return true;
        }
        return false;
    }

    public static void regenMana(Player player, int amount) {
        setMana(player, getMana(player) + amount);
    }
    
    public static void syncMana(Player player) {
        if (player instanceof ServerPlayer serverPlayer) {
            PacketDistributor.sendToPlayer(serverPlayer, new ManaSyncPayload(getMana(player)));
        }
    }
}
