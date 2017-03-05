package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.PEPacketIDs;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleSetHealth;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.MiscSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.utils.recyclable.RecyclableArrayList;
import protocolsupport.utils.recyclable.RecyclableCollection;

public class SetHealth extends MiddleSetHealth {

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData(ProtocolVersion version) {
		RecyclableArrayList<ClientBoundPacketData> packets = RecyclableArrayList.create();
		ClientBoundPacketData sethealth = ClientBoundPacketData.create(PEPacketIDs.SET_HEALTH, version);
		VarNumberSerializer.writeSVarInt(sethealth, (int) Math.ceil(health));
		packets.add(sethealth);
		if (health <= 0.0) {
			ClientBoundPacketData respawnpos = ClientBoundPacketData.create(PEPacketIDs.RESPAWN_POS, version);
			MiscSerializer.writeLFloat(respawnpos, 0);
			MiscSerializer.writeLFloat(respawnpos, 0);
			MiscSerializer.writeLFloat(respawnpos, 0);
			packets.add(respawnpos);
		}
		return packets;
	}

}
