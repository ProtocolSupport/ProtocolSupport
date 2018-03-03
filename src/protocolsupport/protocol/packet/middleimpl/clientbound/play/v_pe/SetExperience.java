package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleSetExperience;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.utils.recyclable.RecyclableArrayList;
import protocolsupport.utils.recyclable.RecyclableCollection;

public class SetExperience extends MiddleSetExperience {

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData() {
		ProtocolVersion version = connection.getVersion();
		RecyclableArrayList<ClientBoundPacketData> packets = RecyclableArrayList.create();
		int playerEntityId = cache.getWatchedEntityCache().getSelfPlayerEntityId();
		packets.add(EntitySetAttributes.create(version, playerEntityId, EntitySetAttributes.createAttribute("minecraft:player.experience", exp)));
		packets.add(EntitySetAttributes.create(version, playerEntityId, EntitySetAttributes.createAttribute("minecraft:player.level", level)));
		return packets;
	}

}
