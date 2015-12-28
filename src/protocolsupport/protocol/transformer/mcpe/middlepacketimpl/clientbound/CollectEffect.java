package protocolsupport.protocol.transformer.mcpe.middlepacketimpl.clientbound;

import java.io.IOException;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.transformer.mcpe.packet.mcpe.ClientboundPEPacket;
import protocolsupport.protocol.transformer.mcpe.packet.mcpe.clientbound.PickupItemEffectPacket;
import protocolsupport.protocol.transformer.middlepacket.clientbound.play.MiddleCollectEffect;
import protocolsupport.protocol.typeremapper.watchedentity.remapper.SpecificType;
import protocolsupport.protocol.typeremapper.watchedentity.types.WatchedEntity;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableEmptyList;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class CollectEffect extends MiddleCollectEffect<RecyclableCollection<? extends ClientboundPEPacket>> {

	@Override
	public RecyclableCollection<? extends ClientboundPEPacket> toData(ProtocolVersion version) throws IOException {
		if (collectorId == storage.getWatchedSelfPlayer().getId()) {
			return RecyclableEmptyList.get();
		}
		WatchedEntity wentity = storage.getWatchedEntity(entityId);
		if (wentity != null && wentity.getType() != SpecificType.ARROW) {
			return RecyclableSingletonList.create(new PickupItemEffectPacket(collectorId, entityId));
		} else {
			return RecyclableEmptyList.get();
		}
	}

}
