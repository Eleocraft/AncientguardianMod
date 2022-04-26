package eleocraft.ancientguardian;

import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import eleocraft.ancientguardian.lists.BlockList;
import eleocraft.ancientguardian.lists.ItemList;
import eleocraft.ancientguardian.lists.StructureConfig;
import eleocraft.ancientguardian.lists.StructureList;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.world.World;
import net.minecraft.world.gen.FlatChunkGenerator;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.settings.DimensionStructuresSettings;
import net.minecraft.world.gen.settings.StructureSeparationSettings;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.IForgeRegistry;

@Mod ("ancientguardian")
@EventBusSubscriber(modid = ancientguardian.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ancientguardian
{
	public static ancientguardian instance;
	public static final String MODID = "ancientguardian";
	
	public static final Logger LOGGER = LogManager.getLogger(MODID);
	
	public ancientguardian()
	{
		instance = this;
		
		final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
		
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::clientRegistries);
		MinecraftForge.EVENT_BUS.register(this);
		StructureList.DEFERRED_REGISTRY_STRUCTURE.register(modEventBus);
		
		MinecraftForge.EVENT_BUS.addListener(EventPriority.NORMAL, this::addDimensionalSpacing);
		MinecraftForge.EVENT_BUS.addListener(EventPriority.HIGH, this::biomeModification);
		BlockList.BLOCKS.register(modEventBus);
		ItemList.ITEMS.register(modEventBus);
	}
	
	private void setup(final FMLCommonSetupEvent event)
	{
        event.enqueueWork(() -> {
            StructureList.setupStructures();
            StructureConfig.registerConfiguredStructures();
        });
		LOGGER.info("Setup Funktioniert");
	}
	
	private void clientRegistries(final FMLClientSetupEvent event)
	{
		LOGGER.info("CR Funktioniert");
	}
	
	@SubscribeEvent
	public static void createBlockItems(final RegistryEvent.Register<Item> event) {
		final IForgeRegistry<Item> registry = event.getRegistry();
		
		BlockList.BLOCKS.getEntries().stream().map(RegistryObject::get).forEach(block -> {
			final Item.Properties properties = new Item.Properties().group(ItemGroup.BUILDING_BLOCKS);
			final BlockItem blockItem = new BlockItem(block, properties);
			blockItem.setRegistryName(block.getRegistryName());
			registry.register(blockItem);
		});
		
	}
	
    public void biomeModification(final BiomeLoadingEvent event) {
    	if (event.getName().getPath().equals("nether_wastes") || event.getName().getPath().equals("soul_sand_valley"))
    	{
    		event.getGeneration().getStructures().add(() -> StructureConfig.ANCIENTRUINS);
    	}
    }
    
    @SuppressWarnings("resource")
	public void addDimensionalSpacing(final WorldEvent.Load event) {
        if(event.getWorld() instanceof ServerWorld){
            ServerWorld serverWorld = (ServerWorld)event.getWorld();

            // Prevent spawning our structure in Vanilla's superflat world as
            // people seem to want their superflat worlds free of modded structures.
            // Also that vanilla superflat is really tricky and buggy to work with in my experience.
            if(serverWorld.getChunkProvider().getChunkGenerator() instanceof FlatChunkGenerator &&
                serverWorld.getDimensionKey().equals(World.OVERWORLD)){
                return;
            }

            Map<Structure<?>, StructureSeparationSettings> tempMap = new HashMap<>(serverWorld.getChunkProvider().generator.func_235957_b_().func_236195_a_());
            // putIfAbsent so people can override the spacing with dimension datapacks themselves if they wish to customize spacing more precisely per dimension.
            tempMap.putIfAbsent(StructureList.ANCIENTRUINS.get(), DimensionStructuresSettings.field_236191_b_.get(StructureList.ANCIENTRUINS.get()));
            serverWorld.getChunkProvider().generator.func_235957_b_().field_236193_d_ = tempMap;
        }
   }
}
