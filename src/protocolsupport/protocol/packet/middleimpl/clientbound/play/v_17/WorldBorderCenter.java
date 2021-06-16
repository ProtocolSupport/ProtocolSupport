package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_17;

import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleWorldBorderCenter;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;

public class WorldBorderCenter extends MiddleWorldBorderCenter {

	public WorldBorderCenter(MiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void write() {
		ClientBoundPacketData worldborderPacket = ClientBoundPacketData.create(PacketType.CLIENTBOUND_PLAY_WORLDBORDER_CENTER);
		worldborderPacket.writeDouble(x);
		worldborderPacket.writeDouble(z);
		codec.writeClientbound(worldborderPacket);
	}

}
