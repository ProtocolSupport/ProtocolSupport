package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_1_8__1_9_r1__1_9_r2__1_10__1_11;

import org.bukkit.Material;

import io.netty.buffer.Unpooled;
import io.netty.handler.codec.DecoderException;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddleCustomPayload;
import protocolsupport.protocol.serializer.ProtocolSupportPacketDataSerializer;
import protocolsupport.zplatform.ServerPlatform;
import protocolsupport.zplatform.itemstack.ItemStackWrapper;
import protocolsupport.zplatform.itemstack.NBTTagCompoundWrapper;
import protocolsupport.zplatform.itemstack.NBTTagListWrapper;

public class CustomPayload extends MiddleCustomPayload {

	private final ProtocolSupportPacketDataSerializer newdata = new ProtocolSupportPacketDataSerializer(Unpooled.buffer(), ProtocolVersion.getLatest());

	@Override
	public void readFromClientData(ProtocolSupportPacketDataSerializer serializer) {
		tag = serializer.readString(20);
		if (serializer.readableBytes() > Short.MAX_VALUE) {
			throw new DecoderException("Payload may not be larger than 32767 bytes");
		}
		newdata.clear();
		if (tag.equals("MC|AdvCdm")) {
			tag = "MC|AdvCmd";
			data = ProtocolSupportPacketDataSerializer.toArray(serializer);
		} else if (tag.equals("MC|BSign") || tag.equals("MC|BEdit")) {
			ItemStackWrapper book = serializer.readItemStack();
			book.setType(Material.BOOK_AND_QUILL);
			NBTTagCompoundWrapper bookTag = book.getTag();

			if (serializer.getVersion() == ProtocolVersion.MINECRAFT_1_8) {
				NBTTagListWrapper pages = bookTag.getList("pages", 8);
				NBTTagListWrapper newPages = ServerPlatform.get().getWrapperFactory().createEmptyNBTList();

				if (!pages.isEmpty()) {
					for (int i = 0; i < pages.size(); i++) {
						String page = pages.getString(i);
						newPages.addString(strFormatting(page));
					}
					bookTag.setList("pages", newPages);
				}
			}

			newdata.writeItemStack(book);
			data = ProtocolSupportPacketDataSerializer.toArray(newdata);
		} else {
			data = ProtocolSupportPacketDataSerializer.toArray(serializer);
		}
	}

	private String strFormatting(String str) {
		char[] chars = str.toCharArray();
		char[] newChars = new char[chars.length];
		boolean mark = false;
		int index = 0;
		for (int i = 0; i < chars.length; i++) {
			char ch = chars[i];
			if (ch == '\\') {
				mark = true;
				int nextIndex = i + 1;
				if (nextIndex >= chars.length)
					break;
				char next = chars[nextIndex];
				if (next == '"') {
					ch = '"';
					i++;
					mark = false;
				} else if (next == 'n') {
					ch = '\n';
					i++;
					mark = false;
				} else if (next == '\\') {
					i++;
					mark = false;
				}
			} else if (ch == '"') {
				if (!mark) {
					continue;
				}
			}
			newChars[index++] = ch;
		}
		return new String(newChars);
	}
}
