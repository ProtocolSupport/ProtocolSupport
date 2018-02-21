package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleSetHealth;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.typeremapper.pe.PEPacketIDs;
import protocolsupport.utils.recyclable.RecyclableArrayList;
import protocolsupport.utils.recyclable.RecyclableCollection;

public class SetHealth extends MiddleSetHealth {

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData() {
		ProtocolVersion version = connection.getVersion();
		RecyclableArrayList<ClientBoundPacketData> packets = RecyclableArrayList.create();
		double shealth = (health * 20.0F) / cache.getMaxHealth();
		packets.add(EntitySetAttributes.create(version, cache.getSelfPlayerEntityId(), EntitySetAttributes.createAttribute("minecraft:health", shealth)));
		packets.add(EntitySetAttributes.create(version, cache.getSelfPlayerEntityId(), EntitySetAttributes.createAttribute("minecraft:player.hunger", food)));
		packets.add(EntitySetAttributes.create(version, cache.getSelfPlayerEntityId(), EntitySetAttributes.createAttribute("minecraft:player.saturation", saturation)));
		if (health <= 0.0) {
			ClientBoundPacketData respawnpos = ClientBoundPacketData.create(PEPacketIDs.RESPAWN_POS, version);
			respawnpos.writeFloatLE(0);
			respawnpos.writeFloatLE(0);
			respawnpos.writeFloatLE(0);
			packets.add(respawnpos);
		}
		return packets;
	}

}
