package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_8_9r1_9r2_10_11_12r1_12r2_13_14r1_14r2_15_16r1_16r2;

import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleWorldBorderLerpSize;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.VarNumberSerializer;

public class WorldBorderLerpSize extends MiddleWorldBorderLerpSize {

	public WorldBorderLerpSize(MiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void write() {
		ClientBoundPacketData worldborderPacket = ClientBoundPacketData.create(ClientBoundPacketType.PLAY_WORLDBORDER_LERP_SIZE);
		VarNumberSerializer.writeVarInt(worldborderPacket, 1); //action (1 - lerp size)
		worldborderPacket.writeDouble(oldSize);
		worldborderPacket.writeDouble(newSize);
		VarNumberSerializer.writeVarLong(worldborderPacket, speed);
		codec.writeClientbound(worldborderPacket);
	}

}
