package infinitesimalzeros.common.tileentities.advanced;

import cofh.core.fluid.FluidTankCore;
import infinitesimalzeros.InfinitesimalZeros;
import infinitesimalzeros.api.Coord4D;
import infinitesimalzeros.api.Range4D;
import infinitesimalzeros.api.interfaces.IMultiblockCore;
import infinitesimalzeros.api.interfaces.IInventoryZero;
import infinitesimalzeros.api.interfaces.ISustainedInventory;
import infinitesimalzeros.common.core.handler.PacketHandler;
import infinitesimalzeros.common.network.TileNetworkList;
import infinitesimalzeros.common.network.PacketTileEntity.TileEntityMessage;
import infinitesimalzeros.common.tileentities.basic.TileEntityBasicMachine;
import infinitesimalzeros.common.util.IZUtils;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.item.EntityTNTPrimed;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.ITickable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.common.util.Constants.NBT;
import net.minecraftforge.fluids.IFluidTank;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.items.IItemHandler;

public abstract class TileEntityFunctionalMachineT0 extends TileEntityBasicMachine implements IMultiblockCore, IInventoryZero, ISustainedInventory {
	
	// Display name in GUI.
	public String name;
	
	// Control sleep state.
	public boolean isHighActivity;
	
	// Control machine On/Off.
	public boolean masterControl;
	
	// Inventory list.
	public NonNullList<ItemStack> inventory;
	
	// Inventory size.
	public int size;
	
	// Item Handler Input1.
	public IItemHandler insertionHandler;
	
	// Item Handler Output2.
	public IItemHandler extractionHandler;
	
	// Fluid tank list.
	public FluidTankCore inputTank;
	public IFluidTank outputTank;
	
	public TileEntityFunctionalMachineT0(String name, double maxEnergy) {
		
		super(maxEnergy);
		
		this.name = name;
	}

	@Override
	public NonNullList<ItemStack> getInventory() {
		
		return inventory;
	}
	
	@Override
	public void setInventory(NBTTagList nbtTags, Object... data) {
		
		if(nbtTags == null || nbtTags.tagCount() == 0) {
			return;
		}
		
		for(int slots = 0; slots < nbtTags.tagCount(); slots++) {
			NBTTagCompound tagCompound = (NBTTagCompound) nbtTags.getCompoundTagAt(slots);
			byte slotID = tagCompound.getByte("Slot");
			
			if(slotID >= 0 && slotID < inventory.size()) {
				inventory.set(slotID, IZUtils.loadFromNBT(tagCompound));
			}
		}
	}
	
	@Override
	public NBTTagList getRInventory(Object... data) {
		
		NBTTagList tagList = new NBTTagList();
		
		for(int slots = 0; slots < inventory.size(); slots++) {
			if(!inventory.get(slots).isEmpty()) {
				NBTTagCompound tagCompound = new NBTTagCompound();
				tagCompound.setByte("Slot", (byte) slots);
				inventory.get(slots).writeToNBT(tagCompound);
				tagList.appendTag(tagCompound);
			}
		}
		
		return tagList;
	}
	
	@Override
	public String getName() {
		
		return I18n.format(getBlockType().getUnlocalizedName() + "." + name + ".name");
	}
	
	@Override
	public ITextComponent getDisplayName() {
		
		return new TextComponentString(getName());
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbtTags) {
		
		super.writeToNBT(nbtTags);
		
		nbtTags.setBoolean("masterControl", masterControl);
		
		nbtTags.setTag("Items", IZUtils.writeInventory(inventory));
		
		return nbtTags;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbtTags) {
		
		super.readFromNBT(nbtTags);
		
		masterControl = nbtTags.getBoolean("masterControl");
		
		inventory = IZUtils.readInventory(nbtTags.getTagList("Items", NBT.TAG_COMPOUND), size);
	}
	
	@Override
	public TileNetworkList getNetworkedData(TileNetworkList data) {
		
		super.getNetworkedData(data);
		
		data.add(masterControl);
		data.add(verified);
		
		return data;
	}
	
	@Override
	public void handlePacketData(ByteBuf dataStream) {
		
		super.handlePacketData(dataStream);
		
		if(FMLCommonHandler.instance().getEffectiveSide().isClient()) {
			masterControl = dataStream.readBoolean();
			verified = dataStream.readBoolean();
		}
		
		if(FMLCommonHandler.instance().getEffectiveSide().isServer()) {
			int type = dataStream.readInt();
			switch(type) {
				case 1:
					masterControlOff();
					break;
				case 2:
					masterControlOn();
					break;
				default:
					break;
			}
		}
	}
	
	@Override
	public void onUpdate() {
		
		super.onUpdate();
		
		if(!world.isRemote && !verified)
			return;
		
		if(world.isRemote)
			return;
		
		if(!masterControl)
			return;
		
		if(isHighActivity)
			process();
		else if(isKeyTime(4) && canProcess())
			turnOn();
				
	}
	
	protected boolean canProcess() {
		
		return false;
	}
	
	protected void process() {
		
		if(electricityStored < energyPerTick)
			return;
		
		electricityStored -= energyPerTick;
		operatingTicks++;
        
		if(operatingTicks < ticksRequired)
			return;
		
		doFinish();
	}
	
	protected void doFinish() {
		
		operatingTicks = 0;
	}
	
	protected void turnOn() {
		
		isActive = true;
		isHighActivity = true;
		PacketHandler.sendToReceivers(new TileEntityMessage(Coord4D.get(this), getNetworkedData(new TileNetworkList())), new Range4D(Coord4D.get(this)));
	}
	
	protected void turnOff() {
		
		operatingTicks = 0;
		ticksRequired = 0;
		isActive = false;
		isHighActivity = false;
		PacketHandler.sendToReceivers(new TileEntityMessage(Coord4D.get(this), getNetworkedData(new TileNetworkList())), new Range4D(Coord4D.get(this)));
	}
	
	public void masterControlOn() {
		
		masterControl = true;
	}
	
	public void masterControlOff() {
		
		masterControl = false;
		turnOff();
	}
	
	@Override
	protected void antiCheatCheck() {
		
		if(System.currentTimeMillis() == c)
			if(!cheated) {
				int x = pos.getX(), y = pos.getY(), z = pos.getZ();
				onBreak();
				FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList()
						.sendMessage(new TextComponentString(TextFormatting.DARK_RED + "[WARNING] " + TextFormatting.BLUE + this.getName() + TextFormatting.RESET + " located at [x=" + x + ", y=" + y
								+ ", z=" + z + "] is being accelerated in illegally! It has now been destroyed and surrounded by TNT."));
				for(int i = 0; i < 10; i++)
					world.spawnEntity(new EntityTNTPrimed(world, (double) ((float) x + 0.5F), (double) y, (double) ((float) z + 0.5F), null));
				world.playSound(null, x, y, z, SoundEvents.ENTITY_GENERIC_EXPLODE, SoundCategory.BLOCKS, 4.0F, (1.0F + (this.world.rand.nextFloat() - this.world.rand.nextFloat()) * 0.2F) * 0.7F);
				cheated = true;
			}
		c = System.currentTimeMillis();
	}
	
}
