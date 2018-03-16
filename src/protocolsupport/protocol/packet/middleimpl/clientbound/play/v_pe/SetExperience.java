package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleSetExperience;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.utils.types.NetworkEntity;
import protocolsupport.utils.recyclable.RecyclableArrayList;
import protocolsupport.utils.recyclable.RecyclableCollection;

public class SetExperience extends MiddleSetExperience {

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData() {
		RecyclableArrayList<ClientBoundPacketData> packets = RecyclableArrayList.create();
		ProtocolVersion version = connection.getVersion();
		NetworkEntity player = cache.getWatchedEntityCache().getSelfPlayer();
		packets.add(EntitySetAttributes.create(version, player, EntitySetAttributes.createAttribute("minecraft:player.experience", exp)));
		packets.add(EntitySetAttributes.create(version, player, EntitySetAttributes.createAttribute("minecraft:player.level", level)));
		return packets;
	}

}
