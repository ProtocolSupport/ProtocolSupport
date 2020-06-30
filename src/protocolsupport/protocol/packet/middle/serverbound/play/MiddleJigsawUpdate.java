package protocolsupport.protocol.packet.middle.serverbound.play;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middle.ServerBoundMiddlePacket;
import protocolsupport.protocol.packet.middleimpl.ServerBoundPacketData;
import protocolsupport.protocol.serializer.PositionSerializer;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.types.Position;

public abstract class MiddleJigsawUpdate extends ServerBoundMiddlePacket {

	public MiddleJigsawUpdate(ConnectionImpl connection) {
		super(connection);
	}

	protected final Position position = new Position(0, 0, 0);
	protected String name;
	protected String target;
	protected String pool;
	protected String finalState;
	protected String jointType;

	@Override
	protected void writeToServer() {
		ServerBoundPacketData jigsawupdate = ServerBoundPacketData.create(PacketType.SERVERBOUND_PLAY_JIGSAW_UPDATE);
		PositionSerializer.writePosition(jigsawupdate, position);
		StringSerializer.writeVarIntUTF8String(jigsawupdate, name);
		StringSerializer.writeVarIntUTF8String(jigsawupdate, target);
		StringSerializer.writeVarIntUTF8String(jigsawupdate, pool);
		StringSerializer.writeVarIntUTF8String(jigsawupdate, finalState);
		StringSerializer.writeVarIntUTF8String(jigsawupdate, jointType);
		codec.read(jigsawupdate);
	}

}
