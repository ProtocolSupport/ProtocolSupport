package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_8_9r1_9r2_10_11_12;

import org.bukkit.Material;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.handler.codec.DecoderException;
import protocolsupport.api.ProtocolType;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.api.chat.ChatAPI;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddleCustomPayload;
import protocolsupport.protocol.serializer.ItemStackSerializer;
import protocolsupport.protocol.serializer.MiscSerializer;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.zplatform.ServerPlatform;
import protocolsupport.zplatform.itemstack.ItemStackWrapper;
import protocolsupport.zplatform.itemstack.NBTTagCompoundWrapper;
import protocolsupport.zplatform.itemstack.NBTTagListWrapper;

public class CustomPayload extends MiddleCustomPayload {

	private final ByteBuf newdata = Unpooled.buffer();

	@Override
	public void readFromClientData(ByteBuf clientdata, ProtocolVersion version) {
		tag = StringSerializer.readString(clientdata, version, 20);
		if (clientdata.readableBytes() > Short.MAX_VALUE) {
			throw new DecoderException("Payload may not be larger than 32767 bytes");
		}
		newdata.clear();
		if (tag.equals("MC|AdvCdm")) {
			tag = "MC|AdvCmd";
			data = MiscSerializer.readAllBytes(clientdata);
		} else if (tag.equals("MC|BSign") || tag.equals("MC|BEdit")) {
			ItemStackWrapper book = ItemStackSerializer.readItemStack(clientdata, version);
			book.setType(Material.BOOK_AND_QUILL);
			if (version == ProtocolVersion.MINECRAFT_1_8 && tag.equals("MC|BSign")) {
				remapBookPages(book);
			}
			ItemStackSerializer.writeItemStack(newdata, ProtocolVersion.getLatest(ProtocolType.PC), book, false);
			data = MiscSerializer.readAllBytes(newdata);
		} else {
			data = MiscSerializer.readAllBytes(clientdata);
		}
	}

	private static void remapBookPages(ItemStackWrapper itemstack) {
		NBTTagCompoundWrapper tag = itemstack.getTag();
		if (!tag.isNull()) {
			if (tag.hasKeyOfType("pages", NBTTagCompoundWrapper.TYPE_LIST)) {
				NBTTagListWrapper pages = tag.getList("pages", NBTTagCompoundWrapper.TYPE_STRING);
				NBTTagListWrapper newPages = ServerPlatform.get().getWrapperFactory().createEmptyNBTList();
				for (int i = 0; i < pages.size(); i++) {
					newPages.addString(ChatAPI.fromJSON(pages.getString(i)).toLegacyText());
				}
				tag.setList("pages", newPages);
			}
			itemstack.setTag(tag);
		}
	}

}
