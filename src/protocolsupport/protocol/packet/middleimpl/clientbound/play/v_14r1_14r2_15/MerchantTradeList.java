package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_14r1_14r2_15;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleMerchantTradeList;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.MerchantDataSerializer;

public class MerchantTradeList extends MiddleMerchantTradeList {

	public MerchantTradeList(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	protected void writeToClient() {
		ClientBoundPacketData merchanttradelist = ClientBoundPacketData.create(PacketType.CLIENTBOUND_PLAY_MERCHANT_TRADE_LIST);
		MerchantDataSerializer.writeMerchantData(merchanttradelist, version, cache.getAttributesCache().getLocale(), merchantData);
		codec.write(merchanttradelist);
	}

}
