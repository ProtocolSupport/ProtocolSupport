package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddlePlayerAbilities;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.typeremapper.pe.PEAdventureSettings;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class PlayerAbilities extends MiddlePlayerAbilities {

	public PlayerAbilities(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData() {
		cache.getAttributesCache().updatePEFlying((flags & flagOffsetCanFly) == flagOffsetCanFly, (flags & flagOffsetIsFlying) == flagOffsetIsFlying);
		return RecyclableSingletonList.create(PEAdventureSettings.createPacket(cache));
	}

}
