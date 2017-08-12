package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_4_5_6;

import org.bukkit.Material;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.handler.codec.DecoderException;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddleCustomPayload;
import protocolsupport.protocol.serializer.ArraySerializer;
import protocolsupport.protocol.serializer.ItemStackSerializer;
import protocolsupport.protocol.serializer.MiscSerializer;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;
import protocolsupport.zplatform.itemstack.ItemStackWrapper;

//TODO: Create types for cmd control data and use them to share more code
public class CustomPayload extends MiddleCustomPayload {

	private final ByteBuf newdata = Unpooled.buffer();

	@Override
	public void readFromClientData(ByteBuf clientdata) {
		ProtocolVersion version = connection.getVersion();
		tag = StringSerializer.readString(clientdata, version, 20);
		if (clientdata.readableBytes() > Short.MAX_VALUE) {
			throw new DecoderException("Payload may not be larger than 32767 bytes");
		}
		newdata.clear();
		ByteBuf olddata = Unpooled.wrappedBuffer(ArraySerializer.readByteArray(clientdata, version, Short.MAX_VALUE));
		if (tag.equals("MC|ItemName")) {
			ArraySerializer.writeByteArray(newdata, ProtocolVersionsHelper.LATEST_PC, olddata);
		} else if (tag.equals("MC|BSign") || tag.equals("MC|BEdit")) {
			ItemStackWrapper book = ItemStackSerializer.readItemStack(olddata, version, cache.getLocale(), true);
			if (!book.isNull()) {
				book.setType(Material.BOOK_AND_QUILL);
			}
			ItemStackSerializer.writeItemStack(newdata, ProtocolVersionsHelper.LATEST_PC, cache.getLocale(), book, false);
		} else if (tag.equals("MC|AdvCdm")) {
			tag = "MC|AdvCmd";
			newdata.writeByte(0);
			newdata.writeInt(olddata.readInt());
			newdata.writeInt(olddata.readInt());
			newdata.writeInt(olddata.readInt());
			StringSerializer.writeString(newdata, ProtocolVersionsHelper.LATEST_PC, StringSerializer.readString(olddata, version));
			newdata.writeBoolean(true);
		} else {
			newdata.writeBytes(olddata);
		}
		data = MiscSerializer.readAllBytes(newdata);
	}

}
