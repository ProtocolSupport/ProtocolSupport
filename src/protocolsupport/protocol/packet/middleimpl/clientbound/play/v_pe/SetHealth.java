package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleSetHealth;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe.EntitySetAttributes.AttributeInfo;
import protocolsupport.protocol.typeremapper.pe.PEPacketIDs;
import protocolsupport.utils.ObjectFloatTuple;
import protocolsupport.utils.recyclable.RecyclableArrayList;
import protocolsupport.utils.recyclable.RecyclableCollection;

public class SetHealth extends MiddleSetHealth {

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData() {
		ProtocolVersion version = connection.getVersion();
		RecyclableArrayList<ClientBoundPacketData> packets = RecyclableArrayList.create();
		packets.add(EntitySetAttributes.create(
			version, cache.getWatchedEntityCache().getSelfPlayer(),
			new ObjectFloatTuple<>(AttributeInfo.HEALTH, health),
			new ObjectFloatTuple<>(AttributeInfo.PLAYER_HUNGER, food),
			new ObjectFloatTuple<>(AttributeInfo.PLAYER_SATURATION, saturation)
		));
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
