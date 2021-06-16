package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_17;

import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleWorldBorderSize;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;

public class WorldBorderSize extends MiddleWorldBorderSize {

	public WorldBorderSize(MiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void write() {
		ClientBoundPacketData worldborderPacket = ClientBoundPacketData.create(PacketType.CLIENTBOUND_PLAY_WORLDBORDER_SIZE);
		worldborderPacket.writeDouble(size);
		codec.writeClientbound(worldborderPacket);
	}

}
