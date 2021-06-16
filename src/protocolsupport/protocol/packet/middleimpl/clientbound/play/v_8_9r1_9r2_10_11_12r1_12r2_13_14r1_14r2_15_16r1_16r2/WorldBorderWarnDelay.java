package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_8_9r1_9r2_10_11_12r1_12r2_13_14r1_14r2_15_16r1_16r2;

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
		ClientBoundPacketData worldborderPacket = ClientBoundPacketData.create(ClientBoundPacketType.CLIENTBOUND_PLAY_WORLDBORDER_WARN_DELAY);
		VarNumberSerializer.writeVarInt(worldborderPacket, 4); //action (4 - warn delay)
		VarNumberSerializer.writeVarInt(worldborderPacket, warnDelay);
		codec.writeClientbound(worldborderPacket);
	}

}
