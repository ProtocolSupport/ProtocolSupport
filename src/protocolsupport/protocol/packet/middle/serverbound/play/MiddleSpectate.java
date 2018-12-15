package protocolsupport.protocol.packet.middle.serverbound.play;

import java.util.UUID;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.ServerBoundPacket;
import protocolsupport.protocol.packet.middle.ServerBoundMiddlePacket;
import protocolsupport.protocol.packet.middleimpl.ServerBoundPacketData;
import protocolsupport.protocol.serializer.MiscSerializer;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public abstract class MiddleSpectate extends ServerBoundMiddlePacket {

	public MiddleSpectate(ConnectionImpl connection) {
		super(connection);
	}

	protected UUID entityUUID;

	@Override
	public RecyclableCollection<ServerBoundPacketData> toNative() {
		ServerBoundPacketData creator = ServerBoundPacketData.create(ServerBoundPacket.PLAY_SPECTATE);
		MiscSerializer.writeUUID(creator, entityUUID);
		return RecyclableSingletonList.create(creator);
	}

}
