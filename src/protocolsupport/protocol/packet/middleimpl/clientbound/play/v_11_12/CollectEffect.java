package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_11_12;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.ClientBoundPacket;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleCollectEffect;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class CollectEffect extends MiddleCollectEffect {

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData(ProtocolVersion version) {
		ClientBoundPacketData serializer = ClientBoundPacketData.create(ClientBoundPacket.PLAY_COLLECT_EFFECT_ID, version);
		VarNumberSerializer.writeVarInt(serializer, entityId);
		VarNumberSerializer.writeVarInt(serializer, collectorId);
		VarNumberSerializer.writeVarInt(serializer, itemCount);
		return RecyclableSingletonList.create(serializer);
	}

}
