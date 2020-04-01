package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_beta;

import java.io.IOException;

import io.netty.buffer.ByteBufOutputStream;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleInventoryOpen;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.typeremapper.legacy.LegacyWindowType;
import protocolsupport.protocol.typeremapper.legacy.LegacyWindowType.LegacyWindowData;
import protocolsupport.protocol.typeremapper.legacy.chat.LegacyChat;

public class InventoryOpen extends MiddleInventoryOpen {

	public InventoryOpen(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	protected void writeToClient0() {
		LegacyWindowData wdata = LegacyWindowType.getData(windowRemapper.toClientWindowType(type));
		ClientBoundPacketData windowopen = ClientBoundPacketData.create(PacketType.CLIENTBOUND_PLAY_WINDOW_OPEN);
		windowopen.writeByte(windowId);
		windowopen.writeByte(wdata.getIntId());
		try (ByteBufOutputStream stream = new ByteBufOutputStream(windowopen)) {
			stream.writeUTF(LegacyChat.clampLegacyText(title.toLegacyText(cache.getAttributesCache().getLocale()), 32));
		} catch (IOException e) {
			throw new RuntimeException("String write error", e);
		}
		windowopen.writeByte(windowRemapper.toClientSlots(0));
		codec.write(windowopen);
	}

}
