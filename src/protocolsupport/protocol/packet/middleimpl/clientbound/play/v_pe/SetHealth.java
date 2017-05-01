package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.legacyremapper.pe.PEPacketIDs;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleSetHealth;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.MiscSerializer;
import protocolsupport.utils.recyclable.RecyclableArrayList;
import protocolsupport.utils.recyclable.RecyclableCollection;

public class SetHealth extends MiddleSetHealth {

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData(ProtocolVersion version) {
		RecyclableArrayList<ClientBoundPacketData> packets = RecyclableArrayList.create();
		double shealth = (health * 20.0F) / cache.getMaxHealth();
		packets.add(EntitySetAttributes.create(version, cache.getSelfPlayerEntityId(), EntitySetAttributes.createAttribute("minecraft:health", shealth)));
		if (shealth <= 0.0) {
			ClientBoundPacketData respawnpos = ClientBoundPacketData.create(PEPacketIDs.RESPAWN_POS, version);
			MiscSerializer.writeLFloat(respawnpos, 0);
			MiscSerializer.writeLFloat(respawnpos, 0);
			MiscSerializer.writeLFloat(respawnpos, 0);
			packets.add(respawnpos);
		}
		packets.add(EntitySetAttributes.create(version, cache.getSelfPlayerEntityId(), EntitySetAttributes.createAttribute("minecraft:player.hunger", food)));
		packets.add(EntitySetAttributes.create(version, cache.getSelfPlayerEntityId(), EntitySetAttributes.createAttribute("minecraft:player.saturation", saturation)));
		return packets;
	}

}
