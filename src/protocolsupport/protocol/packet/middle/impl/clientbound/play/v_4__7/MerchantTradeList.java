package protocolsupport.protocol.packet.middle.impl.clientbound.play.v_4__7;

import protocolsupport.protocol.codec.MerchantDataCodec;
import protocolsupport.protocol.packet.middle.base.clientbound.play.MiddleMerchantTradeList;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV4;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV5;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV6;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV7;
import protocolsupport.protocol.storage.netcache.ClientCache;
import protocolsupport.protocol.typeremapper.legacy.LegacyCustomPayloadChannelName;

public class MerchantTradeList extends MiddleMerchantTradeList implements
IClientboundMiddlePacketV4,
IClientboundMiddlePacketV5,
IClientboundMiddlePacketV6,
IClientboundMiddlePacketV7
{

	public MerchantTradeList(IMiddlePacketInit init) {
		super(init);
	}

	protected final ClientCache clientCache = cache.getClientCache();

	@Override
	protected void write() {
		io.writeClientbound(CustomPayload.create(
			version,
			LegacyCustomPayloadChannelName.LEGACY_TRADE_LIST,
			merchantData,
			(merchantDataTo, merchantData) -> MerchantDataCodec.writeMerchantData(merchantDataTo, version, clientCache.getLocale(), merchantData)
		));
	}

}
