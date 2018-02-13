package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleWorldSound;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.typeremapper.sound.SoundRemapper;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableEmptyList;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class WorldSound extends MiddleWorldSound {

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData() {
		ProtocolVersion version = connection.getVersion();
		String soundname = SoundRemapper.getSoundName(version, id);
		if (soundname == null) {
			return RecyclableEmptyList.get();
		}
		return RecyclableSingletonList.create(WorldCustomSound.create(version, soundname, x, y, z, volume, pitch));
	}

}
