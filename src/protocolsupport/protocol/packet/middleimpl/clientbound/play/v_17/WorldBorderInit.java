package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_17;

import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleWorldBorderInit;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.VarNumberSerializer;

public class WorldBorderInit extends MiddleWorldBorderInit {

	public WorldBorderInit(MiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void write() {
		ClientBoundPacketData worldborderPacket = ClientBoundPacketData.create(PacketType.CLIENTBOUND_PLAY_WORLDBORDER_INIT);
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
