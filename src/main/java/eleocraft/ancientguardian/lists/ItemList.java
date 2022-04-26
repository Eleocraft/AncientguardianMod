package eleocraft.ancientguardian.lists;

import eleocraft.ancientguardian.ancientguardian;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ItemList
{
	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, ancientguardian.MODID);
	
	public static final RegistryObject<Item> ANCIENT_CRISTAL = ITEMS.register("ancient_crystal", () -> new Item(new Item.Properties().group(ItemGroup.MATERIALS)));
}
