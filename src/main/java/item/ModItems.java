package item;

import milkwater.milkmenagerie.MilkwatersMenagerie;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tiers;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import util.ModRarities;

public class ModItems {
	public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(MilkwatersMenagerie.MODID);
	
	// Swords
    public static final DeferredItem<SwordItem> ZOMBIE_ARM_ITEM = ITEMS.register("zombie_arm",
    		() -> new SwordItem(ModToolTiers.ZOMBIE_TIER, new SwordItem.Properties()
    				.attributes(SwordItem.createAttributes(ModToolTiers.ZOMBIE_TIER, 5, -3f))));
    
    public static final DeferredItem<SwordItem> BLADED_GLOVE_ITEM = ITEMS.register("bladed_glove",
    		() -> new SwordItem(Tiers.IRON, new SwordItem.Properties()
    				.attributes(SwordItem.createAttributes(Tiers.IRON, 2, -1.8f))
    	    		.rarity(ModRarities.GREEN.getValue())
    	    		));
    
    public static final DeferredItem<SwordItem> SPEAR_ITEM = ITEMS.register("spear",
    		() -> new SwordItem(Tiers.IRON, new SwordItem.Properties()
    				.attributes(SwordItem.createAttributes(Tiers.IRON, 3, -2.5f))
    	    		));
    
    public static final DeferredItem<SwordItem> NIGHTS_EDGE_ITEM = ITEMS.register("nights_edge",
    		() -> new SwordItem(ModToolTiers.HARDMODE_TIER, new SwordItem.Properties()
    				.attributes(SwordItem.createAttributes(ModToolTiers.HARDMODE_TIER, 10, -2.6f))
    	    		.rarity(ModRarities.ORANGE.getValue())
    	    		));
    
    public static final DeferredItem<Custom_StarfuryItem> STARFURY_ITEM = ITEMS.register("starfury",
    		() -> new Custom_StarfuryItem(ModToolTiers.PREHARDMODE_TIER, new Custom_StarfuryItem.Properties()
    				.attributes(Custom_StarfuryItem.createAttributes(ModToolTiers.PREHARDMODE_TIER, 6, -2.4f))
    	    		.rarity(ModRarities.GREEN.getValue())
    	    		));
    
    // Arrows
    public static final DeferredItem<Item> FIRE_ARROW_ITEM = ITEMS.register("fire_arrow",
    		() -> new Custom_FireArrowItem(new Item.Properties()));
    
    // Yoyos
    public static final DeferredItem<Item> WOODEN_YOYO_ITEM = ITEMS.register("wooden_yoyo",
    		() -> new Custom_WoodenYoyoItem(new Item.Properties()
    				.durability(59)
    				));
    
    public static final DeferredItem<Item> RALLY_ITEM = ITEMS.register("rally",
    		() -> new Custom_RallyItem(new Item.Properties()
    				.durability(251)
    	    		.rarity(ModRarities.BLUE.getValue())
    				));
    
    // Bows
    public static final DeferredItem<Item> TSUNAMI_ITEM = ITEMS.register("tsunami",
    	    () -> new Custom_TsunamiItem(new Item.Properties()
    	    		.durability(2048)
    	    		.rarity(ModRarities.YELLOW.getValue())
    	    		));
    
    // Magic stuff
    public static final DeferredItem<Item> WAND_OF_SPARKING_ITEM = ITEMS.register("wand_of_sparking",
    	    () -> new Custom_WandOfSparkingItem(new Item.Properties()
    	    		.durability(59)
    	    		.rarity(ModRarities.BLUE.getValue())
    	    		));
}
