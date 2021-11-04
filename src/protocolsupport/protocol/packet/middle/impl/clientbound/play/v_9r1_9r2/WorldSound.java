package protocolsupport.protocol.packet.middle.impl.clientbound.play.v_9r1_9r2;

import protocolsupport.protocol.packet.middle.base.clientbound.play.MiddleWorldSound;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV9r1;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV9r2;
import protocolsupport.protocol.utils.minecraftdata.MinecraftSoundData;

public class WorldSound extends MiddleWorldSound implements
IClientboundMiddlePacketV9r1,
IClientboundMiddlePacketV9r2 {

	public WorldSound(IMiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void write() {
		String soundname = MinecraftSoundData.getNameById(id);
		if (soundname == null) {
			return;
		}
		io.writeClientbound(WorldCustomSound.create(version, x, y, z, soundname, category, volume, pitch));
	}

}
