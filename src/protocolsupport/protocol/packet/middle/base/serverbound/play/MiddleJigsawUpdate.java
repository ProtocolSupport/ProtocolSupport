package protocolsupport.protocol.packet.middle.base.serverbound.play;

import protocolsupport.protocol.codec.PositionCodec;
import protocolsupport.protocol.codec.StringCodec;
import protocolsupport.protocol.packet.ServerBoundPacketData;
import protocolsupport.protocol.packet.ServerBoundPacketType;
import protocolsupport.protocol.packet.middle.base.serverbound.ServerBoundMiddlePacket;
import protocolsupport.protocol.types.Position;

public abstract class MiddleJigsawUpdate extends ServerBoundMiddlePacket {

	protected MiddleJigsawUpdate(IMiddlePacketInit init) {
		super(init);
	}

	protected final Position position = new Position(0, 0, 0);
	protected String name;
	protected String target;
	protected String pool;
	protected String finalState;
	protected String jointType;

	@Override
	protected void write() {
		ServerBoundPacketData jigsawupdate = ServerBoundPacketData.create(ServerBoundPacketType.PLAY_JIGSAW_UPDATE);
		PositionCodec.writePosition(jigsawupdate, position);
		StringCodec.writeVarIntUTF8String(jigsawupdate, name);
		StringCodec.writeVarIntUTF8String(jigsawupdate, target);
		StringCodec.writeVarIntUTF8String(jigsawupdate, pool);
		StringCodec.writeVarIntUTF8String(jigsawupdate, finalState);
		StringCodec.writeVarIntUTF8String(jigsawupdate, jointType);
		io.writeServerbound(jigsawupdate);
	}

}
