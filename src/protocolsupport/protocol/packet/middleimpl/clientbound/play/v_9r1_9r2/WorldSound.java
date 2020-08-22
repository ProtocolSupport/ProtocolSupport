package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_9r1_9r2;

import protocolsupport.protocol.packet.middle.clientbound.play.MiddleWorldSound;
import protocolsupport.protocol.utils.minecraftdata.MinecraftSoundData;

public class WorldSound extends MiddleWorldSound {

	public WorldSound(MiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void writeToClient() {
		String soundname = MinecraftSoundData.getNameById(id);
		if (soundname == null) {
			return;
		}
		codec.write(WorldCustomSound.create(version, x, y, z, soundname, category, volume, pitch));
	}

}
