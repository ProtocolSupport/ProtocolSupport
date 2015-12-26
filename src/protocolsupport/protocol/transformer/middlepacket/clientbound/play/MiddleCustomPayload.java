package protocolsupport.protocol.transformer.middlepacket.clientbound.play;

import protocolsupport.protocol.PacketDataSerializer;
import protocolsupport.protocol.storage.LocalStorage;
import protocolsupport.protocol.transformer.middlepacket.ClientBoundMiddlePacket;
import protocolsupport.utils.ChannelUtils;

public abstract class MiddleCustomPayload<T> extends ClientBoundMiddlePacket<T> {

	protected String tag;
	protected byte[] data;

	@Override
	public void readFromServerData(PacketDataSerializer serializer) {
		tag = serializer.readString(20);
		data = ChannelUtils.toArray(serializer);
	}

	@Override
	public void handle(LocalStorage storage) {
	}

}
