package protocolsupport.protocol.packet.middle.clientbound.play;

import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.serializer.ProtocolSupportPacketDataSerializer;
import protocolsupport.utils.netty.ChannelUtils;

public abstract class MiddleCustomPayload<T> extends ClientBoundMiddlePacket<T> {

	protected String tag;
	protected byte[] data;

	@Override
	public void readFromServerData(ProtocolSupportPacketDataSerializer serializer) {
		tag = serializer.readString(20);
		data = ChannelUtils.toArray(serializer);
	}

}
