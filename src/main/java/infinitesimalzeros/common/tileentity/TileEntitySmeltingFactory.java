package infinitesimalzeros.common.tileentity;

import infinitesimalzeros.InfinitesimalZeros;
import infinitesimalzeros.common.tileentity.basis.TileEntityElectricMachine;
import infinitesimalzeros.common.util.interfaces.IAdvancedBoundingBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;

public class TileEntitySmeltingFactory extends TileEntityElectricMachine implements IAdvancedBoundingBlock {

	public TileEntitySmeltingFactory() {
		
		super("SmeltingFactory", 3000000, 2000, 15);
	}

	@Override
	public void onPlace() {
		InfinitesimalZeros.logger.info("n");
		//BlockPos pos = getPos().add(x, y, z);
		
	}

	@Override
	public void onBreak() {
		
		// TODO Auto-generated method stub
		
	}

	@Override
	public int[] getBoundSlots(BlockPos location, EnumFacing side) {
		
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean canBoundInsert(BlockPos location, int i, ItemStack itemstack) {
		
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean canBoundExtract(BlockPos location, int i, ItemStack itemstack, EnumFacing side) {
		
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean canBoundReceiveEnergy(BlockPos location, EnumFacing side) {
		
		// TODO Auto-generated method stub
		return false;
	}
	
}
