package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_10_11_12r1_12r2_13_14r1_14r2_15_16r1_16r2;

import protocolsupport.protocol.packet.middle.clientbound.play.MiddleWorldSound;
import protocolsupport.protocol.utils.minecraftdata.MinecraftSoundData;

public class WorldSound extends MiddleWorldSound {

	public WorldSound(MiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void write() {
		String soundname = MinecraftSoundData.getNameById(id);
		if (soundname == null) {
			return;
		}
		codec.writeClientbound(WorldCustomSound.create(version, x, y, z, soundname, category, volume, pitch));
	}

}
