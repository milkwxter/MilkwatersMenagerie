package datagen;

import java.util.concurrent.CompletableFuture;

import javax.annotation.Nullable;

import milkwater.milkmenagerie.MilkwatersMenagerie;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class ModBlockTagProvider extends BlockTagsProvider {
	public ModBlockTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
		super(output, lookupProvider, MilkwatersMenagerie.MODID, existingFileHelper);
	}

	@Override
	protected void addTags(Provider provider) {
		// TODO Auto-generated method stub
	}

}
