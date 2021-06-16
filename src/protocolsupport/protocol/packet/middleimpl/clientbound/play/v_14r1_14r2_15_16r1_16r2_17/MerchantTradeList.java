package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_14r1_14r2_15_16r1_16r2_17;

import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleMerchantTradeList;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.MerchantDataSerializer;

public class MerchantTradeList extends MiddleMerchantTradeList {

	public MerchantTradeList(MiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void write() {
		ClientBoundPacketData merchanttradelist = ClientBoundPacketData.create(PacketType.CLIENTBOUND_PLAY_MERCHANT_TRADE_LIST);
		MerchantDataSerializer.writeMerchantData(merchanttradelist, version, cache.getClientCache().getLocale(), merchantData);
		codec.writeClientbound(merchanttradelist);
	}

}
