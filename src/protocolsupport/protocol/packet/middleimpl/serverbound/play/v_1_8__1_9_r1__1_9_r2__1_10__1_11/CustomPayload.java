package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_1_8__1_9_r1__1_9_r2__1_10__1_11;

import org.bukkit.Material;

import io.netty.buffer.Unpooled;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddleCustomPayload;
import protocolsupport.protocol.serializer.ProtocolSupportPacketDataSerializer;
import protocolsupport.utils.nms.ItemStackWrapper;

public class CustomPayload extends MiddleCustomPayload {

	private final ProtocolSupportPacketDataSerializer newdata = new ProtocolSupportPacketDataSerializer(Unpooled.buffer(), ProtocolVersion.getLatest());

	@Override
	public void readFromClientData(ProtocolSupportPacketDataSerializer serializer) {
		tag = serializer.readString(20);
		newdata.clear();
		if (tag.equals("MC|AdvCdm")) {
			tag = "MC|AdvCmd";
			data = ProtocolSupportPacketDataSerializer.toArray(serializer);
		} else if (tag.equals("MC|BSign") || tag.equals("MC|BEdit")) {
			ItemStackWrapper book = serializer.readItemStack();
			book.setType(Material.BOOK_AND_QUILL);
			newdata.writeItemStack(book);
			data = ProtocolSupportPacketDataSerializer.toArray(newdata);
		} else {
			data = ProtocolSupportPacketDataSerializer.toArray(serializer);
		}
	}

}
