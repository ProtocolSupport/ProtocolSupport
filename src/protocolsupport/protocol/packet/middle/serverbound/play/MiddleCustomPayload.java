package protocolsupport.protocol.packet.middle.serverbound.play;

import io.netty.buffer.Unpooled;
import protocolsupport.protocol.packet.ServerBoundPacket;
import protocolsupport.protocol.packet.middle.ServerBoundMiddlePacket;
import protocolsupport.protocol.packet.middleimpl.ServerBoundPacketData;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public abstract class MiddleCustomPayload extends ServerBoundMiddlePacket {

	protected String tag;
	protected byte[] data;

	@Override
	public RecyclableCollection<ServerBoundPacketData> toNative() {
		return RecyclableSingletonList.create(create(tag, data));
	}
	
	public static ServerBoundPacketData create(String tag, byte[] data) {
		if(tag.equals("MC|ItemName")) {
			System.out.println("ITEMNAME: " + StringSerializer.readString(Unpooled.copiedBuffer(data), ProtocolVersionsHelper.LATEST_PC));
		}
		ServerBoundPacketData creator = ServerBoundPacketData.create(ServerBoundPacket.PLAY_CUSTOM_PAYLOAD);
		StringSerializer.writeString(creator, ProtocolVersionsHelper.LATEST_PC, tag);
		creator.writeBytes(data);
		return creator;
	}

}
