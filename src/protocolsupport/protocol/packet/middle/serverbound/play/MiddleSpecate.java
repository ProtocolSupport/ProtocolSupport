package protocolsupport.protocol.packet.middle.serverbound.play;

import java.util.UUID;

import protocolsupport.protocol.packet.ServerBoundPacket;
import protocolsupport.protocol.packet.middle.ServerBoundMiddlePacket;
import protocolsupport.protocol.packet.middleimpl.ServerBoundPacketData;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public abstract class MiddleSpecate extends ServerBoundMiddlePacket {

	protected UUID entityUUID;

	@Override
	public RecyclableCollection<ServerBoundPacketData> toNative() {
		ServerBoundPacketData creator = ServerBoundPacketData.create(ServerBoundPacket.PLAY_SPECTATE);
		creator.writeUUID(entityUUID);
		return RecyclableSingletonList.create(creator);
	}

}
