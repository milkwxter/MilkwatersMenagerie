package datagen;

import item.ModItems;
import milkwater.milkmenagerie.MilkwatersMenagerie;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.client.model.generators.ItemModelBuilder;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredItem;

public class ModItemModelProvider extends ItemModelProvider {
	public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
		super(output, MilkwatersMenagerie.MODID, existingFileHelper);
	}
	
	protected void registerModels() {
		handheldItem(ModItems.ZOMBIE_ARM_ITEM);
		handheldItem(ModItems.STARFURY_ITEM);
		
		handheldItem(ModItems.TSUNAMI_ITEM);
		
		withExistingParent(ModItems.ANGRY_ZOMBIE_SPAWN_EGG_ITEM.getId().getPath(), mcLoc("item/template_spawn_egg"));
	}
	
	private ItemModelBuilder handheldItem(DeferredItem<?> item) {
		return withExistingParent(item.getId().getPath(),
				ResourceLocation.parse("item/handheld")).texture("layer0",
						ResourceLocation.fromNamespaceAndPath(MilkwatersMenagerie.MODID, "item/" + item.getId().getPath()));
	}
}
