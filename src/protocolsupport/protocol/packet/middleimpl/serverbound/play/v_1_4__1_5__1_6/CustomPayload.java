package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_1_4__1_5__1_6;

import io.netty.buffer.Unpooled;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddleCustomPayload;
import protocolsupport.protocol.serializer.ProtocolSupportPacketDataSerializer;
import protocolsupport.utils.netty.ChannelUtils;

public class CustomPayload extends MiddleCustomPayload {

	private final ProtocolSupportPacketDataSerializer newdata = new ProtocolSupportPacketDataSerializer(Unpooled.buffer(), ProtocolVersion.getLatest());

	@Override
	public void readFromClientData(ProtocolSupportPacketDataSerializer serializer) {
		tag = serializer.readString(20);
		ProtocolSupportPacketDataSerializer olddata = new ProtocolSupportPacketDataSerializer(Unpooled.wrappedBuffer(serializer.readByteArray(Short.MAX_VALUE)), serializer.getVersion());
		newdata.clear();
		if (tag.equals("MC|ItemName")) {
			newdata.writeByteArray(olddata);
		} else if (tag.equals("MC|BSign") || tag.equals("MC|BEdit")) {
			newdata.writeItemStack(olddata.readItemStack());
		} else if (tag.equals("MC|AdvCdm")) {
			newdata.writeByte(0);
			newdata.writeInt(olddata.readInt());
			newdata.writeInt(olddata.readInt());
			newdata.writeInt(olddata.readInt());
			newdata.writeString(olddata.readString());
			newdata.writeBoolean(true);
		} else {
			newdata.writeBytes(olddata);
		}
		data = ChannelUtils.toArray(newdata);
	}

}
