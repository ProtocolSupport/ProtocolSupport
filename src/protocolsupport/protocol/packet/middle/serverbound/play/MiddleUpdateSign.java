package protocolsupport.protocol.packet.middle.serverbound.play;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middle.ServerBoundMiddlePacket;
import protocolsupport.protocol.packet.middleimpl.ServerBoundPacketData;
import protocolsupport.protocol.serializer.PositionSerializer;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.types.Position;

public abstract class MiddleUpdateSign extends ServerBoundMiddlePacket {

	public MiddleUpdateSign(ConnectionImpl connection) {
		super(connection);
	}

	protected final Position position = new Position(0, 0, 0);
	protected String[] lines = new String[4];

	@Override
	protected void writeToServer() {
		codec.read(create(position, lines));
	}

	public static ServerBoundPacketData create(Position position, String[] lines) {
		ServerBoundPacketData creator = ServerBoundPacketData.create(PacketType.SERVERBOUND_PLAY_UPDATE_SIGN);
		PositionSerializer.writePosition(creator, position);
		for (int i = 0; i < lines.length; i++) {
			StringSerializer.writeVarIntUTF8String(creator, lines[i]);
		}
		return creator;
	}

}
