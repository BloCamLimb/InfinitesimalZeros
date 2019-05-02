package infinitesimalzeros.common.blocks;

import java.util.List;
import java.util.Random;

import infinitesimalzeros.common.registry.BlockRegister;
import infinitesimalzeros.common.registry.RegistryBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
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
	public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
		
		Block below = worldIn.getBlockState(pos.add(0, -1, 0)).getBlock();
		return below == Blocks.DIAMOND_BLOCK;
	}
	
}
