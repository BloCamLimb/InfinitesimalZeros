package infinitesimalzeros.common.tileentity.basis;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import infinitesimalzeros.api.Coord4D;
import infinitesimalzeros.api.Range4D;
import infinitesimalzeros.common.network.PacketDataRequest.DataRequestMessage;
import infinitesimalzeros.common.network.PacketTileEntity.TileEntityMessage;
import infinitesimalzeros.common.network.TileNetworkList;
import infinitesimalzeros.common.util.IZUtils;
import infinitesimalzeros.common.util.handlers.PacketHandler;
import infinitesimalzeros.common.util.interfaces.ITileComponent;
import infinitesimalzeros.common.util.interfaces.ITileNetwork;
import io.netty.buffer.ByteBuf;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.items.ItemStackHandler;

public abstract class TileEntityBasicBlock extends TileEntity implements ITickable, ITileNetwork {
	
	public EnumFacing facing = EnumFacing.NORTH;
	
	public EnumFacing clientFacing = facing;
	
	public HashSet<EntityPlayer> playersUsing = new HashSet<>();
	
	public int ticker;
	
	public boolean doAutoSync = true;
	
	public List<ITileComponent> components = new ArrayList<>();

	@Override
	public void onLoad() {
		super.onLoad();
		if(world.isRemote)
		{
			PacketHandler.network.sendToServer((IMessage) new DataRequestMessage(Coord4D.get(this)));
		}
	}
	
	@Override
	public void update() {
		for(ITileComponent component : components)
		{
			component.tick();
		}
		
		onUpdate();
		
		if(!world.isRemote)
		{
			if(doAutoSync && playersUsing.size() > 0)
			{
				for(EntityPlayer player : playersUsing)
				{
					PacketHandler.network.sendTo(new TileEntityMessage(Coord4D.get(this), getNetworkedData(new TileNetworkList())), (EntityPlayerMP)player);
				}
			}
		}

		ticker++;
	}
	
	@Override
	public void updateContainingBlockInfo()
	{
		super.updateContainingBlockInfo();
		
	}

	public void open(EntityPlayer player)
	{
		playersUsing.add(player);
	}

	public void close(EntityPlayer player)
	{
		playersUsing.remove(player);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		
		super.readFromNBT(compound);
		
		if(compound.hasKey("facing")) {
			facing = EnumFacing.getFront(compound.getInteger("facing"));
		}
		
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		
		super.writeToNBT(compound);
		
		if(facing != null) {
			compound.setInteger("facing", facing.ordinal());
		}
		
		return compound;
	}

	@Override
	public void handlePacketData(ByteBuf dataStream)
	{
		if(FMLCommonHandler.instance().getEffectiveSide().isClient())
		{
			facing = EnumFacing.getFront(dataStream.readInt());
	
			if(clientFacing != facing)
			{
				IZUtils.updateBlock(world, getPos());
				world.notifyNeighborsOfStateChange(getPos(), world.getBlockState(getPos()).getBlock(), true);
				clientFacing = facing;
			}
	
			for(ITileComponent component : components)
			{
				component.read(dataStream);
			}
		}
	}
	
	@Override
	public void invalidate()
	{
		super.invalidate();
		
		for(ITileComponent component : components)
		{
			component.invalidate();
		}
	}

	@Override
	public void validate()
	{
		super.validate();

		if(world.isRemote)
		{
			PacketHandler.network.sendToServer(new DataRequestMessage(Coord4D.get(this)));
		}
	}
	
	public abstract void onUpdate();
	
	@Override
	public TileNetworkList getNetworkedData(TileNetworkList data) {
		data.add(facing == null ? -1 : facing.ordinal());

		for(ITileComponent component : components)
		{
			component.write(data);
		}

		return data;
	}

	public boolean canSetFacing(int facing)
	{
		return true;
	}
	
	public void setFacing(short direction)
	{
		if(canSetFacing(direction))
		{
			facing = EnumFacing.getFront(direction);
		}

		if(!(facing == clientFacing || world.isRemote))
		{
			PacketHandler.sendToReceivers(new TileEntityMessage(Coord4D.get(this), getNetworkedData(new TileNetworkList())), new Range4D(Coord4D.get(this)));
			markDirty();
			clientFacing = facing;
		}
	}
	
}
