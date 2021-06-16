package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_8_9r1_9r2_10_11_12r1_12r2_13_14r1_14r2_15_16r1_16r2;

import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleWorldBorderInit;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.VarNumberSerializer;

public class WorldBorderInit extends MiddleWorldBorderInit {

	public WorldBorderInit(MiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void write() {
		ClientBoundPacketData worldborderPacket = ClientBoundPacketData.create(ClientBoundPacketType.PLAY_WORLDBORDER_INIT);
		VarNumberSerializer.writeVarInt(worldborderPacket, 3); //action (3 - init)
		worldborderPacket.writeDouble(x);
		worldborderPacket.writeDouble(z);
		worldborderPacket.writeDouble(oldSize);
		worldborderPacket.writeDouble(newSize);
		VarNumberSerializer.writeVarLong(worldborderPacket, speed);
		VarNumberSerializer.writeVarInt(worldborderPacket, teleportBound);
		VarNumberSerializer.writeVarInt(worldborderPacket, warnDelay);
		VarNumberSerializer.writeVarInt(worldborderPacket, warnDistance);
		codec.writeClientbound(worldborderPacket);
	}

}
