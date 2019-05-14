package infinitesimalzeros.common.tileentities;

import infinitesimalzeros.InfinitesimalZeros;
import infinitesimalzeros.api.Coord4D;
import infinitesimalzeros.common.core.InventoryHandlerZero;
import infinitesimalzeros.common.recipe.RecipeT1;
import infinitesimalzeros.common.recipe.core.RecipeCoreT1;
import infinitesimalzeros.common.registry.RegistryItems;
import infinitesimalzeros.common.tileentities.advanced.TileEntityFunctionalMachineT1;
import infinitesimalzeros.common.util.IZUtils;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidTank;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TileEntitySmelter extends TileEntityFunctionalMachineT1 {
	
	private RecipeT1 curRecipe;
	
	public TileEntitySmelter() {
		
		super("NanaSmelter", 5000000);
		
	}

	@Override
	@SideOnly(Side.CLIENT)
	public AxisAlignedBB getRenderBoundingBox() {
		
		return INFINITE_EXTENT_AABB;
	}

	@Override
	public void doGraphicalUpdates(int slot) {
		
	}
	
	@Override
	public void onUpdate() {
		
		super.onUpdate();

	}
	
	@Override
	protected boolean canProcess() {
		
		if (inventory.get(0).isEmpty() || getEnergy() <= 0) {
			return false;
		}

		getRecipe();

		if (curRecipe == null) {
			return false;
		}
		if (inventory.get(0).getCount() < curRecipe.getInput().getCount() || getEnergy() < curRecipe.getPower()) {
			return false;
		}
		
		ItemStack primaryItem = curRecipe.getPrimaryOutput();
		
		return curRecipe.getInput().getCount() <= inventory.get(0).getCount() && inventory.get(1).isEmpty() || inventory.get(1).isItemEqual(primaryItem) && inventory.get(1).getCount() + primaryItem.getCount() <= primaryItem.getMaxStackSize();
	}
	

	protected void getRecipe() {

		curRecipe = RecipeCoreT1.getRecipe(inventory.get(0));
	}
	
	@Override
	protected void doFinish() {
		
		super.doFinish();
		
		if (curRecipe == null) {
			getRecipe();
		}
		if (curRecipe == null) {
			turnOff();
			return;
		}
		
		ItemStack primaryItem = curRecipe.getPrimaryOutput();
		
		if (inventory.get(1).isEmpty()) {
			inventory.set(1, primaryItem.copy());
		} else {
			inventory.get(1).grow(primaryItem.getCount());
		}
		
		inventory.get(0).shrink(curRecipe.getInput().getCount());
		
		if (inventory.get(0).getCount() <= 0) {
			inventory.set(0, ItemStack.EMPTY);
		}
		
		if(canProcess()) {
			ticksRequired = curRecipe.getTime();
			energyPerTick = curRecipe.getPower();
		} else
			turnOff();
	}
	
	@Override
	protected void turnOn() {
		
		super.turnOn();
		
		ticksRequired = curRecipe.getTime();
		energyPerTick = curRecipe.getPower();
	}

	@Override
	public void onPlace(EnumFacing side) {
		
		for(int x = -1; x <= 1; x++) {
			
			for(int y = 0; y <= 3; y++) {
				
				for(int z = -1; z <= 1; z++) {
					
					if(x == 0 && y == 0 && z == 0)
						continue;

					int xx = side == EnumFacing.SOUTH ? x : side == EnumFacing.EAST ? -z : side == EnumFacing.NORTH ? -x : z;
					int zz = side == EnumFacing.SOUTH ? z : side == EnumFacing.EAST ? -x : side == EnumFacing.NORTH ? -z : x;
					
					BlockPos pos = getPos().add(xx, y, zz);
					
					if(x == -1 && y == 0 && z == 0)
						IZUtils.constructAdvBoundingBox(world, pos, Coord4D.get(this), 1);
					else if (x == 1 && y == 0 && z == 0)
						IZUtils.constructAdvBoundingBox(world, pos, Coord4D.get(this), 2);
					else
						IZUtils.constructAdvBoundingBox(world, pos, Coord4D.get(this), 0);
					
					world.notifyNeighborsOfStateChange(pos, getBlockType(), true);
				}
			}
		}
		
	}

	@Override
	public void onBreak() {
		
		for(int x = -1; x <= 1; x++) {
			
			for(int y = 0; y <= 3; y++) {
				
				for(int z = -1; z <= 1; z++) {
					
					world.setBlockToAir(getPos().add(x,y,z));
				}
			}
		}
		
	}

	@Override
	public boolean isStackValid(int slot, ItemStack stack) {
		
		return RecipeCoreT1.recipeExists(stack);
	}

	@Override
	public IFluidTank[] getAccessibleFluidTanks(EnumFacing side) {
		
		return null;
	}

	@Override
	public boolean canFillTankFrom(int iTank, EnumFacing side, FluidStack resource) {
		
		return false;
	}

	@Override
	public boolean canDrainTankFrom(int iTank, EnumFacing side) {
		
		return false;
	}
	
	

}
