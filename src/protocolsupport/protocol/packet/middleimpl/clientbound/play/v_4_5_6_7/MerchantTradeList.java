package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_4_5_6_7;

import protocolsupport.protocol.packet.middle.clientbound.play.MiddleMerchantTradeList;
import protocolsupport.protocol.serializer.MerchantDataSerializer;
import protocolsupport.protocol.typeremapper.legacy.LegacyCustomPayloadChannelName;

public class MerchantTradeList extends MiddleMerchantTradeList {

	public MerchantTradeList(MiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void writeToClient() {
		codec.write(CustomPayload.create(
			version,
			LegacyCustomPayloadChannelName.LEGACY_TRADE_LIST,
			to -> MerchantDataSerializer.writeMerchantData(to, version, cache.getClientCache().getLocale(), merchantData)
		));
	}

}
