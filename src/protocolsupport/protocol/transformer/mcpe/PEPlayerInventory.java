package protocolsupport.protocol.transformer.mcpe;

import protocolsupport.protocol.transformer.mcpe.packet.mcpe.clientbound.ContainerSetContentsPacket;
import net.minecraft.server.v1_8_R3.EntityHuman;
import net.minecraft.server.v1_8_R3.EntityPlayer;
import net.minecraft.server.v1_8_R3.ItemStack;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import net.minecraft.server.v1_8_R3.PlayerInventory;

public class PEPlayerInventory extends PlayerInventory {

	public static final int PLAYER_INVENTORY_WID = 0;
	public static final int PLAYER_ARMOR_WID = 0x78;

	public PEPlayerInventory(EntityHuman entityhuman) {
		super(entityhuman);
	}

	private int[] hotbar = ContainerSetContentsPacket.HOTBAR_SLOTS;

	public void setHotbarRef(int realSlot, int hotbarSlot) {
		hotbar[hotbarSlot] = realSlot;
	}

	public int[] getHotbarRefs() {
		return hotbar;
	}

	public void setSelectedSlot(int index) {
		if (index >= 0 && index < getSize() - 4) {
			itemInHandIndex = index;
		}
	}

	@Override
	public ItemStack getItemInHand() {
		if (itemInHandIndex >= 0 && itemInHandIndex < getSize() - 4) {
			return items[itemInHandIndex];
		}
		return null;
	}

	@Override
	public boolean pickup(ItemStack itemstack) {
		boolean result = super.pickup(itemstack);
		((EntityPlayer) player).updateInventory(player.activeContainer);
		return result;
	}

	private static final String SLOT_TAG_NAME = "PSSlotTag";

	//Adds slot number to item nbt, allows us to solve problem with switching between same itemstacks and will also allow to tell us which slot client used
	public static ItemStack addSlotNumberTag(ItemStack itemstack, int netSlot) {
		if (itemstack == null) {
			return null;
		}
		NBTTagCompound tag = itemstack.getTag();
		if (tag == null) {
			tag = new NBTTagCompound();
		}
		tag.setInt(SLOT_TAG_NAME, netSlot);
		itemstack.setTag(tag);
		return itemstack;
	}

	//Returns inventory slot from itemstack
	public static int getSlotNumber(ItemStack itemstack) {
		if (itemstack == null) {
			return -1;
		}
		NBTTagCompound tag = itemstack.getTag();
		if (tag == null) {
			return -1;
		}
		if (!tag.hasKeyOfType(SLOT_TAG_NAME, 99)) {
			return -1;
		}
		int slot = tag.getInt(SLOT_TAG_NAME);
		if (slot >= 9 && slot < 36) {
			return slot;
		}
		if (slot >= 36 && slot < 45) {
			return slot - 36;
		}
		return -1;
	}

}
