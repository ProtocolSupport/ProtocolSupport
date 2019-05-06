package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_4_5_6_7_8_9r1_9r2_10_11_12r1_12r2_13_14;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddleInventoryClick;
import protocolsupport.protocol.serializer.ItemStackSerializer;
import protocolsupport.protocol.typeremapper.basic.WindowSlotsRemappingHelper;
import protocolsupport.protocol.types.NetworkItemStack;

public class InventoryClick extends MiddleInventoryClick {

	public InventoryClick(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	public void readFromClientData(ByteBuf clientdata) {
		windowId = clientdata.readUnsignedByte();
		slot = clientdata.readShort();
		switch (cache.getWindowCache().getOpenedWindow()) {
			case BREWING_STAND: {
				if (!WindowSlotsRemappingHelper.hasBrewingBlazePowderSlot(version) && (slot >= WindowSlotsRemappingHelper.BREWING_BLAZE_POWDER_SLOT)) {
					slot++;
				}
				break;
			}
			case ENCHANTMENT: {
				if (!WindowSlotsRemappingHelper.hasEnchantLapisSlot(version) && (slot >= WindowSlotsRemappingHelper.ENCHANT_LAPIS_SLOT)) {
					slot++;
				}
				break;
			}
			default: {
				break;
			}
		}
		button = clientdata.readUnsignedByte();
		actionNumber = clientdata.readShort();
		mode = clientdata.readUnsignedByte();
		itemstack = ItemStackSerializer.readItemStack(clientdata, version, cache.getAttributesCache().getLocale());
		if ((button == 0) && (mode == 1)) {
			itemstack = NetworkItemStack.NULL;
		}
	}

}
