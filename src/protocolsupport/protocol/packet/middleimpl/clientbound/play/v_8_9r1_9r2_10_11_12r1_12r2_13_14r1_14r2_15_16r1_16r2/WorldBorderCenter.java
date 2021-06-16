package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_8_9r1_9r2_10_11_12r1_12r2_13_14r1_14r2_15_16r1_16r2;

import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleWorldBorderCenter;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.VarNumberSerializer;

public class WorldBorderCenter extends MiddleWorldBorderCenter {

	public WorldBorderCenter(MiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void write() {
		ClientBoundPacketData worldborderPacket = ClientBoundPacketData.create(ClientBoundPacketType.CLIENTBOUND_PLAY_WORLDBORDER_CENTER);
		VarNumberSerializer.writeVarInt(worldborderPacket, 2); //action (2 - center)
		worldborderPacket.writeDouble(x);
		worldborderPacket.writeDouble(z);
		codec.writeClientbound(worldborderPacket);
	}

}
