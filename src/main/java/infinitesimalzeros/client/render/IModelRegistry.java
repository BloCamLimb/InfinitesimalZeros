package infinitesimalzeros.client.render;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public interface IModelRegistry {

	@SideOnly(Side.CLIENT)
	void registerModels();
	
}
