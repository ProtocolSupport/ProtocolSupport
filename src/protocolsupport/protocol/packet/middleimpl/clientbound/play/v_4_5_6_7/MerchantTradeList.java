package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_4_5_6_7;

import protocolsupport.protocol.codec.MerchantDataCodec;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleMerchantTradeList;
import protocolsupport.protocol.storage.netcache.ClientCache;
import protocolsupport.protocol.typeremapper.legacy.LegacyCustomPayloadChannelName;

public class MerchantTradeList extends MiddleMerchantTradeList {

	public MerchantTradeList(MiddlePacketInit init) {
		super(init);
	}

	protected final ClientCache clientCache = cache.getClientCache();

	@Override
	protected void write() {
		codec.writeClientbound(CustomPayload.create(
			version,
			LegacyCustomPayloadChannelName.LEGACY_TRADE_LIST,
			merchantData,
			(merchantDataTo, merchantData) -> MerchantDataCodec.writeMerchantData(merchantDataTo, version, clientCache.getLocale(), merchantData)
		));
	}

}
