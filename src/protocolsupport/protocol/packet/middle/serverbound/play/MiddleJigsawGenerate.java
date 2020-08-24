package protocolsupport.protocol.packet.middle.serverbound.play;

import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middle.ServerBoundMiddlePacket;
import protocolsupport.protocol.packet.middleimpl.ServerBoundPacketData;
import protocolsupport.protocol.serializer.PositionSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.types.Position;

public abstract class MiddleJigsawGenerate extends ServerBoundMiddlePacket {

	public MiddleJigsawGenerate(MiddlePacketInit init) {
		super(init);
	}

	protected final Position position = new Position(0, 0, 0);
	protected int levels;
	protected boolean keep;

	@Override
	protected void writeToServer() {
		ServerBoundPacketData jigsawgenerate = ServerBoundPacketData.create(PacketType.SERVERBOUND_PLAY_JIGSAW_GENERATE);
		PositionSerializer.writePosition(jigsawgenerate, position);
		VarNumberSerializer.writeVarInt(jigsawgenerate, levels);
		jigsawgenerate.writeBoolean(keep);
		codec.read(jigsawgenerate);
	}

}
