package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_1_8__1_9_r1__1_9_r2__1_10;

import protocolsupport.protocol.packet.middle.serverbound.play.MiddleCustomPayload;
import protocolsupport.protocol.serializer.ProtocolSupportPacketDataSerializer;
import protocolsupport.utils.netty.ChannelUtils;

public class CustomPayload extends MiddleCustomPayload {

	@Override
	public void readFromClientData(ProtocolSupportPacketDataSerializer serializer) {
		tag = serializer.readString(20);
		data = ChannelUtils.toArray(serializer);
	}

}
