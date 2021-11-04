package protocolsupport.protocol.packet.middle.impl.clientbound.play.v_17r1_17r2;

import protocolsupport.protocol.packet.ClientBoundPacketData;
import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middle.base.clientbound.play.MiddleWorldBorderCenter;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV17r1;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV17r2;

public class WorldBorderCenter extends MiddleWorldBorderCenter implements
IClientboundMiddlePacketV17r1,
IClientboundMiddlePacketV17r2 {

	public WorldBorderCenter(IMiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void write() {
		ClientBoundPacketData worldborderPacket = ClientBoundPacketData.create(ClientBoundPacketType.PLAY_WORLDBORDER_CENTER);
		worldborderPacket.writeDouble(x);
		worldborderPacket.writeDouble(z);
		io.writeClientbound(worldborderPacket);
	}

}
