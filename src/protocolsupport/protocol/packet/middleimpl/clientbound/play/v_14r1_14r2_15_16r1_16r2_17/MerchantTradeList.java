package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_14r1_14r2_15_16r1_16r2_17;

import protocolsupport.protocol.codec.MerchantDataCodec;
import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleMerchantTradeList;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;

public class MerchantTradeList extends MiddleMerchantTradeList {

	public MerchantTradeList(MiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void write() {
		ClientBoundPacketData merchanttradelist = ClientBoundPacketData.create(ClientBoundPacketType.PLAY_MERCHANT_TRADE_LIST);
		MerchantDataCodec.writeMerchantData(merchanttradelist, version, cache.getClientCache().getLocale(), merchantData);
		codec.writeClientbound(merchanttradelist);
	}

}
