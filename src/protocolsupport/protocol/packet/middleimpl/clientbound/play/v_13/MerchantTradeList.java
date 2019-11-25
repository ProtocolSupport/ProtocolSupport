package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_13;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleMerchantTradeList;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_13_14r1_14r2.CustomPayload;
import protocolsupport.protocol.serializer.MerchantDataSerializer;
import protocolsupport.protocol.typeremapper.legacy.LegacyCustomPayloadChannelName;

public class MerchantTradeList extends MiddleMerchantTradeList {

	public MerchantTradeList(ConnectionImpl connection) {
		super(connection);
	}

	protected final ByteBuf buffer = Unpooled.buffer();

	@Override
	public void writeToClient() {
		MerchantDataSerializer.writeMerchantData(buffer, version, cache.getAttributesCache().getLocale(), merchantData);
		codec.write(CustomPayload.create(codec, LegacyCustomPayloadChannelName.MODERN_TRADE_LIST, buffer));
	}

	@Override
	public void postHandle() {
		buffer.clear();
	}

}
