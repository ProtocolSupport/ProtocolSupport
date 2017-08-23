package protocolsupport.protocol.typeremapper.pe;

import java.util.ArrayList;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe.InventorySetItems;
import protocolsupport.protocol.utils.types.WindowType;
import protocolsupport.utils.recyclable.RecyclableArrayList;
import protocolsupport.zplatform.itemstack.ItemStackWrapper;

//Slots are different in PE. That's absolutely great! ;/
//This class houses some run of the mill hacky, slashy functions to compensate slots and simulate PC inventory clicking behavior.
//Buyers beware!
public class PEInventory {

	public static int remapClientboundSlot(WindowType windowType, int slot) {
		switch(windowType) {
			case PLAYER: {
				//Hotbar
				if((slot >= 36) && (slot <= 44)) {
					return slot - 36;
				}
			}
		default:
			return slot;
		}
	}
	
	public static int remapServerboundSlot(WindowType windowType, int slot) {
		switch(windowType) {
			case PLAYER: {
				//Hotbar
				if(slot < 9) {
					return slot + 36;
				}
			}
			default: {
				return slot;
			}
		}
	}
	
	public static RecyclableArrayList<ClientBoundPacketData> inventoryContent(ProtocolVersion version, String locale, WindowType windowType, int windowId, ArrayList<ItemStackWrapper> itemstacks) {
		RecyclableArrayList<ClientBoundPacketData> contentpackets = RecyclableArrayList.create();
		switch(windowType) {
			case PLAYER: {
				//ItemStackWrapper[] peInvGrid = new ItemStackWrapper[5];
				//ItemStackWrapper[] peArmor = new ItemStackWrapper[4];
				//ItemStackWrapper[] peInventory = new ItemStackWrapper[36];
				//ItemStackWrapper[] peOffhand = new ItemStackWrapper[1];
				for (int i = 0; i < itemstacks.size(); i++) {
					if(i <= 4) {
						//TODO: COntinue :Blegh:
					}
				}
				break;
			}
			default: {
				contentpackets.add(InventorySetItems.create(version, locale, windowType, windowId, (ItemStackWrapper[]) itemstacks.toArray()));
				break;
			}
		}
		return null;
	}
	
	//Slot thingy numbers.
	public static class PESlot {
		public static final int POCKET_OFFHAND = 119;
		public static final int POCKET_ARMOR_EQUIPMENT = 120;
		public static final int POCKET_CREATIVE_INVENTORY = 121;
		public static final int POCKET_CLICKED_SLOT = 124;
	}

}
