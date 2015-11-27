package protocolsupport.protocol.transformer.mcpe;

import java.util.Arrays;

import net.minecraft.server.v1_8_R3.EntityHuman;
import net.minecraft.server.v1_8_R3.ItemStack;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import net.minecraft.server.v1_8_R3.PlayerInventory;

public class PEPlayerInventory extends PlayerInventory {

	public static final int PLAYER_INVENTORY_WID = 0;
	public static final int PLAYER_ARMOR_WID = 0x78;

	public PEPlayerInventory(EntityHuman entityhuman) {
		super(entityhuman);
	}

	private int[] hotbarToSlot = new int[] {36, 37, 38, 39, 40, 41, 42, 43, 44};
	private int[] slotToHotbar = new int[45]; 
	{
		Arrays.fill(slotToHotbar, -1);
		for (int i = 0; i < 9; i++) {
			slotToHotbar[36 + i] = i;
		}
	}

	public void setHotbarRef(int peSlot, int hotbarSlot) {
		int prevPeSlot = hotbarToSlot[hotbarSlot];
		int prevHotbarSlot = slotToHotbar[peSlot];
		if (prevHotbarSlot != -1) {
			hotbarToSlot[prevHotbarSlot] = prevPeSlot;
		}
		slotToHotbar[prevPeSlot] = prevHotbarSlot;
		hotbarToSlot[hotbarSlot] = peSlot;
		slotToHotbar[peSlot] = hotbarSlot;
	}

	public int[] getHotbarRefs() {
		return hotbarToSlot;
	}

	public int getHotbarSlotFor(int peSlot) {
		return slotToHotbar[peSlot];
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

	private static final String SLOT_TAG_NAME = "PSSlotTag";

	//convert network slot to player inventory slot
	public static int toInvSlot(int netSlot, int datalength) {
		int playerInvStartIndex = datalength - 36;
		if (netSlot >= playerInvStartIndex) {
			int netPlayerSlot = netSlot - playerInvStartIndex;
			if (netPlayerSlot >= 0 && netPlayerSlot < 27) {
				return netPlayerSlot + 9;
			}
			if (netPlayerSlot >= 27 && netPlayerSlot < 35) {
				return netPlayerSlot - 27;
			}
		}
		return -1;
	}

	//Adds slot number to item nbt, allows us to solve problem with switching between same itemstacks and will also allow to tell us which slot client used
	public static ItemStack addSlotNumberTag(ItemStack itemstack, int invSlot) {
		if (invSlot == -1) {
			return itemstack;
		}
		if (itemstack == null) {
			return null;
		}
		NBTTagCompound tag = itemstack.getTag();
		if (tag == null) {
			tag = new NBTTagCompound();
		}
		tag.setInt(SLOT_TAG_NAME, invSlot);
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
		if (slot > 35) {
			return -1;
		}
		return slot;
	}

}
