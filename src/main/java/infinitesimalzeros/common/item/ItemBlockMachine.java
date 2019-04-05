package infinitesimalzeros.common.item;

import infinitesimalzeros.common.util.ItemDataUtils;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagList;

public class ItemBlockMachine extends ItemBlock {
	
	public Block metaBlock;

	public ItemBlockMachine(Block block)
	{
		super(block);
		metaBlock = block;
		setHasSubtypes(true);
		setNoRepair();
		setMaxStackSize(1);
	}
	
	public void setInventory(NBTTagList nbtTags, Object... data)
	{
		if(data[0] instanceof ItemStack)
		{
			ItemDataUtils.setList((ItemStack)data[0], "Items", nbtTags);
		}
	}

	public NBTTagList getInventory(Object... data)
	{
		if(data[0] instanceof ItemStack)
		{
			return ItemDataUtils.getList((ItemStack)data[0], "Items");
		}

		return null;
	}
	
	/*public void setEnergy(ItemStack itemStack, double amount)
	{
		if(!MachineType.get(itemStack).isElectric)
		{
			return;
		}
		
		ItemDataUtils.setDouble(itemStack, "energyStored", amount);
	}*/


}
