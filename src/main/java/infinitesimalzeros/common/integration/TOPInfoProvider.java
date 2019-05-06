package infinitesimalzeros.common.integration;

import java.util.UUID;
import java.util.function.Function;

import javax.print.attribute.TextSyntax;

import infinitesimalzeros.InfinitesimalZeros;
import infinitesimalzeros.common.tileentities.TileEntityAdvancedBoundingBox;
import infinitesimalzeros.common.tileentities.advanced.TileEntityFunctionalMachineT0;
import mcjty.theoneprobe.api.IProbeHitData;
import mcjty.theoneprobe.api.IProbeInfo;
import mcjty.theoneprobe.api.IProbeInfoProvider;
import mcjty.theoneprobe.api.ITheOneProbe;
import mcjty.theoneprobe.api.ProbeMode;
import mcjty.theoneprobe.api.TextStyleClass;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.UsernameCache;
import net.minecraftforge.fluids.FluidTankInfo;

public class TOPInfoProvider implements Function<ITheOneProbe, Void>, IProbeInfoProvider {

	@Override
	public String getID() {
		
		return InfinitesimalZeros.MODID;
	}

	@Override
	public void addProbeInfo(ProbeMode mode, IProbeInfo probeInfo, EntityPlayer player, World world, IBlockState blockState, IProbeHitData data) {
		
		//if(mode == ProbeMode.EXTENDED) {
			
			TileEntity adv = world.getTileEntity(data.getPos());
			
			
			if(adv instanceof TileEntityAdvancedBoundingBox) {
				TileEntityFunctionalMachineT0 tile = (TileEntityFunctionalMachineT0) ((TileEntityAdvancedBoundingBox) adv).getCoreT0();
				probeInfo.text(TextStyleClass.INFO + "Owner: " + UsernameCache.getLastKnownUsername(UUID.fromString(tile.ownerUUID)));
			}
		//}
		
	}

	@Override
	public Void apply(ITheOneProbe t) {
		
		t.registerProvider(this);
		return null;
	}
	
}
