package datagen;

import java.util.concurrent.CompletableFuture;

import javax.annotation.Nullable;

import item.ModItems;
import milkwater.milkmenagerie.MilkwatersMenagerie;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class ModItemTagProvider extends ItemTagsProvider {
	public ModItemTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider,
			CompletableFuture<TagLookup<Block>> blockTags, @Nullable ExistingFileHelper existingFileHelper) {
		super(output, lookupProvider, blockTags, MilkwatersMenagerie.MODID, existingFileHelper);
	}
	
	protected void addTags(HolderLookup.Provider provider) {
		tag(ItemTags.SWORDS).add(ModItems.ZOMBIE_ARM_ITEM.get());
		tag(ItemTags.SWORDS).add(ModItems.STARFURY_ITEM.get());
		tag(ItemTags.SWORDS).add(ModItems.NIGHTS_EDGE_ITEM.get());
	}
}
