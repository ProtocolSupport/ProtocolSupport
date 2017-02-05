package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_1_7;

import org.bukkit.Material;

import io.netty.buffer.Unpooled;
import io.netty.handler.codec.DecoderException;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddleCustomPayload;
import protocolsupport.protocol.serializer.ProtocolSupportPacketDataSerializer;
import protocolsupport.zplatform.itemstack.ItemStackWrapper;

public class CustomPayload extends MiddleCustomPayload {

	private final ProtocolSupportPacketDataSerializer newdata = new ProtocolSupportPacketDataSerializer(Unpooled.buffer(), ProtocolVersion.getLatest());

	@Override
	public void readFromClientData(ProtocolSupportPacketDataSerializer serializer) {
		tag = serializer.readString(20);
		if (serializer.readableBytes() > Short.MAX_VALUE) {
			throw new DecoderException("Payload may not be larger than 32767 bytes");
		}
		ProtocolSupportPacketDataSerializer olddata = new ProtocolSupportPacketDataSerializer(Unpooled.wrappedBuffer(serializer.readByteArray()), serializer.getVersion());
		newdata.clear();
		if (tag.equals("MC|ItemName")) {
			newdata.writeByteArray(olddata);
		} else if (tag.equals("MC|BSign") || tag.equals("MC|BEdit")) {
			ItemStackWrapper book = olddata.readItemStack();
			book.setType(Material.BOOK_AND_QUILL);
			newdata.writeItemStack(book);
		} else if (tag.equals("MC|AdvCdm")) {
			tag = "MC|AdvCmd";
			newdata.writeByte(olddata.readByte());
			newdata.writeInt(olddata.readInt());
			newdata.writeInt(olddata.readInt());
			newdata.writeInt(olddata.readInt());
			newdata.writeString(olddata.readString());
			newdata.writeBoolean(true);
		} else {
			newdata.writeBytes(olddata);
		}
		data = ProtocolSupportPacketDataSerializer.toArray(newdata);
	}

}
