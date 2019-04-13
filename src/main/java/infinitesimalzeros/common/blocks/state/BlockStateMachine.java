package infinitesimalzeros.common.blocks.state;

import com.google.common.base.Predicate;

import infinitesimalzeros.common.blocks.BlockTileEntityCore;
import infinitesimalzeros.common.blocks.BlockTileEntityCore.MachineSets;
import infinitesimalzeros.common.blocks.BlockTileEntityCore.MachineTypes;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraftforge.common.property.ExtendedBlockState;
import net.minecraftforge.common.property.IUnlistedProperty;

public class BlockStateMachine extends ExtendedBlockState {
	
	public static final PropertyBool activeProperty = PropertyBool.create("active");
	
	public BlockStateMachine(BlockTileEntityCore block, PropertyEnum<?> typeProperty) {
		
		super(block, new IProperty[] { BlockStateFacing.facingProperty, typeProperty, activeProperty }, new IUnlistedProperty[] {});
	}
	
	public static class MachineBlockPredicate implements Predicate<MachineTypes> {
		
		public MachineSets machineBlock;
		
		public MachineBlockPredicate(MachineSets type) {
			
			machineBlock = type;
		}
		
		@Override
		public boolean apply(MachineTypes input) {
			
			return input.block == machineBlock;
		}
	}
	
}
