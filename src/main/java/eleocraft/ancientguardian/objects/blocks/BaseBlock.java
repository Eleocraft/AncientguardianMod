package eleocraft.ancientguardian.objects.blocks;

import javax.annotation.Nullable;

import eleocraft.ancientguardian.api.block.state.BlockProperties;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.registries.ForgeRegistries;

public class BaseBlock extends Block
{
	 private static final Vector3d BASE_MIN_CORNER = new Vector3d(0.0, 0.0, 0.0);
	 private static final Vector3d BASE_MAX_CORNER = new Vector3d(16.0, 8.0, 16.0);
	
	 private static final VoxelShape BASE = Block.makeCuboidShape(BASE_MIN_CORNER.getX(), BASE_MIN_CORNER.getY(), BASE_MIN_CORNER.getZ(), BASE_MAX_CORNER.getX(), BASE_MAX_CORNER.getY(), BASE_MAX_CORNER.getZ());
	 
	 public static final BooleanProperty ACTIVE_PORT = BlockProperties.ACTIVE_PORT;
	 
	 public BaseBlock(Properties properties)
	 {
		super(properties);
		this.setDefaultState(this.stateContainer.getBaseState().with(ACTIVE_PORT, Boolean.valueOf(false)));
	 }
	
	 @Override
	 public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) 
	 {
	    return BASE;
	 }
	 
	 @Override
	 public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit)
	 {
		 if (player.inventory.getCurrentItem().getItem() == ForgeRegistries.ITEMS.getValue(new ResourceLocation("ancientguardian:ancient_crystal")) && !state.get(ACTIVE_PORT))
		 {
			 System.out.println("It worked");
			 worldIn.setBlockState(pos, state.with(ACTIVE_PORT, Boolean.valueOf(true)));
			 player.inventory.getCurrentItem().setCount(player.inventory.getCurrentItem().getCount() - 1);
			 return ActionResultType.SUCCESS;
		 }
		 return ActionResultType.FAIL;
	 }
	 
	  @Nullable
	  @Override
	  public BlockState getStateForPlacement(BlockItemUseContext blockItemUseContext) 
	  {
		  BlockState blockState = getDefaultState().with(ACTIVE_PORT, Boolean.valueOf(false));
		  return blockState;
	  }
	  
	  @Override
	  protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) 
	  {
		  builder.add(ACTIVE_PORT);
	  }
}
