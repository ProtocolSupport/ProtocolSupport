package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_4_5_6_7_8;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleWorldSound;
import protocolsupport.protocol.typeremapper.basic.SoundRemapper;

public class WorldSound extends MiddleWorldSound {

	public WorldSound(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	protected void writeToClient() {
		String soundname = SoundRemapper.getSoundName(version, id);
		if (soundname != null) {
			return;
		}
		codec.write(WorldCustomSound.create(version, x, y, z, soundname, volume, pitch));
	}

}
