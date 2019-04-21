package infinitesimalzeros.common.tileentities;

import infinitesimalzeros.api.Coord4D;
import infinitesimalzeros.common.core.handler.InventoryHandler;
import infinitesimalzeros.common.registry.RegistryItems;
import infinitesimalzeros.common.tileentities.basis.TileEntityElectricMachine;
import infinitesimalzeros.common.util.IZUtils;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TileEntitySmelter extends TileEntityElectricMachine {
	
	public TileEntitySmelter() {
		
		super("Smelter", 5000000, 600, 30);
		size = 12;
		inventory = NonNullList.withSize(12, ItemStack.EMPTY);
		
		insertionHandler = new InventoryHandler(11, this, 0, true, false);
		extractionHandler = new InventoryHandler(1, this, 11, false, true);
		
	}

	@Override
	@SideOnly(Side.CLIENT)
	public AxisAlignedBB getRenderBoundingBox() {
		
		return INFINITE_EXTENT_AABB;
	}

	@Override
	public void doGraphicalUpdates(int slot) {
		
	}
	
	@Override
	public void onUpdate() {
		
		super.onUpdate();
		
		if(!inventory.get(0).isEmpty() && getEnergy() >= energyPerTick) {
			setActive(true);
			electricityStored -= energyPerTick;
			
			if ((operatingTicks + 1) < ticksRequired) {
                operatingTicks++;
            } else if ((operatingTicks + 1) >= ticksRequired) {
            	
            	int i = inventory.get(0).getCount()-1;
            	
            	inventory.get(0).setCount(i);
            	
            	if(inventory.get(11).isEmpty())
            		inventory.set(11, new ItemStack(Blocks.DIAMOND_BLOCK));
            	else
            		inventory.get(11).setCount(inventory.get(11).getCount()+1);

            	operatingTicks = 0;
            }
			
		}
		
		if(inventory.get(0).isEmpty()) {
			setActive(false);
			operatingTicks = 0;
		}
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
	
	

}
