package util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.UnaryOperator;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.Rarity;
import net.neoforged.fml.common.asm.enumextension.EnumProxy;

public class ModRarities {
	// this is where I store all the rarities
	public static final List<EnumProxy<Rarity>> ALL_CUSTOM_RARITIES = new ArrayList<>();
	
	// custom rarities
	public static final EnumProxy<Rarity> BLUE = new EnumProxy<>(
	        Rarity.class, -1, "milkwatersmenagerie:blue",
	        (UnaryOperator<Style>) style -> style.withColor(ChatFormatting.BLUE)
	    );
	
	public static final EnumProxy<Rarity> GREEN = new EnumProxy<>(
	        Rarity.class, -1, "milkwatersmenagerie:green",
	        (UnaryOperator<Style>) style -> style.withColor(ChatFormatting.DARK_GREEN)
	    );
	
	public static final EnumProxy<Rarity> ORANGE = new EnumProxy<>(
	        Rarity.class, -1, "milkwatersmenagerie:orange",
	        (UnaryOperator<Style>) style -> style.withColor(ChatFormatting.GOLD)
	    );

	public static final EnumProxy<Rarity> LIGHT_RED = new EnumProxy<>(
	        Rarity.class, -1, "milkwatersmenagerie:light_red",
	        (UnaryOperator<Style>) style -> style.withColor(ChatFormatting.RED)
	    );

	public static final EnumProxy<Rarity> PINK = new EnumProxy<>(
	        Rarity.class, -1, "milkwatersmenagerie:pink",
	        (UnaryOperator<Style>) style -> style.withColor(ChatFormatting.LIGHT_PURPLE)
	    );
	
	public static final EnumProxy<Rarity> LIGHT_PURPLE = new EnumProxy<>(
	        Rarity.class, -1, "milkwatersmenagerie:light_purple",
	        (UnaryOperator<Style>) style -> style.withColor(ChatFormatting.DARK_PURPLE)
	    );

	public static final EnumProxy<Rarity> LIME = new EnumProxy<>(
	        Rarity.class, -1, "milkwatersmenagerie:lime",
	        (UnaryOperator<Style>) style -> style.withColor(ChatFormatting.GREEN)
	    );

	public static final EnumProxy<Rarity> YELLOW = new EnumProxy<>(
	        Rarity.class, -1, "milkwatersmenagerie:yellow",
	        (UnaryOperator<Style>) style -> style.withColor(ChatFormatting.YELLOW)
	    );

	public static final EnumProxy<Rarity> CYAN = new EnumProxy<>(
	        Rarity.class, -1, "milkwatersmenagerie:cyan",
	        (UnaryOperator<Style>) style -> style.withColor(ChatFormatting.AQUA)
	    );

	public static final EnumProxy<Rarity> RED = new EnumProxy<>(
	        Rarity.class, -1, "milkwatersmenagerie:red",
	        (UnaryOperator<Style>) style -> style.withColor(ChatFormatting.DARK_RED)
	    );

	static {
	        Collections.addAll(
	            ALL_CUSTOM_RARITIES,
	            BLUE, GREEN, ORANGE, LIGHT_RED, PINK, LIGHT_PURPLE, LIME, YELLOW, CYAN, RED
	        );
	    }
}