package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_9r1_9r2;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_4_5_6_7_8_9r1_9r2_10_11_12r1_12r2_13.AbstractWorldEntitySound;

public class EntitySound extends AbstractWorldEntitySound {

	public EntitySound(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	protected void writeWorldSound(String sound, double x, double y, double z) {
		codec.write(WorldCustomSound.create(version, x, y, z, sound, category, volume, pitch));
	}

}
