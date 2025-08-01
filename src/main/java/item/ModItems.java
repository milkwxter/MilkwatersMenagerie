package item;

import entity.ModEntities;
import milkwater.milkmenagerie.MilkwatersMenagerie;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SwordItem;
import net.neoforged.neoforge.common.DeferredSpawnEggItem;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItems {
	public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(MilkwatersMenagerie.MODID);
	
	// My custom easy items
    public static final DeferredItem<BlockItem> ROTTENFLESH_BLOCK_ITEM = ITEMS.registerSimpleBlockItem("rotten_flesh_block",
    		MilkwatersMenagerie.ROTTENFLESH_BLOCK);
    
    public static final DeferredItem<Item> ANGRY_ZOMBIE_SPAWN_EGG_ITEM = ITEMS.register("angry_zombie_spawn_egg",
    		() -> new DeferredSpawnEggItem(ModEntities.ANGRYZOMBIE, 0x3E692D, 0xE61515,
    				new Item.Properties()));
    
    // My custom hard items
    public static final DeferredItem<SwordItem> ZOMBIE_ARM_ITEM = ITEMS.register("zombie_arm",
    		() -> new SwordItem(ModToolTiers.NATURAL_TIER, new SwordItem.Properties()
    				.attributes(SwordItem.createAttributes(ModToolTiers.NATURAL_TIER, 5, -3f))));
    
    public static final DeferredItem<Custom_StarfuryItem> STARFURY_ITEM = ITEMS.register("starfury",
    		() -> new Custom_StarfuryItem(ModToolTiers.NATURAL_TIER, new Custom_StarfuryItem.Properties()
    				.attributes(Custom_StarfuryItem.createAttributes(ModToolTiers.NATURAL_TIER, 6, -1f))));
    
    // My custom impossible items
    public static final DeferredItem<Item> TSUNAMI_ITEM = ITEMS.register("tsunami",
    	    () -> new Custom_TsunamiItem(new Item.Properties()
    	        .durability(2560)));

}
