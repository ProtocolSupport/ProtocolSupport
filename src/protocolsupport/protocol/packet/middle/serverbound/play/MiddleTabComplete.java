package protocolsupport.protocol.packet.middle.serverbound.play;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middle.ServerBoundMiddlePacket;
import protocolsupport.protocol.packet.middleimpl.ServerBoundPacketData;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;

public abstract class MiddleTabComplete extends ServerBoundMiddlePacket {

	public MiddleTabComplete(ConnectionImpl connection) {
		super(connection);
	}

	protected int id;
	protected String string;

	@Override
	public void writeToServer() {
		ServerBoundPacketData tabcomplete = ServerBoundPacketData.create(PacketType.SERVERBOUND_PLAY_TAB_COMPLETE);
		VarNumberSerializer.writeVarInt(tabcomplete, id);
		StringSerializer.writeVarIntUTF8String(tabcomplete, string);
		codec.read(tabcomplete);
	}

}
