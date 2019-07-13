package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_4_5_6_7_8_9r1_9r2_10_11_12r1_12r2_13_14;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.ClientBoundPacket;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleInventoryData;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.types.WindowType;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class InventoryData extends MiddleInventoryData {

	public InventoryData(ConnectionImpl connection) {
		super(connection);
	}

	private static final int[] furTypeTr = { 1, 2, 0 };
	private final int[] enchTypeVal = new int[10];

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData() {
		if ((version == ProtocolVersion.MINECRAFT_1_8) && (cache.getWindowCache().getOpenedWindow() == WindowType.ENCHANTMENT)) {
			enchTypeVal[type] = value;
			if ((type >= 7) && (type <= 9)) {
				ClientBoundPacketData serializer = ClientBoundPacketData.create(ClientBoundPacket.PLAY_WINDOW_DATA_ID);
				serializer.writeByte(windowId);
				serializer.writeShort(type - 3);
				serializer.writeShort((value << 8) | enchTypeVal[type - 3]);
				return RecyclableSingletonList.create(serializer);
			} else if ((type >= 4) && (type <= 6)) {
				ClientBoundPacketData serializer = ClientBoundPacketData.create(ClientBoundPacket.PLAY_WINDOW_DATA_ID);
				serializer.writeByte(windowId);
				serializer.writeShort(type);
				serializer.writeShort(((enchTypeVal[type + 3]) << 8) | value);
				return RecyclableSingletonList.create(serializer);
			}
		} else if (version.isBefore(ProtocolVersion.MINECRAFT_1_8) && (cache.getWindowCache().getOpenedWindow() == WindowType.FURNACE)) {
			if (type < furTypeTr.length) {
				type = furTypeTr[type];
			}
		}
		ClientBoundPacketData serializer = ClientBoundPacketData.create(ClientBoundPacket.PLAY_WINDOW_DATA_ID);
		serializer.writeByte(windowId);
		serializer.writeShort(type);
		serializer.writeShort(value);
		return RecyclableSingletonList.create(serializer);
	}

}
