package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_4_5_6_7_8_9r1_9r2_10_11_12r1_12r2_13_14;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.ClientBoundPacket;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleInventorySetItems;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.ItemStackSerializer;
import protocolsupport.protocol.typeremapper.basic.WindowSlotsRemappingHelper;
import protocolsupport.protocol.types.NetworkItemStack;
import protocolsupport.protocol.types.WindowType;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class InventorySetItems extends MiddleInventorySetItems {

	public InventorySetItems(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData() {
		WindowType window = windowId == WINDOW_ID_PLAYER_INVENTORY ? WindowType.PLAYER : cache.getWindowCache().getOpenedWindow();
		switch (window) {
			case PLAYER: {
				if (!WindowSlotsRemappingHelper.hasPlayerOffhandSlot(version)) {
					itemstacks.remove(WindowSlotsRemappingHelper.PLAYER_OFF_HAND_SLOT);
				}
				break;
			}
			case BREWING_STAND: {
				if (!WindowSlotsRemappingHelper.hasBrewingBlazePowderSlot(version)) {
					itemstacks.remove(WindowSlotsRemappingHelper.BREWING_BLAZE_POWDER_SLOT);
				}
				break;
			}
			case ENCHANTMENT: {
				if (!WindowSlotsRemappingHelper.hasEnchantLapisSlot(version)) {
					itemstacks.remove(WindowSlotsRemappingHelper.ENCHANT_LAPIS_SLOT);
				}
				break;
			}
			default: {
				break;
			}
		}
		ClientBoundPacketData serializer = ClientBoundPacketData.create(ClientBoundPacket.PLAY_WINDOW_SET_ITEMS_ID);
		serializer.writeByte(windowId);
		serializer.writeShort(itemstacks.size());
		for (NetworkItemStack itemstack : itemstacks) {
			ItemStackSerializer.writeItemStack(serializer, version, cache.getAttributesCache().getLocale(), itemstack);
		}
		return RecyclableSingletonList.create(serializer);
	}

}
