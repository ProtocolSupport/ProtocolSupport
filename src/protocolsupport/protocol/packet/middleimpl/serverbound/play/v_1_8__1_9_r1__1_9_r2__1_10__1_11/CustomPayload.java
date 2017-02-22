package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_1_8__1_9_r1__1_9_r2__1_10__1_11;

import org.bukkit.Material;

import io.netty.buffer.Unpooled;
import io.netty.handler.codec.DecoderException;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.api.chat.ChatAPI;
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
			if (serializer.getVersion() == ProtocolVersion.MINECRAFT_1_8) {
				remapBookPages(book);
			}
			newdata.writeItemStack(book);
			data = ProtocolSupportPacketDataSerializer.toArray(newdata);
		} else {
			data = ProtocolSupportPacketDataSerializer.toArray(serializer);
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
