package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_1_4__1_5__1_6__1_7__1_8;

import java.io.IOException;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.ClientBoundPacket;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleInventoryData;
import protocolsupport.protocol.packet.middleimpl.PacketData;
import protocolsupport.protocol.storage.SharedStorage.WindowType;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class InventoryData extends MiddleInventoryData<RecyclableCollection<PacketData>> {

	private static final int[] furTypeTr = {1, 2, 0};
	private final int[] enchTypeVal = new int[10];

	@Override
	public RecyclableCollection<PacketData> toData(ProtocolVersion version) throws IOException {
		if (version == ProtocolVersion.MINECRAFT_1_8 && sharedstorage.getOpenedWindow() == WindowType.ENCHANT) {
			enchTypeVal[type] = value;
			if (type >= 7 && type <= 9) {
				PacketData serializer = PacketData.create(ClientBoundPacket.PLAY_WINDOW_DATA_ID, version);
				serializer.writeByte(windowId);
				serializer.writeShort(type - 3);
				serializer.writeShort((value << 8) | enchTypeVal[type - 3]);
				return RecyclableSingletonList.<PacketData>create(serializer);
			} else if (type >= 4 && type <= 6) {
				PacketData serializer = PacketData.create(ClientBoundPacket.PLAY_WINDOW_DATA_ID, version);
				serializer.writeByte(windowId);
				serializer.writeShort(type);
				serializer.writeShort(((enchTypeVal[type + 3]) << 8) | value);
				return RecyclableSingletonList.<PacketData>create(serializer);
			}
		} else if (version.isBefore(ProtocolVersion.MINECRAFT_1_8) && sharedstorage.getOpenedWindow() == WindowType.FURNACE) {
			if (type < furTypeTr.length) {
				type = furTypeTr[type];
			}
		}
		PacketData serializer = PacketData.create(ClientBoundPacket.PLAY_WINDOW_DATA_ID, version);
		serializer.writeByte(windowId);
		serializer.writeShort(type);
		serializer.writeShort(value);
		return RecyclableSingletonList.<PacketData>create(serializer);
	}

}
