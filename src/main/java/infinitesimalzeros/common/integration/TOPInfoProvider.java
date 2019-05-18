package infinitesimalzeros.common.integration;

import java.util.UUID;
import java.util.function.Function;

import javax.print.attribute.TextSyntax;

import cofh.core.util.helpers.StringHelper;
import infinitesimalzeros.InfinitesimalZeros;
import infinitesimalzeros.common.tileentities.TileEntityAdvancedBoundingBox;
import infinitesimalzeros.common.tileentities.advanced.TileEntityFunctionalMachineT0;
import infinitesimalzeros.common.util.IZUtils;
import infinitesimalzeros.common.util.IZUtils.TypeNumberFormat;
import mcjty.theoneprobe.api.ElementAlignment;
import mcjty.theoneprobe.api.IProbeHitData;
import mcjty.theoneprobe.api.IProbeInfo;
import mcjty.theoneprobe.api.IProbeInfoProvider;
import mcjty.theoneprobe.api.IProgressStyle;
import mcjty.theoneprobe.api.ITextStyle;
import mcjty.theoneprobe.api.ITheOneProbe;
import mcjty.theoneprobe.api.NumberFormat;
import mcjty.theoneprobe.api.ProbeMode;
import mcjty.theoneprobe.api.TextStyleClass;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.UsernameCache;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.fluids.FluidTankInfo;

public class TOPInfoProvider implements Function<ITheOneProbe, Void>, IProbeInfoProvider {

	@Override
	public String getID() {
		
		return InfinitesimalZeros.MODID;
	}
	
	IProbeInfo progressBar(IProbeInfo probeInfo, int current, int max, int color1, int color2, String prefix, String suffix) {
        return probeInfo.progress(current, max, probeInfo.defaultProgressStyle().borderColor(0xff969696).backgroundColor(0x44969696)
                        .filledColor(color1)
                        .alternateFilledColor(color2)
                        .prefix(prefix)
                        .suffix(suffix).width(80)
        );
    }

	@Override
	public void addProbeInfo(ProbeMode mode, IProbeInfo probeInfo, EntityPlayer player, World world, IBlockState blockState, IProbeHitData data) {
		
		{
			
			TileEntity adv = world.getTileEntity(data.getPos());
			
			
			if(adv instanceof TileEntityAdvancedBoundingBox || adv instanceof TileEntityFunctionalMachineT0) {
				
				TileEntityFunctionalMachineT0 tile;
				
				if(adv instanceof TileEntityAdvancedBoundingBox)
					tile = (TileEntityFunctionalMachineT0) ((TileEntityAdvancedBoundingBox) adv).getCoreT0();
				else
					tile = (TileEntityFunctionalMachineT0) adv;
				
				if(tile.isActive) {
	                IProbeInfo hori = probeInfo.horizontal(probeInfo.defaultLayoutStyle().alignment(ElementAlignment.ALIGN_BOTTOMRIGHT).spacing(5));
	                
					if(tile.hasCapability(CapabilityEnergy.ENERGY, null)) {
						hori.progress(100 * tile.operatingTicks / tile.ticksRequired, 100, probeInfo.defaultProgressStyle().filledColor(0xb4cccccc).alternateFilledColor(0xb4aaaaaa).borderColor(0xff969696).backgroundColor(0x44969696).prefix("Progress: ").suffix("%").width(60)).horizontal(probeInfo.defaultLayoutStyle().alignment(ElementAlignment.ALIGN_CENTER).spacing(5));
						hori.text(IZUtils.format(tile.energyPerTick, TypeNumberFormat.COMPACT, "RF/t"));
					} else {
						hori.progress(100 * tile.operatingTicks / tile.ticksRequired, 100, probeInfo.defaultProgressStyle().filledColor(0xb4cccccc).alternateFilledColor(0xb4aaaaaa).borderColor(0xff969696).backgroundColor(0x44969696).prefix("Progress: ").suffix("%").width(68)).horizontal(probeInfo.defaultLayoutStyle().alignment(ElementAlignment.ALIGN_CENTER).spacing(5));
						hori.text(IZUtils.format(tile.energyPerTick, TypeNumberFormat.COMPACT, "mB/s"));
					}
				}

			}
		}
		
	}

	@Override
	public Void apply(ITheOneProbe t) {
		
		t.registerProvider(this);
		return null;
	}
	
}
