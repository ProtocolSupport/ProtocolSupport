package protocolsupport.protocol.packet.middle.serverbound.play;

import protocolsupport.protocol.packet.ServerBoundPacket;
import protocolsupport.protocol.packet.middle.ServerBoundMiddlePacket;
import protocolsupport.protocol.packet.middleimpl.ServerBoundPacketData;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public abstract class MiddleChat extends ServerBoundMiddlePacket {

	protected String message;

	@Override
	public RecyclableCollection<ServerBoundPacketData> toNative() {
		return RecyclableSingletonList.create(create(message));
	}

	public static ServerBoundPacketData create(String message) {
		ServerBoundPacketData creator = ServerBoundPacketData.create(ServerBoundPacket.PLAY_CHAT);
		StringSerializer.writeString(creator, ProtocolVersionsHelper.LATEST_PC, message);
		return creator;
	}
}
