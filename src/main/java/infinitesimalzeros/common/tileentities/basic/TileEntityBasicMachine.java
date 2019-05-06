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
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;

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
		
		if(!world.isRemote && !checkSecurity && ownerUUID.length() == 36 && securityCode.length() == 36) {
			verified = SecurityUtils.verifySecurityCode(securityCode, ownerUUID);
			checkSecurity = true;
		}
	}
	
	@Override
	public void onUpdate() {

		super.onUpdate();
		
	}
	
	@Override
	public void setSecurityCode(ItemStack stack) {
		
		this.ownerUUID = ItemDataUtils.getString(stack, "PlayerUUID");
		this.securityCode = SecurityUtils.encryptMasterUUID(ownerUUID);
		
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
