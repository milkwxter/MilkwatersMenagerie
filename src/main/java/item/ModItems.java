package item;

import milkwater.milkmenagerie.MilkwatersMenagerie;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SwordItem;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItems {
	public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(MilkwatersMenagerie.MODID);
	
	// My custom easy items
    public static final DeferredItem<BlockItem> ROTTENFLESH_BLOCK_ITEM = ITEMS.registerSimpleBlockItem("rotten_flesh_block",
    		MilkwatersMenagerie.ROTTENFLESH_BLOCK);
    
    // My custom hard items
    public static final DeferredItem<SwordItem> ZOMBIE_ARM_ITEM = ITEMS.register("zombie_arm",
    		() -> new SwordItem(ModToolTiers.ZOMBIE_TIER, new SwordItem.Properties()
    				.attributes(SwordItem.createAttributes(ModToolTiers.ZOMBIE_TIER, 5, -3f))));
    
    public static final DeferredItem<SwordItem> NIGHTS_EDGE_ITEM = ITEMS.register("nights_edge",
    		() -> new SwordItem(ModToolTiers.HARDMODE_TIER, new SwordItem.Properties()
    				.attributes(SwordItem.createAttributes(ModToolTiers.HARDMODE_TIER, 10, -2.6f))));
    
    // My custom impossible items
    public static final DeferredItem<Custom_StarfuryItem> STARFURY_ITEM = ITEMS.register("starfury",
    		() -> new Custom_StarfuryItem(ModToolTiers.PREHARDMODE_TIER, new Custom_StarfuryItem.Properties()
    				.attributes(Custom_StarfuryItem.createAttributes(ModToolTiers.PREHARDMODE_TIER, 6, -2.4f))));
    
    public static final DeferredItem<Item> TSUNAMI_ITEM = ITEMS.register("tsunami",
    	    () -> new Custom_TsunamiItem(new Item.Properties()
    	        .durability(2560)));

}
