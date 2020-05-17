package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_9r1_9r2;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleWorldSound;
import protocolsupport.protocol.typeremapper.basic.SoundRemapper;

public class WorldSound extends MiddleWorldSound {

	public WorldSound(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	public void writeToClient() {
		String soundname = SoundRemapper.getSoundName(version, id);
		if (soundname == null) {
			return;
		}
		codec.write(WorldCustomSound.create(version, x, y, z, soundname, category, volume, pitch));
	}

}
