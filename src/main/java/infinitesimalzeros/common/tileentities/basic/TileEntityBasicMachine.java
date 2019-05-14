package infinitesimalzeros.common.tileentities.basic;

import infinitesimalzeros.InfinitesimalZeros;
import infinitesimalzeros.api.Coord4D;
import infinitesimalzeros.api.Range4D;
import infinitesimalzeros.api.interfaces.ISecurityComponent;
import infinitesimalzeros.common.core.handler.PacketHandler;
import infinitesimalzeros.common.network.PacketTileEntity.TileEntityMessage;
import infinitesimalzeros.common.util.SecurityUtils;
import infinitesimalzeros.common.util.ItemDataUtils;
import infinitesimalzeros.common.network.TileNetworkList;
import net.minecraft.entity.item.EntityTNTPrimed;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.common.FMLCommonHandler;

public abstract class TileEntityBasicMachine extends TileEntityElectricBlock implements ISecurityComponent {
	
	public boolean verified;
	
	public boolean isActive;
	
	public int energyPerTick;
	
	public int operatingTicks;
	
	public int ticksRequired;
	
	public String ownerUUID = "";
	
	public String securityCode = "";
	
	public TileEntityBasicMachine(double maxEnergy) {
		
		super(maxEnergy);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbtTags) {
		
		super.readFromNBT(nbtTags);
		
		isActive = nbtTags.getBoolean("isActive");
		
		operatingTicks = nbtTags.getInteger("operatingTicks");
		ticksRequired = nbtTags.getInteger("ticksRequired");
		
		ownerUUID = nbtTags.getString("OwnerUUID");
		securityCode = nbtTags.getString("SecurityCode");
		
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbtTags) {
		
		super.writeToNBT(nbtTags);
		
		nbtTags.setBoolean("isActive", isActive);
		
		nbtTags.setInteger("operatingTicks", operatingTicks);
		nbtTags.setInteger("ticksRequired", ticksRequired);
		
		nbtTags.setString("OwnerUUID", ownerUUID);
		nbtTags.setString("SecurityCode", securityCode);
		
		return nbtTags;
	}
	
	@Override
	public void validate() {
		
		super.validate();
		
		if(!world.isRemote && !checkedSecurity && ownerUUID.length() == 36 && securityCode.length() == 36) {
			verified = SecurityUtils.verifySecurityCode(securityCode, ownerUUID);
			checkedSecurity = true;
		}
	}
	
	@Override
	public void onUpdate() {
		
		if(doAutoAntiCheat && isKeyTime(15) && !world.isRemote)
			antiCheatCheck();
			
	}
	
	@Override
	public void setSecurityCode(ItemStack stack) {
		
		this.ownerUUID = ItemDataUtils.getString(stack, "PlayerUUID");
		this.securityCode = SecurityUtils.encryptMasterUUID(ownerUUID);
		
	}
	
	public double getScaledProgress() {
		
		return ((double) operatingTicks) / (double) ticksRequired;
	}
	
	@Override
	public boolean canSetFacing(int facing) {
		
		return facing != 0 && facing != 1;
	}
	
	protected void antiCheatCheck() {
		
	}

	protected boolean isKeyTime(int key) {
		
		return world.getWorldTime() % key == 0;
	}
	
}
