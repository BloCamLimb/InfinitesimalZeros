package infinitesimalzeros.common.tileentities;

import infinitesimalzeros.InfinitesimalZeros;
import infinitesimalzeros.api.Coord4D;
import infinitesimalzeros.api.interfaces.IAdvancedBoundingBlock;
import infinitesimalzeros.api.interfaces.IInventoryZero;
import infinitesimalzeros.api.interfaces.ISustainedInventory;
import infinitesimalzeros.common.core.handler.InventoryHandler;
import infinitesimalzeros.common.tileentities.basis.TileEntityElectricMachine;
import infinitesimalzeros.common.util.IZUtils;
import infinitesimalzeros.common.util.LangUtils;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.Constants.NBT;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

public class TileEntitySmelter extends TileEntityElectricMachine {
	
	public TileEntitySmelter() {
		
		super("Smelter", 50000, 60, 200);
		size = 12;
		inventory = NonNullList.withSize(12, ItemStack.EMPTY);
		
		insertionHandler = new InventoryHandler(11, this, 0, true, false);
		extractionHandler = new InventoryHandler(1, this, 11, false, true);
		
	}


	@Override
	public void doGraphicalUpdates(int slot) {
		
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
