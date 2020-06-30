package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_13;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleMerchantTradeList;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_13_14r1_14r2_15_16.CustomPayload;
import protocolsupport.protocol.serializer.MerchantDataSerializer;
import protocolsupport.protocol.typeremapper.legacy.LegacyCustomPayloadChannelName;

public class MerchantTradeList extends MiddleMerchantTradeList {

	public MerchantTradeList(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	protected void writeToClient() {
		codec.write(CustomPayload.create(
			LegacyCustomPayloadChannelName.MODERN_TRADE_LIST,
			to -> MerchantDataSerializer.writeMerchantData(to, version, cache.getClientCache().getLocale(), merchantData)
		));
	}

}
