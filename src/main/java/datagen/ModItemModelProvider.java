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
		// items that dont go in your hand
		basicItem(ModItems.FIRE_ARROW_ITEM.get());
		basicItem(ModItems.WOODEN_YOYO_ITEM.get());
		basicItem(ModItems.RALLY_ITEM.get());
		
		// swords and bows and shit
		handheldItem(ModItems.ZOMBIE_ARM_ITEM);
		handheldItem(ModItems.STARFURY_ITEM);
		handheldItem(ModItems.TSUNAMI_ITEM);
		handheldItem(ModItems.NIGHTS_EDGE_ITEM);
		handheldItem(ModItems.BLADED_GLOVE_ITEM);
		handheldItem(ModItems.SPEAR_ITEM);
	}
	
	private ItemModelBuilder handheldItem(DeferredItem<?> item) {
		return withExistingParent(item.getId().getPath(),
				ResourceLocation.parse("item/handheld")).texture("layer0",
						ResourceLocation.fromNamespaceAndPath(MilkwatersMenagerie.MODID, "item/" + item.getId().getPath()));
	}
}
