package infinitesimalzeros.common.items;

import java.util.HashMap;

import cofh.api.block.IDismantleable;
import cofh.api.item.IToolHammer;
import cofh.core.util.helpers.BlockHelper;
import cofh.core.util.helpers.ServerHelper;
import mekanism.api.IMekWrench;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.Event.Result;

public class Neutron extends Item implements IToolHammer, IMekWrench {
	
	private static final String name = "neutron";
	public HashMap<String, Integer> colorMap = new HashMap<>();
	
	public Neutron() {
		
		super();
		this.setCreativeTab(CreativeTabs.MATERIALS);
	}
	
	@Override
	public String getItemStackDisplayName(ItemStack stack) {
		
		String ss = "any";
		NBTTagCompound nbt = stack.getTagCompound();
		if(nbt == null) {
			nbt = new NBTTagCompound();
		}
		if(nbt.getString("meta") != "") {
			ss = nbt.getString("meta").toLowerCase();
		}
		String sss = I18n.format("material." + ss, ss);
		return super.getItemStackDisplayName(stack) + " (" + sss + ")";
	}
	
	@Override
	public EnumActionResult onItemUseFirst(EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ, EnumHand hand) {

		IBlockState state = world.getBlockState(pos);
		Block block = state.getBlock();
		
		if (world.isAirBlock(pos)) {
			return EnumActionResult.PASS;
		}
		PlayerInteractEvent.RightClickBlock event = new PlayerInteractEvent.RightClickBlock(player, hand, pos, side, new Vec3d(hitX, hitY, hitZ));
		if (MinecraftForge.EVENT_BUS.post(event) || event.getResult() == Result.DENY || event.getUseBlock() == Result.DENY || event.getUseItem() == Result.DENY) {
			return EnumActionResult.PASS;
		}
		if (ServerHelper.isServerWorld(world) && player.isSneaking()) {
			((IDismantleable) block).dismantleBlock(world, pos, state, player, false);
			return EnumActionResult.SUCCESS;
		}
		if (BlockHelper.canRotate(block)) {
			world.setBlockState(pos, BlockHelper.rotateVanillaBlock(world, state, pos), 3);
			player.swingArm(hand);
			return ServerHelper.isServerWorld(world) ? EnumActionResult.SUCCESS : EnumActionResult.PASS;
		} else if (!player.isSneaking() && block.rotateBlock(world, pos, side)) {
			player.swingArm(hand);
			return ServerHelper.isServerWorld(world) ? EnumActionResult.SUCCESS : EnumActionResult.PASS;
		}
		return EnumActionResult.SUCCESS;
	}

	@Override
	public boolean isUsable(ItemStack item, EntityLivingBase user, BlockPos pos) {
		
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isUsable(ItemStack item, EntityLivingBase user, Entity entity) {
		
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public void toolUsed(ItemStack item, EntityLivingBase user, BlockPos pos) {
		
		// TODO Auto-generated method stub
		
	}

	@Override
	public void toolUsed(ItemStack item, EntityLivingBase user, Entity entity) {
		
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean canUseWrench(ItemStack stack, EntityPlayer player, BlockPos pos) {
		
		// TODO Auto-generated method stub
		return true;
	}
	
}
