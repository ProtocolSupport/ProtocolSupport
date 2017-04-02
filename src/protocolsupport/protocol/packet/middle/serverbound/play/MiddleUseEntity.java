package protocolsupport.protocol.packet.middle.serverbound.play;

import org.bukkit.util.Vector;

import protocolsupport.protocol.packet.ServerBoundPacket;
import protocolsupport.protocol.packet.middle.ServerBoundMiddlePacket;
import protocolsupport.protocol.packet.middleimpl.ServerBoundPacketData;
import protocolsupport.protocol.serializer.MiscSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public abstract class MiddleUseEntity extends ServerBoundMiddlePacket {

	protected int entityId;
	protected Action action;
	protected Vector interactedAt;
	protected int usedHand;

	@Override
	public RecyclableCollection<ServerBoundPacketData> toNative() {
		return RecyclableSingletonList.create(create(entityId, action, interactedAt, usedHand));
	}
	
	public static ServerBoundPacketData create(int entityId, Action action, Vector interactedAt, int usedHand) {
		ServerBoundPacketData creator = ServerBoundPacketData.create(ServerBoundPacket.PLAY_USE_ENTITY);
		VarNumberSerializer.writeVarInt(creator, entityId);
		MiscSerializer.writeEnum(creator, action);
		switch (action) {
			case INTERACT: {
				VarNumberSerializer.writeVarInt(creator, usedHand);
				break;
			}
			case INTERACT_AT: {
				creator.writeFloat((float) interactedAt.getX());
				creator.writeFloat((float) interactedAt.getY());
				creator.writeFloat((float) interactedAt.getZ());
				VarNumberSerializer.writeVarInt(creator, usedHand);
				break;
			}
			case ATTACK: {
				break;
			}
		}
		return creator;
	}

	public enum Action {
		INTERACT, ATTACK, INTERACT_AT
	}

}
