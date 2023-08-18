package protocolsupport.protocol.packet.middle.impl.clientbound.play.v_4__8;

import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV4;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV5;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV6;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV7;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV8;
import protocolsupport.protocol.packet.middle.impl.clientbound.play.v_4__13.AbstractWorldEntitySound;

public class EntitySound extends AbstractWorldEntitySound implements
IClientboundMiddlePacketV4,
IClientboundMiddlePacketV5,
IClientboundMiddlePacketV6,
IClientboundMiddlePacketV7,
IClientboundMiddlePacketV8 {

	public EntitySound(IMiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void writeWorldSound(String sound, double x, double y, double z) {
		io.writeClientbound(WorldSound.createCustomSound(version, x, y, z, sound, volume, pitch));
	}

}
