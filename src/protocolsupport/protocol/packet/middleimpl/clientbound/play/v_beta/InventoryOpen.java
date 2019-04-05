package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_beta;

import java.io.IOException;

import io.netty.buffer.ByteBufOutputStream;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.ClientBoundPacket;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleInventoryOpen;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.typeremapper.basic.GenericIdRemapper;
import protocolsupport.protocol.typeremapper.legacy.chat.LegacyChat;
import protocolsupport.protocol.typeremapper.utils.RemappingTable.EnumRemappingTable;
import protocolsupport.protocol.utils.types.WindowType;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class InventoryOpen extends MiddleInventoryOpen {

	protected final EnumRemappingTable<WindowType> typeRemapper = GenericIdRemapper.INVENTORY.getTable(version);

	public InventoryOpen(ConnectionImpl connection) {
		super(connection);
	}

	@SuppressWarnings("resource")
	@Override
	public RecyclableCollection<ClientBoundPacketData> toData() {
		ClientBoundPacketData serializer = ClientBoundPacketData.create(ClientBoundPacket.PLAY_WINDOW_OPEN_ID);
		serializer.writeByte(windowId);
		serializer.writeByte(typeRemapper.getRemap(type).toLegacyId());
		try {
			new ByteBufOutputStream(serializer).writeUTF(LegacyChat.clampLegacyText(title.toLegacyText(cache.getAttributesCache().getLocale()), 32));
		} catch (IOException e) {
			throw new RuntimeException("String write error", e);
		}
		serializer.writeByte(slots);
		return RecyclableSingletonList.create(serializer);
	}

}
