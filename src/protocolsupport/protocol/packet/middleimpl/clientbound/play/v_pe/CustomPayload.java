package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleCustomPayload;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.MerchantDataSerializer;
import protocolsupport.protocol.storage.netcache.NetworkDataCache;
import protocolsupport.protocol.typeremapper.pe.PEPacketIDs;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;
import protocolsupport.protocol.utils.types.MerchantData;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableEmptyList;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class CustomPayload extends MiddleCustomPayload {

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData() {
		if (tag.equals("MC|TrList")) {
			return RecyclableSingletonList.create(openTrade(
				connection.getVersion(), 
				cache, 
				MerchantDataSerializer.readMerchantData(data, ProtocolVersionsHelper.LATEST_PC, cache.getAttributesCache().getLocale())
			));
		}
		return RecyclableEmptyList.get();
	}
	
	public static ClientBoundPacketData openTrade(ProtocolVersion version, NetworkDataCache cache, MerchantData data) {
		ClientBoundPacketData serializer = ClientBoundPacketData.create(PEPacketIDs.TRADE_UPDATE, version);
		MerchantDataSerializer.writePEMerchantData(serializer, version, cache, data);
		return serializer;
	}

}
