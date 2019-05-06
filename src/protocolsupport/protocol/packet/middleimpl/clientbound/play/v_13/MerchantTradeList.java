package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_13;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleMerchantTradeList;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_13_14.CustomPayload;
import protocolsupport.protocol.serializer.MerchantDataSerializer;
import protocolsupport.protocol.typeremapper.legacy.LegacyCustomPayloadChannelName;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class MerchantTradeList extends MiddleMerchantTradeList {

	public MerchantTradeList(ConnectionImpl connection) {
		super(connection);
	}

	protected final ByteBuf buffer = Unpooled.buffer();

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData() {
		buffer.clear();
		MerchantDataSerializer.writeMerchantData(buffer, version, cache.getAttributesCache().getLocale(), merchantData);
		return RecyclableSingletonList.create(CustomPayload.create(version, LegacyCustomPayloadChannelName.MODERN_TRADE_LIST, buffer));
	}

}
