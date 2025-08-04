package item;

import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.common.SimpleTier;

public class ModToolTiers {
	public static final Tier ZOMBIE_TIER = new SimpleTier(BlockTags.INCORRECT_FOR_STONE_TOOL,
			64, 3f, 0f, 0, () -> Ingredient.of(Items.ROTTEN_FLESH));
	
	public static final Tier PREHARDMODE_TIER = new SimpleTier(BlockTags.INCORRECT_FOR_DIAMOND_TOOL,
			1024, 3f, 0f, 10, null);
	
	public static final Tier HARDMODE_TIER = new SimpleTier(BlockTags.INCORRECT_FOR_NETHERITE_TOOL,
			2048, 3f, 0f, 10, () -> Ingredient.of(Items.DIAMOND));
}
