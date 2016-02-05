package protocolsupport.protocol.transformer.middlepacketimpl.serverbound.play.v_1_4_1_5_1_6;

import java.io.IOException;

import io.netty.buffer.Unpooled;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.PacketDataSerializer;
import protocolsupport.protocol.RecyclablePacketDataSerializer;
import protocolsupport.protocol.transformer.middlepacket.serverbound.play.MiddleCustomPayload;
import protocolsupport.utils.netty.ChannelUtils;

public class CustomPayload extends MiddleCustomPayload {

	@Override
	public void readFromClientData(PacketDataSerializer serializer) throws IOException {
		tag = serializer.readString(20);
		PacketDataSerializer olddata = new PacketDataSerializer(Unpooled.wrappedBuffer(serializer.readArray()), serializer.getVersion());
		RecyclablePacketDataSerializer newdata = RecyclablePacketDataSerializer.create(ProtocolVersion.getLatest());
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
				newdata.writeString(olddata.readString(Short.MAX_VALUE));
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
