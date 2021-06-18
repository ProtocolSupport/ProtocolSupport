package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_8_9r1_9r2_10_11_12r1_12r2_13_14r1_14r2_15_16r1_16r2;

import protocolsupport.protocol.codec.VarNumberCodec;
import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleWorldBorderWarnDistance;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;

public class WorldBorderWarnDistance extends MiddleWorldBorderWarnDistance {

	public WorldBorderWarnDistance(MiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void write() {
		ClientBoundPacketData worldborderPacket = ClientBoundPacketData.create(ClientBoundPacketType.PLAY_WORLDBORDER_WARN_DISTANCE);
		VarNumberCodec.writeVarInt(worldborderPacket, 5); //action (4 - warn distance)
		VarNumberCodec.writeVarInt(worldborderPacket, warnDistance);
		codec.writeClientbound(worldborderPacket);
	}

}
