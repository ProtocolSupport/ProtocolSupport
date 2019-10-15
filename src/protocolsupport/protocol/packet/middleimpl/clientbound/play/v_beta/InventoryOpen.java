package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_beta;

import java.io.IOException;

import io.netty.buffer.ByteBufOutputStream;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleInventoryOpen;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.packet.middleimpl.IPacketData;
import protocolsupport.protocol.typeremapper.legacy.LegacyWindowType;
import protocolsupport.protocol.typeremapper.legacy.LegacyWindowType.LegacyWindowData;
import protocolsupport.protocol.typeremapper.legacy.chat.LegacyChat;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class InventoryOpen extends MiddleInventoryOpen {

	public InventoryOpen(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	public RecyclableCollection<? extends IPacketData> toData0() {
		LegacyWindowData wdata = LegacyWindowType.getData(windowRemapper.toClientWindowType(type));
		ClientBoundPacketData serializer = ClientBoundPacketData.create(PacketType.CLIENTBOUND_PLAY_WINDOW_OPEN);
		serializer.writeByte(windowId);
		serializer.writeByte(wdata.getIntId());
		try (ByteBufOutputStream stream = new ByteBufOutputStream(serializer)) {
			stream.writeUTF(LegacyChat.clampLegacyText(title.toLegacyText(cache.getAttributesCache().getLocale()), 32));
		} catch (IOException e) {
			throw new RuntimeException("String write error", e);
		}
		serializer.writeByte(windowRemapper.toClientSlots(0));
		return RecyclableSingletonList.create(serializer);
	}

}
