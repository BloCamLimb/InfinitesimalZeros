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

public abstract class TileEntityBasicMachine extends TileEntityOperationalMachine implements ISecurityComponent {
	
	public ResourceLocation guiLocation;
	
	public boolean verified;
	
	public String ownerUUID = "";
	public String securityCode = "";
	
	// public RECIPE cachedRecipe = null;
	
	public TileEntityBasicMachine(double maxEnergy) {
		
		super(maxEnergy);
		
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbtTags) {
		
		super.writeToNBT(nbtTags);
		
		nbtTags.setString("OwnerUUID", ownerUUID);
		nbtTags.setString("SecurityCode", securityCode);
		
		return nbtTags;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbtTags) {
		
		super.readFromNBT(nbtTags);
		
		ownerUUID = nbtTags.getString("OwnerUUID");
		securityCode = nbtTags.getString("SecurityCode");
		
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

		super.onUpdate();
		
		if(doAutoAntiCheat && isKeyTime(15) && !world.isRemote)
			antiCheatCheck();
			
	}
	
	@Override
	public void setSecurityCode(ItemStack stack) {
		
		this.ownerUUID = ItemDataUtils.getString(stack, "PlayerUUID");
		this.securityCode = SecurityUtils.encryptMasterUUID(ownerUUID);
		
	}
	
	protected void antiCheatCheck() {
		
	}

	protected boolean isKeyTime(int key) {
		
		return world.getWorldTime() % key == 0;
	}
	
	@Override
    public void setActive(boolean active) {
		
        boolean stateChange = (isActive != active);

        if (stateChange) {
            isActive = active;
            PacketHandler.sendToReceivers(new TileEntityMessage(Coord4D.get(this), getNetworkedData(new TileNetworkList())), new Range4D(Coord4D.get(this)));
        }
    }
	
}
