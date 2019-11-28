package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_8_9r1_9r2_10_11_12r1_12r2;

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
		codec.write(CustomPayload.create(LegacyCustomPayloadChannelName.LEGACY_TRADE_LIST, to -> {
			MerchantDataSerializer.writeMerchantData(to, version, cache.getAttributesCache().getLocale(), merchantData);
		}));
	}

}
