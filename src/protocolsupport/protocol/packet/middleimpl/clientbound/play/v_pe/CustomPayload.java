package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe;

import protocolsupport.protocol.packet.middle.clientbound.play.MiddleCustomPayload;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.MerchantDataSerializer;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableEmptyList;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class CustomPayload extends MiddleCustomPayload {

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData() {
		if (tag.equals("MC|TrList")) {
			return RecyclableSingletonList.create(cache.getPEInventoryCache().getFakeVillager().updateTrade(
					cache, connection.getVersion(),
					MerchantDataSerializer.readMerchantData(data, ProtocolVersionsHelper.LATEST_PC, cache.getAttributesCache().getLocale()))
			);
		}
		return RecyclableEmptyList.get();
	}

}
