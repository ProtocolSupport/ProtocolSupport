package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe;

import protocolsupport.protocol.packet.middle.clientbound.play.MiddlePlayerAbilities;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.typeremapper.pe.PEAdventureSettings;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class PlayerAbilities extends MiddlePlayerAbilities {

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData() {
		return RecyclableSingletonList.create(PEAdventureSettings.createPacket(cache));
	}

}
