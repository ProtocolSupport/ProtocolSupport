package protocolsupport.protocol.packet.middle.base.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.codec.MerchantDataCodec;
import protocolsupport.protocol.packet.middle.base.clientbound.ClientBoundMiddlePacket;
import protocolsupport.protocol.types.MerchantData;

public abstract class MiddleMerchantTradeList extends ClientBoundMiddlePacket {

	protected MiddleMerchantTradeList(IMiddlePacketInit init) {
		super(init);
	}

	protected MerchantData merchantData;

	@Override
	protected void decode(ByteBuf serverdata) {
		merchantData = MerchantDataCodec.readMerchantData(serverdata);
	}

}
