package util;

import java.util.function.UnaryOperator;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.Rarity;
import net.neoforged.fml.common.asm.enumextension.EnumProxy;

public class ModRarities {
	public static final EnumProxy<Rarity> YELLOW = new EnumProxy<>(
		    Rarity.class,
		    -1,
		    "milkwatersmenagerie:yellow",
		    (UnaryOperator<Style>) style -> style.withColor(ChatFormatting.YELLOW)
		);
}
