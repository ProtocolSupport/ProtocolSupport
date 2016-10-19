package protocolsupport.protocol.packet.middle.serverbound.play;

import org.bukkit.util.Vector;

import protocolsupport.protocol.packet.ServerBoundPacket;
import protocolsupport.protocol.packet.middle.ServerBoundMiddlePacket;
import protocolsupport.protocol.packet.middleimpl.ServerBoundPacketData;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public abstract class MiddleUseEntity extends ServerBoundMiddlePacket {

	protected int entityId;
	protected Action action;
	protected Vector interactedAt;
	protected int usedHand;

	@Override
	public RecyclableCollection<ServerBoundPacketData> toNative() throws Exception {
		ServerBoundPacketData creator = ServerBoundPacketData.create(ServerBoundPacket.PLAY_USE_ENTITY);
		creator.writeVarInt(entityId);
		creator.writeEnum(action);
		switch (action) {
			case INTERACT: {
				creator.writeVarInt(usedHand);
				break;
			}
			case INTERACT_AT: {
				creator.writeFloat((float) interactedAt.getX());
				creator.writeFloat((float) interactedAt.getY());
				creator.writeFloat((float) interactedAt.getZ());
				creator.writeVarInt(usedHand);
				break;
			}
			case ATTACK: {
				break;
			}
		}
		return RecyclableSingletonList.create(creator);
	}

	protected enum Action {
		INTERACT, ATTACK, INTERACT_AT
	}

}
