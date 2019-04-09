package infinitesimalzeros.common.block.state;

import net.minecraft.block.Block;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;

public class BlockStateFacing extends BlockStateContainer {
	
	public static final PropertyDirection facingProperty = PropertyDirection.create("facing");
	
	public BlockStateFacing(Block block) {
		
		super(block, facingProperty);
	}
	
}
