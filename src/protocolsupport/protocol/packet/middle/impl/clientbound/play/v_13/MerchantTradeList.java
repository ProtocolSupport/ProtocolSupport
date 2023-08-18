package protocolsupport.protocol.packet.middle.impl.clientbound.play.v_13;

import protocolsupport.protocol.codec.MerchantDataCodec;
import protocolsupport.protocol.packet.middle.base.clientbound.play.MiddleMerchantTradeList;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV13;
import protocolsupport.protocol.packet.middle.impl.clientbound.play.v_13__20.CustomPayload;
import protocolsupport.protocol.typeremapper.legacy.LegacyCustomPayloadChannelName;

public class MerchantTradeList extends MiddleMerchantTradeList implements IClientboundMiddlePacketV13 {

	public MerchantTradeList(IMiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void write() {
		io.writeClientbound(CustomPayload.create(
			LegacyCustomPayloadChannelName.MODERN_TRADE_LIST,
			to -> MerchantDataCodec.writeMerchantData(to, version, cache.getClientCache().getLocale(), merchantData)
		));
	}

}
