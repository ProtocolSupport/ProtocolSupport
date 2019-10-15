package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_4_5_6_7;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleMerchantTradeList;
import protocolsupport.protocol.packet.middleimpl.IPacketData;
import protocolsupport.protocol.serializer.MerchantDataSerializer;
import protocolsupport.protocol.typeremapper.legacy.LegacyCustomPayloadChannelName;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class MerchantTradeList extends MiddleMerchantTradeList {

	public MerchantTradeList(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	public RecyclableCollection<? extends IPacketData> toData() {
		return RecyclableSingletonList.create(CustomPayload.create(version, LegacyCustomPayloadChannelName.LEGACY_TRADE_LIST, to -> {
			MerchantDataSerializer.writeMerchantData(to, version, cache.getAttributesCache().getLocale(), merchantData);
		}));
	}

}
