package protocolsupport.protocol.packet.middle.base.serverbound.play;

import protocolsupport.protocol.codec.PositionCodec;
import protocolsupport.protocol.codec.VarNumberCodec;
import protocolsupport.protocol.packet.ServerBoundPacketData;
import protocolsupport.protocol.packet.ServerBoundPacketType;
import protocolsupport.protocol.packet.middle.base.serverbound.ServerBoundMiddlePacket;
import protocolsupport.protocol.types.Position;

public abstract class MiddleJigsawGenerate extends ServerBoundMiddlePacket {

	protected MiddleJigsawGenerate(IMiddlePacketInit init) {
		super(init);
	}

	protected final Position position = new Position(0, 0, 0);
	protected int levels;
	protected boolean keep;

	@Override
	protected void write() {
		ServerBoundPacketData jigsawgenerate = ServerBoundPacketData.create(ServerBoundPacketType.PLAY_JIGSAW_GENERATE);
		PositionCodec.writePosition(jigsawgenerate, position);
		VarNumberCodec.writeVarInt(jigsawgenerate, levels);
		jigsawgenerate.writeBoolean(keep);
		io.writeServerbound(jigsawgenerate);
	}

}
