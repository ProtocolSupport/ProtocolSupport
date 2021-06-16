package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_17;

import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleWorldBorderWarnDistance;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.VarNumberSerializer;

public class WorldBorderWarnDistance extends MiddleWorldBorderWarnDistance {

	public WorldBorderWarnDistance(MiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void write() {
		ClientBoundPacketData worldborderPacket = ClientBoundPacketData.create(ClientBoundPacketType.PLAY_WORLDBORDER_WARN_DISTANCE);
		VarNumberSerializer.writeVarInt(worldborderPacket, warnDistance);
		codec.writeClientbound(worldborderPacket);
	}

}
