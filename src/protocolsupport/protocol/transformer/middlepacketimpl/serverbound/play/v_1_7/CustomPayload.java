package protocolsupport.protocol.transformer.middlepacketimpl.serverbound.play.v_1_7;

import java.io.IOException;

import io.netty.buffer.Unpooled;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.PacketDataSerializer;
import protocolsupport.protocol.transformer.middlepacket.serverbound.play.MiddleCustomPayload;
import protocolsupport.protocol.transformer.middlepacketimpl.SupportedVersions;
import protocolsupport.utils.Utils;

@SupportedVersions({ProtocolVersion.MINECRAFT_1_7_10, ProtocolVersion.MINECRAFT_1_7_5})
public class CustomPayload extends MiddleCustomPayload {

	@Override
	public void readFromClientData(PacketDataSerializer serializer) throws IOException {
		tag = serializer.readString(20);
		PacketDataSerializer olddata = new PacketDataSerializer(Unpooled.wrappedBuffer(serializer.readArray()), serializer.getVersion());
		PacketDataSerializer newdata = PacketDataSerializer.createNew(ProtocolVersion.getLatest());
		try {
			if (tag.equals("MC|ItemName")) {
				newdata.writeVarInt(olddata.readableBytes());
				newdata.writeBytes(olddata);
			} else if (tag.equals("MC|BSign") || tag.equals("MC|BEdit")) {
				newdata.writeItemStack(olddata.readItemStack());
			} else if (tag.equals("MC|AdvCdm")) {
				newdata.writeByte(olddata.readByte());
				newdata.writeInt(olddata.readInt());
				newdata.writeInt(olddata.readInt());
				newdata.writeInt(olddata.readInt());
				newdata.writeString(olddata.readString(Short.MAX_VALUE));
				newdata.writeBoolean(true);
			} else {
				newdata.writeBytes(olddata);
			}
			data = Utils.toArray(newdata);
		} finally {
			newdata.release();
		}
	}

}
