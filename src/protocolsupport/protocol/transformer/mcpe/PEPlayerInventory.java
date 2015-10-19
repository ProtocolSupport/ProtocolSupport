package protocolsupport.protocol.transformer.mcpe;

import net.minecraft.server.v1_8_R3.EntityHuman;
import net.minecraft.server.v1_8_R3.ItemStack;
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

}
