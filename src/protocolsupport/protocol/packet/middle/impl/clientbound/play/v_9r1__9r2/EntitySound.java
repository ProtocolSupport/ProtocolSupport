package protocolsupport.protocol.packet.middle.impl.clientbound.play.v_9r1__9r2;

import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV9r1;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV9r2;
import protocolsupport.protocol.packet.middle.impl.clientbound.play.v_4__13.AbstractWorldEntitySound;

public class EntitySound extends AbstractWorldEntitySound implements
IClientboundMiddlePacketV9r1,
IClientboundMiddlePacketV9r2 {

	public EntitySound(IMiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void writeWorldSound(String sound, double x, double y, double z) {
		io.writeClientbound(WorldSound.createCustomSound(version, x, y, z, sound, category, volume, pitch));
	}

}
