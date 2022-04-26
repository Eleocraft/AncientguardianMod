package eleocraft.ancientguardian.lists;

import eleocraft.ancientguardian.ancientguardian;
import eleocraft.ancientguardian.objects.blocks.BaseBlock;
import eleocraft.ancientguardian.objects.blocks.CornerBlock;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.StairsBlock;
import net.minecraft.block.material.Material;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class BlockList
{
	public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, ancientguardian.MODID);
	
	public static final RegistryObject<Block> ANCIENT_BRICKS_BLOCK = BLOCKS.register("ancient_bricks_block", () -> new Block(Block.Properties.create(Material.ROCK).sound(SoundType.STONE).hardnessAndResistance(4.0f, 15.0f)));
	public static final RegistryObject<Block> ANCIENT_PILLAR_BLOCK = BLOCKS.register("ancient_pillar_block", () -> new Block(Block.Properties.create(Material.ROCK).sound(SoundType.STONE).hardnessAndResistance(4.0f, 15.0f)));
	public static final RegistryObject<Block> ANCIENT_STAIRS = BLOCKS.register("ancient_stairs", () -> new StairsBlock(() -> ANCIENT_BRICKS_BLOCK.get().getDefaultState(), Block.Properties.from(ANCIENT_BRICKS_BLOCK.get())));
	public static final RegistryObject<Block> ANCIENT_CORNER_BLOCK = BLOCKS.register("ancient_corner_block", () -> new CornerBlock(Block.Properties.create(Material.ROCK).sound(SoundType.STONE).hardnessAndResistance(4.0f, 15.0f)));
	public static final RegistryObject<Block> ANCIENT_BASE = BLOCKS.register("ancient_base", () -> new BaseBlock(Block.Properties.create(Material.ROCK).sound(SoundType.STONE).hardnessAndResistance(-1.0f, 100.0f)));
}
