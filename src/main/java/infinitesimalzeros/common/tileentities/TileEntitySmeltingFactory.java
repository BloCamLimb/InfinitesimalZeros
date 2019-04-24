package infinitesimalzeros.common.tileentities;

import infinitesimalzeros.api.Coord4D;
import infinitesimalzeros.api.interfaces.IAdvancedBoundingBlock;
import infinitesimalzeros.api.interfaces.IInventoryZero;
import infinitesimalzeros.api.interfaces.ISustainedInventory;
import infinitesimalzeros.common.core.InventoryHandler;
import infinitesimalzeros.common.tileentities.basis.TileEntityElectricMachine;
import infinitesimalzeros.common.util.IZUtils;
import infinitesimalzeros.common.util.LangUtils;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.Constants.NBT;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

public class TileEntitySmeltingFactory extends TileEntityElectricMachine {
	
	public TileEntitySmeltingFactory() {
		
		super("SmeltingFactory", 3000000, 2000, 15);
		

	}


	
}
