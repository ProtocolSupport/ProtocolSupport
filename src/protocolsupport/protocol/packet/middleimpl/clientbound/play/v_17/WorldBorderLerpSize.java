package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_17;

import protocolsupport.protocol.codec.VarNumberCodec;
import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleWorldBorderLerpSize;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;

public class WorldBorderLerpSize extends MiddleWorldBorderLerpSize {

	public WorldBorderLerpSize(MiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void write() {
		ClientBoundPacketData worldborderPacket = ClientBoundPacketData.create(ClientBoundPacketType.PLAY_WORLDBORDER_LERP_SIZE);
		worldborderPacket.writeDouble(oldSize);
		worldborderPacket.writeDouble(newSize);
		VarNumberCodec.writeVarLong(worldborderPacket, speed);
		codec.writeClientbound(worldborderPacket);
	}

}
