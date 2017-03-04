package protocolsupport.protocol.packet.middle.serverbound.play;

import protocolsupport.protocol.packet.ServerBoundPacket;
import protocolsupport.protocol.packet.middle.ServerBoundMiddlePacket;
import protocolsupport.protocol.packet.middleimpl.ServerBoundPacketData;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableEmptyList;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public abstract class MiddleTeleportAccept extends ServerBoundMiddlePacket {

	protected int teleportConfirmId;

	@Override
	public RecyclableCollection<ServerBoundPacketData> toNative() {
		if (cache.tryTeleportConfirm(teleportConfirmId)) {
			return create(teleportConfirmId);
		} else {
			return RecyclableEmptyList.get();
		}
	}

	public static RecyclableCollection<ServerBoundPacketData> create(int teleportId) {
		ServerBoundPacketData creator = ServerBoundPacketData.create(ServerBoundPacket.PLAY_TELEPORT_ACCEPT);
		VarNumberSerializer.writeVarInt(creator, teleportId);
		return RecyclableSingletonList.create(creator);
	}

}
