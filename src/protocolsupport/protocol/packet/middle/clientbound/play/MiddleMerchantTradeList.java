package protocolsupport.protocol.packet.middle.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.serializer.MerchantDataSerializer;
import protocolsupport.protocol.types.MerchantData;

public abstract class MiddleMerchantTradeList extends ClientBoundMiddlePacket {

	protected MiddleMerchantTradeList(MiddlePacketInit init) {
		super(init);
	}

	protected MerchantData merchantData;

	@Override
	protected void decode(ByteBuf serverdata) {
		merchantData = MerchantDataSerializer.readMerchantData(serverdata);
	}

}
