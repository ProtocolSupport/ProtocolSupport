package protocolsupport.protocol.packet.middle.impl.clientbound.play.v_9r1_9r2;

import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV9r1;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV9r2;
import protocolsupport.protocol.packet.middle.impl.clientbound.play.v_4_5_6_7_8_9r1_9r2_10_11_12r1_12r2_13.AbstractWorldEntitySound;

public class EntitySound extends AbstractWorldEntitySound implements
IClientboundMiddlePacketV9r1,
IClientboundMiddlePacketV9r2 {

	public EntitySound(IMiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void writeWorldSound(String sound, double x, double y, double z) {
		io.writeClientbound(WorldCustomSound.create(version, x, y, z, sound, category, volume, pitch));
	}

}
