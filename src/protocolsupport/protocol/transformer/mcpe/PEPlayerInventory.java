package protocolsupport.protocol.transformer.mcpe;

import java.util.concurrent.ThreadLocalRandom;

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

	@Override
	public ItemStack getItemInHand() {
		if (itemInHandIndex >= 0 && itemInHandIndex < getSize()) {
			return items[itemInHandIndex];
		}
		return null;
	}

	//Adds random integer to itemstack tags
	//This method is always used on sending itemstacks
	//Why do we do this?
	//Because switching to the same itemstack in the inventory doesn't send the packet,
	// so we will think that client uses some slot, but client will actually try to use another
	//We solve this problem by adding random tag, so itemstacks won't be equal
	public static ItemStack addFakeTag(ItemStack itemstack) {
		if (itemstack == null) {
			return null;
		}
		NBTTagCompound tag = itemstack.getTag();
		if (tag == null) {
			tag = new NBTTagCompound();
		}
		tag.setInt("PSFakeTag", ThreadLocalRandom.current().nextInt());
		itemstack.setTag(tag);
		return itemstack;
	}

}
