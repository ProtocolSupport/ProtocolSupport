package protocolsupport.zmcpe.packetsimpl.clientbound;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleSetHealth;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;
import protocolsupport.zmcpe.packetsimpl.PEPacketIDs;

public class SetHealth extends MiddleSetHealth<RecyclableCollection<ClientBoundPacketData>> {

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData(ProtocolVersion version) {
		ClientBoundPacketData serializer = ClientBoundPacketData.create(PEPacketIDs.SET_HEALTH, version);
		serializer.writeSVarInt((int) Math.ceil((health * 20.0F) / cache.getMaxHealth()));
		return RecyclableSingletonList.create(serializer);
	}

}
