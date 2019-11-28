package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_14r1_14r2;

import protocolsupport.api.chat.ChatAPI;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleInventoryOpen;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.MiscSerializer;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;

public class InventoryOpen extends MiddleInventoryOpen {

	public InventoryOpen(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	protected void writeToClient0() {
		ClientBoundPacketData windowopen = ClientBoundPacketData.create(PacketType.CLIENTBOUND_PLAY_WINDOW_OPEN);
		VarNumberSerializer.writeVarInt(windowopen, windowId);
		MiscSerializer.writeVarIntEnum(windowopen, windowRemapper.toClientWindowType(type));
		StringSerializer.writeVarIntUTF8String(windowopen, ChatAPI.toJSON(title));
		codec.write(windowopen);
	}

}
