package infinitesimalzeros.client.render.item;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import infinitesimalzeros.common.blocks.BlockTileEntityCore.MachineTypes;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderMachineItem extends SubTypeItemRenderer<MachineTypes> {
	
	public static Map<MachineTypes, ItemLayerWrapper> modelMap = new HashMap<>();
	
	@Nullable
    @Override
    protected ItemLayerWrapper getModel(MachineTypes machineType) {
        return modelMap.get(machineType);
    }

    @Nullable
    @Override
    protected MachineTypes getType(@Nonnull ItemStack stack) {
        return MachineTypes.get(stack);
    }
	
}
