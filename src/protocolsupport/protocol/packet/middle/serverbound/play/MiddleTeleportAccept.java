package protocolsupport.protocol.packet.middle.serverbound.play;

import protocolsupport.protocol.packet.ServerBoundPacket;
import protocolsupport.protocol.packet.middle.ServerBoundMiddlePacket;
import protocolsupport.protocol.packet.middleimpl.PacketCreator;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public abstract class MiddleTeleportAccept extends ServerBoundMiddlePacket {

	protected int teleportAcceptId;

	@Override
	public RecyclableCollection<PacketCreator> toNative() throws Exception {
		return create(teleportAcceptId);
	}

	public static RecyclableCollection<PacketCreator> create(int teleportId) {
		PacketCreator creator = PacketCreator.create(ServerBoundPacket.PLAY_TELEPORT_ACCEPT);
		creator.writeVarInt(teleportId);
		return RecyclableSingletonList.create(creator);
	}

}
