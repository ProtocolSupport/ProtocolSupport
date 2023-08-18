package protocolsupport.protocol.packet.middle.impl.clientbound.play.v_10__13;

import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV10;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV11;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV12r1;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV12r2;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV13;
import protocolsupport.protocol.packet.middle.impl.clientbound.play.v_10__18.WorldSound;
import protocolsupport.protocol.packet.middle.impl.clientbound.play.v_4__13.AbstractWorldEntitySound;

public class EntitySound extends AbstractWorldEntitySound implements
IClientboundMiddlePacketV10,
IClientboundMiddlePacketV11,
IClientboundMiddlePacketV12r1,
IClientboundMiddlePacketV12r2,
IClientboundMiddlePacketV13 {

	public EntitySound(IMiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void writeWorldSound(String sound, double x, double y, double z) {
		io.writeClientbound(WorldSound.createCustomSound(version, x, y, z, sound, category, volume, pitch));
	}

}
