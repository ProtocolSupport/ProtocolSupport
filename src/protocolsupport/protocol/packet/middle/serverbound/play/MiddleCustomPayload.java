package protocolsupport.protocol.packet.middle.serverbound.play;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import protocolsupport.protocol.packet.ServerBoundPacket;
import protocolsupport.protocol.packet.middle.ServerBoundMiddlePacket;
import protocolsupport.protocol.packet.middleimpl.ServerBoundPacketData;
import protocolsupport.protocol.serializer.ItemStackSerializer;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;
import protocolsupport.protocol.utils.i18n.I18NData;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;
import protocolsupport.zplatform.itemstack.ItemStackWrapper;


public abstract class MiddleCustomPayload extends ServerBoundMiddlePacket {

	protected String tag;
	protected byte[] data;

	@Override
	public RecyclableCollection<ServerBoundPacketData> toNative() {
		return RecyclableSingletonList.create(create(tag, data));
	}
	
	public static ServerBoundPacketData create(String tag, byte[] data) {
		if(tag == "MC|BEdit") {
			ByteBuf buf = Unpooled.copiedBuffer(data);
			ItemStackWrapper booky = ItemStackSerializer.readItemStack(buf, ProtocolVersionsHelper.LATEST_PC, I18NData.DEFAULT_LOCALE, false);
			System.out.println("Booky: " + booky);
			System.out.println("NBT: " + booky.getTag());
		}
		ServerBoundPacketData creator = ServerBoundPacketData.create(ServerBoundPacket.PLAY_CUSTOM_PAYLOAD);
		StringSerializer.writeString(creator, ProtocolVersionsHelper.LATEST_PC, tag);
		creator.writeBytes(data);
		return creator;
	}

}
