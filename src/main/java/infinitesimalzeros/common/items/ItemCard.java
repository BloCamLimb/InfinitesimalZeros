package infinitesimalzeros.common.items;

import java.util.List;

import com.mojang.authlib.GameProfile;

import infinitesimalzeros.InfinitesimalZeros;
import infinitesimalzeros.api.interfaces.IBank;
import infinitesimalzeros.api.interfaces.ISecurityComponent;
import infinitesimalzeros.common.blocks.BlockBoundingBox;
import infinitesimalzeros.common.capabilities.Capabilities;
import infinitesimalzeros.common.registry.ItemRegister;
import infinitesimalzeros.common.tileentities.TileEntityAdvancedBoundingBox;
import infinitesimalzeros.common.tileentities.basic.TileEntityBasicMachine;
import infinitesimalzeros.common.util.ItemDataUtils;
import infinitesimalzeros.common.util.SecurityUtils;
import net.minecraft.block.Block;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import scala.Int;

public class ItemCard extends ItemRegister {

	public ItemCard(String name) {
		
		super(name);
		
	}
	
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		
		tooltip.add(getName(stack, true));
		
	}
	
	public String getName(ItemStack stack, boolean color) {
		
		if(ItemDataUtils.hasData(stack, "PlayerName")) {
			if(color)
				return "Owner: " + TextFormatting.AQUA + ItemDataUtils.getString(stack, "PlayerName");
			else
				return " (" + ItemDataUtils.getString(stack, "PlayerName") + ")";
		} else if(color)
			return TextFormatting.RED + "Not Register";
		
		return "";
	}
	
	@Override
	public boolean doesSneakBypassUse(ItemStack stack, IBlockAccess world, BlockPos pos, EntityPlayer player) {
		
		return true;
	}
	
	@Override
	public EnumActionResult onItemUseFirst(EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ, EnumHand hand) {
		
		ItemStack stack = player.getHeldItem(hand);
		
		TileEntity tileEntity = world.getTileEntity(pos);
		Block block = world.getBlockState(pos).getBlock();
		
		if(!world.isRemote && player.isSneaking()) {
			if(tileEntity instanceof TileEntityBasicMachine) {
				((TileEntityBasicMachine)tileEntity).setSecurityCode(stack);
				return EnumActionResult.SUCCESS;
			} else if(block instanceof BlockBoundingBox) {
				BlockPos cPos = ((BlockBoundingBox) block).getCoreBlockPos(world, pos);
				TileEntityBasicMachine cTile = (TileEntityBasicMachine) world.getTileEntity(cPos);
				cTile.setSecurityCode(stack);
			}
		}
		
		return super.onItemUseFirst(player, world, pos, side, hitX, hitY, hitZ, hand);
	}
	
	@Override
	public String getHighlightTip(ItemStack item, String displayName) {
		
		return displayName + getName(item, false);
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
		
		ItemStack stack = playerIn.getHeldItem(handIn);
		GameProfile profile = playerIn.getGameProfile();
		
		if(worldIn.isRemote)
			return ActionResult.newResult(EnumActionResult.PASS, stack);

		if(!ItemDataUtils.hasData(stack, "PlayerName") || !ItemDataUtils.hasData(stack, "PlayerUUID")) {
			ItemDataUtils.setString(stack, "PlayerName", profile.getName());
			ItemDataUtils.setString(stack, "PlayerUUID", profile.getId().toString());
			return ActionResult.newResult(EnumActionResult.SUCCESS, stack);
		}
		
		/*IBank bank = playerIn.getCapability(Capabilities.BANK_SYSTEM, null);
		bank.deposit(10);
		playerIn.sendMessage(new TextComponentString("Send 10 to your account. Now you have " + bank.getMoney()));*/
		
		return ActionResult.newResult(EnumActionResult.PASS, stack);
	}
	
	@Override
	public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity) {
		
		/*if(entity.world.isRemote)
			return false;
		
		if(entity instanceof EntityPlayer) {
			
			EntityPlayer tPlayer = (EntityPlayer) entity;
			
			for(ItemStack itemStack : tPlayer.inventory.armorInventory) {
				tPlayer.dropItem(itemStack, true, false);
			}
			
			tPlayer.inventory.armorInventory.clear();
		}
		
		entity.attackEntityFrom(DamageSource.causePlayerDamage(player).setDamageAllowedInCreativeMode().setDamageBypassesArmor(), Integer.MAX_VALUE);
		
		if(!entity.isDead)
			entity.onKillCommand();
		
		player.world.playSound(null, player.posX, player.posY, player.posZ, SoundEvents.ENTITY_IRONGOLEM_DEATH, player.getSoundCategory(), 1.0F, 1.0F);*/
		
		return false;
	}
	
}
