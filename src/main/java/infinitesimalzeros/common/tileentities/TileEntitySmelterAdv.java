package infinitesimalzeros.common.tileentities;

import java.util.List;

import javax.annotation.Nonnull;

import infinitesimalzeros.client.gui.tab.GuiBasicTab.GuiTabs;
import infinitesimalzeros.client.gui.tab.GuiTabHome;
import infinitesimalzeros.common.tileentities.basis.TileEntityElectricMachine;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

public class TileEntitySmelterAdv extends TileEntityElectricMachine {
	
	public TileEntitySmelterAdv() {
		
		super("", 500000, 60, 200);
	}

	@Override
	public NonNullList<ItemStack> getInventory() {
		
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void doGraphicalUpdates(int slot) {
		
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPlace() {
		
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onBreak() {
		
		// TODO Auto-generated method stub
		
	}
	
}
