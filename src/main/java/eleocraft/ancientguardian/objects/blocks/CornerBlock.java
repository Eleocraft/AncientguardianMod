package eleocraft.ancientguardian.objects.blocks;

import java.util.Map;

import javax.annotation.Nullable;

import com.google.common.collect.ImmutableMap;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.Direction;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.IBlockReader;

public class CornerBlock extends Block
{
	 private static final Vector3d BASE_MIN_CORNER_N = new Vector3d(0.0, 8.0, 0.0);
	 private static final Vector3d BASE_MAX_CORNER_N = new Vector3d(16.0, 16.0, 8.0);
	
	 private static final VoxelShape BASE_N = Block.makeCuboidShape(BASE_MIN_CORNER_N.getX(), BASE_MIN_CORNER_N.getY(), BASE_MIN_CORNER_N.getZ(), BASE_MAX_CORNER_N.getX(), BASE_MAX_CORNER_N.getY(), BASE_MAX_CORNER_N.getZ());
	 
	 private static final Vector3d BASE_MIN_CORNER_E = new Vector3d(8.0, 8.0, 0.0);
	 private static final Vector3d BASE_MAX_CORNER_E = new Vector3d(16.0, 16.0, 16.0);
	 
	 private static final VoxelShape BASE_E = Block.makeCuboidShape(BASE_MIN_CORNER_E.getX(), BASE_MIN_CORNER_E.getY(), BASE_MIN_CORNER_E.getZ(), BASE_MAX_CORNER_E.getX(), BASE_MAX_CORNER_E.getY(), BASE_MAX_CORNER_E.getZ());
	 
	 private static final Vector3d BASE_MIN_CORNER_S = new Vector3d(0.0, 8.0, 8.0);
	 private static final Vector3d BASE_MAX_CORNER_S = new Vector3d(16.0, 16.0, 16.0);
	 
	 private static final VoxelShape BASE_S = Block.makeCuboidShape(BASE_MIN_CORNER_S.getX(), BASE_MIN_CORNER_S.getY(), BASE_MIN_CORNER_S.getZ(), BASE_MAX_CORNER_S.getX(), BASE_MAX_CORNER_S.getY(), BASE_MAX_CORNER_S.getZ());
	 
	 private static final Vector3d BASE_MIN_CORNER_W = new Vector3d(0.0, 8.0, 0.0);
	 private static final Vector3d BASE_MAX_CORNER_W = new Vector3d(8.0, 16.0, 16.0);
	 
	 private static final VoxelShape BASE_W = Block.makeCuboidShape(BASE_MIN_CORNER_W.getX(), BASE_MIN_CORNER_W.getY(), BASE_MIN_CORNER_W.getZ(), BASE_MAX_CORNER_W.getX(), BASE_MAX_CORNER_W.getY(), BASE_MAX_CORNER_W.getZ());
	 
	  private static final Map<Direction, VoxelShape> POST_SHAPES =
		        ImmutableMap.of(Direction.NORTH,BASE_N,   Direction.EAST,BASE_E,   Direction.SOUTH,BASE_S,   Direction.WEST,BASE_W);
	  
	 public CornerBlock(Properties properties)
	 {
		 super(properties);
	 }
	
	  @Override
	  public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) 
	  {
		  Direction direction = state.get(FACING);
		  VoxelShape voxelShape = POST_SHAPES.get(direction);
		  return voxelShape;
	  }
	  public BlockState rotate(BlockState state, Rotation rot) {
	      return state.with(FACING, rot.rotate(state.get(FACING)));
	   }
	   
	  @Nullable
	  @Override
	  public BlockState getStateForPlacement(BlockItemUseContext blockItemUseContext) 
	  {
		  Direction direction = blockItemUseContext.getPlacementHorizontalFacing();  // north, east, south, or west

		  BlockState blockState = getDefaultState().with(FACING, direction);
		  return blockState;
	  }
	  
	  @Override
	  protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) 
	  {
		  builder.add(FACING);
	  }
	  private static final DirectionProperty FACING = HorizontalBlock.HORIZONTAL_FACING;
}
