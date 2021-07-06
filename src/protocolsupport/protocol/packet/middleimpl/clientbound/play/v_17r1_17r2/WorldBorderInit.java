package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_17r1_17r2;

import protocolsupport.protocol.codec.VarNumberCodec;
import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleWorldBorderInit;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;

public class WorldBorderInit extends MiddleWorldBorderInit {

	public WorldBorderInit(MiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void write() {
		ClientBoundPacketData worldborderPacket = ClientBoundPacketData.create(ClientBoundPacketType.PLAY_WORLDBORDER_INIT);
		worldborderPacket.writeDouble(x);
		worldborderPacket.writeDouble(z);
		worldborderPacket.writeDouble(oldSize);
		worldborderPacket.writeDouble(newSize);
		VarNumberCodec.writeVarLong(worldborderPacket, speed);
		VarNumberCodec.writeVarInt(worldborderPacket, teleportBound);
		VarNumberCodec.writeVarInt(worldborderPacket, warnDelay);
		VarNumberCodec.writeVarInt(worldborderPacket, warnDistance);
		codec.writeClientbound(worldborderPacket);
	}

}
