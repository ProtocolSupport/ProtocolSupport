package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_1_4__1_5__1_6;

import java.io.IOException;

import io.netty.buffer.Unpooled;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddleCustomPayload;
import protocolsupport.protocol.serializer.ProtocolSupportPacketDataSerializer;
import protocolsupport.protocol.serializer.RecyclableProtocolSupportPacketDataSerializer;
import protocolsupport.utils.netty.ChannelUtils;

public class CustomPayload extends MiddleCustomPayload {

	@Override
	public void readFromClientData(ProtocolSupportPacketDataSerializer serializer) throws IOException {
		tag = serializer.readString(20);
		ProtocolSupportPacketDataSerializer olddata = new ProtocolSupportPacketDataSerializer(Unpooled.wrappedBuffer(serializer.readByteArray(Short.MAX_VALUE)), serializer.getVersion());
		RecyclableProtocolSupportPacketDataSerializer newdata = RecyclableProtocolSupportPacketDataSerializer.create(ProtocolVersion.getLatest());
		try {
			if (tag.equals("MC|ItemName")) {
				newdata.writeVarInt(olddata.readableBytes());
				newdata.writeBytes(olddata);
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
		} finally {
			newdata.release();
		}
	}

}
