package protocolsupport.zmcpe.packetsimpl.clientbound;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleSetHealth;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.utils.recyclable.RecyclableArrayList;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.zmcpe.packetsimpl.PEPacketIDs;

public class SetHealth extends MiddleSetHealth<RecyclableCollection<ClientBoundPacketData>> {

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData(ProtocolVersion version) {
		RecyclableArrayList<ClientBoundPacketData> packets = RecyclableArrayList.create();
		ClientBoundPacketData sethealth = ClientBoundPacketData.create(PEPacketIDs.SET_HEALTH, version);
		sethealth.writeSVarInt((int) Math.ceil(health));
		packets.add(sethealth);
		if (health <= 0.0) {
			ClientBoundPacketData respawnpos = ClientBoundPacketData.create(PEPacketIDs.RESPAWN_POS, version);
			respawnpos.writeLFloat(0);
			respawnpos.writeLFloat(0);
			respawnpos.writeLFloat(0);
			packets.add(respawnpos);
		}
		return packets;
	}

}
