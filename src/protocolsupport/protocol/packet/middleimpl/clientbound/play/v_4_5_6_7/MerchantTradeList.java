package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_4_5_6_7;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleMerchantTradeList;
import protocolsupport.protocol.serializer.MerchantDataSerializer;
import protocolsupport.protocol.typeremapper.legacy.LegacyCustomPayloadChannelName;

public class MerchantTradeList extends MiddleMerchantTradeList {

	public MerchantTradeList(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	public void writeToClient() {
		codec.write(CustomPayload.create(codec, version, LegacyCustomPayloadChannelName.LEGACY_TRADE_LIST, to -> {
			MerchantDataSerializer.writeMerchantData(to, version, cache.getAttributesCache().getLocale(), merchantData);
		}));
	}

}
