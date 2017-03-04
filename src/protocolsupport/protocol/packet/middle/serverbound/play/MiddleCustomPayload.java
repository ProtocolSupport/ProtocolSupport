package protocolsupport.protocol.packet.middle.serverbound.play;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.ServerBoundPacket;
import protocolsupport.protocol.packet.middle.ServerBoundMiddlePacket;
import protocolsupport.protocol.packet.middleimpl.ServerBoundPacketData;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public abstract class MiddleCustomPayload extends ServerBoundMiddlePacket {

	protected String tag;
	protected byte[] data;

	@Override
	public RecyclableCollection<ServerBoundPacketData> toNative() {
		ServerBoundPacketData creator = ServerBoundPacketData.create(ServerBoundPacket.PLAY_CUSTOM_PAYLOAD);
		StringSerializer.writeString(creator, ProtocolVersion.getLatest(), tag);
		creator.writeBytes(data);
		return RecyclableSingletonList.create(creator);
	}

}
