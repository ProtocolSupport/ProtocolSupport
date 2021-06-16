package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_17;

import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleWorldBorderWarnDelay;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.VarNumberSerializer;

public class WorldBorderWarnDelay extends MiddleWorldBorderWarnDelay {

	public WorldBorderWarnDelay(MiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void write() {
		ClientBoundPacketData worldborderPacket = ClientBoundPacketData.create(ClientBoundPacketType.PLAY_WORLDBORDER_WARN_DELAY);
		VarNumberSerializer.writeVarInt(worldborderPacket, warnDelay);
		codec.writeClientbound(worldborderPacket);
	}

}
