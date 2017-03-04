package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_1_11;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.ClientBoundPacket;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleCollectEffect;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class CollectEffect extends MiddleCollectEffect {

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData(ProtocolVersion version) {
		ClientBoundPacketData serializer = ClientBoundPacketData.create(ClientBoundPacket.PLAY_COLLECT_EFFECT_ID, version);
		serializer.writeVarInt(entityId);
		serializer.writeVarInt(collectorId);
		serializer.writeVarInt(itemCount);
		return RecyclableSingletonList.create(serializer);
	}

}
