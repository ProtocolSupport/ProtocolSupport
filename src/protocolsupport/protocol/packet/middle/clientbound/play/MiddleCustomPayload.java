package protocolsupport.protocol.packet.middle.clientbound.play;

import protocolsupport.protocol.PacketDataSerializer;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.utils.netty.ChannelUtils;

public abstract class MiddleCustomPayload<T> extends ClientBoundMiddlePacket<T> {

	protected String tag;
	protected byte[] data;

	@Override
	public void readFromServerData(PacketDataSerializer serializer) {
		tag = serializer.readString(20);
		data = ChannelUtils.toArray(serializer);
	}

}
