package protocolsupport.protocol.packet.middle.serverbound.play;

import org.bukkit.util.Vector;

import protocolsupport.protocol.packet.ServerBoundPacket;
import protocolsupport.protocol.packet.middle.ServerBoundMiddlePacket;
import protocolsupport.protocol.packet.middleimpl.PacketCreator;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public abstract class MiddleUseEntity extends ServerBoundMiddlePacket {

	protected int entityId;
	protected int action;
	protected Vector interactedAt;
	protected int usedHand;

	@Override
	public RecyclableCollection<PacketCreator> toNative() throws Exception {
		PacketCreator creator = PacketCreator.create(ServerBoundPacket.PLAY_USE_ENTITY);
		creator.writeVarInt(entityId);
		creator.writeVarInt(action);
		if (action == 2) {
			creator.writeFloat((float) interactedAt.getX());
			creator.writeFloat((float) interactedAt.getY());
			creator.writeFloat((float) interactedAt.getZ());
		}
		creator.writeVarInt(usedHand);
		return RecyclableSingletonList.create(creator);
	}

}
