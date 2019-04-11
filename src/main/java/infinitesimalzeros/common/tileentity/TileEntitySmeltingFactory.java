package infinitesimalzeros.common.tileentity;

import infinitesimalzeros.InfinitesimalZeros;
import infinitesimalzeros.api.Coord4D;
import infinitesimalzeros.common.tileentity.basis.TileEntityElectricMachine;
import infinitesimalzeros.common.util.IZUtils;
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
		
		for(int x = -1; x <= 1; x++) {
			
			for(int y = 0; y <= 3; y++) {
				
				for(int z = -1; z <= 1; z++) {
					
					if(x == 0 && y == 0 && z == 0) {
						
						continue;
					}
					
					BlockPos pos = getPos().add(x,y,z);
					IZUtils.constructAdvBoundingBox(world, pos, Coord4D.get(this));
					world.notifyNeighborsOfStateChange(pos, getBlockType(), true);
				}
			}
		}
		
	}

	@Override
	public void onBreak() {
		
		for(int x = -1; x <= 1; x++) {
			
			for(int y = 0; y <= 3; y++) {
				
				for(int z = -1; z <= 1; z++) {
					
					world.setBlockToAir(getPos().add(x,y,z));
				}
			}
		}
		
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
