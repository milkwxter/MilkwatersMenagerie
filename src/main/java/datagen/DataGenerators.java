package datagen;

import java.util.concurrent.CompletableFuture;

import milkwater.milkmenagerie.MilkwatersMenagerie;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;

@EventBusSubscriber(modid = MilkwatersMenagerie.MODID)
public class DataGenerators {
    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        PackOutput output = event.getGenerator().getPackOutput();
        CompletableFuture<HolderLookup.Provider> lookup = event.getLookupProvider();
        ExistingFileHelper helper = event.getExistingFileHelper();
        
        ModBlockTagProvider blockTags = new ModBlockTagProvider(output, lookup, helper);
        event.getGenerator().addProvider(event.includeServer(), blockTags);
        
        event.getGenerator().addProvider(event.includeServer(),
            new ModItemTagProvider(output, lookup, blockTags.contentsGetter(), helper));
    }
}