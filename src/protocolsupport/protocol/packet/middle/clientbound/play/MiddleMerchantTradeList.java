package protocolsupport.protocol.packet.middle.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.serializer.MerchantDataSerializer;
import protocolsupport.protocol.types.MerchantData;

public abstract class MiddleMerchantTradeList extends ClientBoundMiddlePacket {

	protected MerchantData merchantData;


	public MiddleMerchantTradeList(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	public void readFromServerData(ByteBuf serverdata) {
		merchantData = MerchantDataSerializer.readMerchantData(serverdata);
	}

}
