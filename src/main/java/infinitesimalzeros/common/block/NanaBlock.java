package infinitesimalzeros.common.block;

import java.util.List;
import java.util.Random;

import infinitesimalzeros.common.registry.BlockRegister;
import infinitesimalzeros.common.registry.RegistryBlocks;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class NanaBlock extends BlockRegister {

	public NanaBlock(String name) {
		super(name, Material.IRON);
		this.setHardness(1);
		this.setResistance(15);
		this.setHarvestLevel("pickaxe", 0);
	}

	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune) {
		return Item.getItemFromBlock(RegistryBlocks.NanaBlock);
	}
	
	@Override
	public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, Entity entityIn, boolean isActualState) {
		super.addCollisionBoxToList(state, worldIn, pos, entityBox, collidingBoxes, entityIn, isActualState);
	}
	
}
