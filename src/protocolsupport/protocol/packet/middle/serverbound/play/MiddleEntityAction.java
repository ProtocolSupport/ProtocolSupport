package protocolsupport.protocol.packet.middle.serverbound.play;

import protocolsupport.protocol.packet.ServerBoundPacket;
import protocolsupport.protocol.packet.middle.ServerBoundMiddlePacket;
import protocolsupport.protocol.packet.middleimpl.ServerBoundPacketData;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public abstract class MiddleEntityAction extends ServerBoundMiddlePacket {

	protected int entityId;
	protected int actionId;
	protected int jumpBoost;

	@Override
	public RecyclableCollection<ServerBoundPacketData> toNative() {
		ServerBoundPacketData creator = MiddleEntityAction.create(entityId, actionId, jumpBoost);
		return RecyclableSingletonList.create(creator);
	}

	public static ServerBoundPacketData create(int entityId, int actionId, int jumpBoost) {
		ServerBoundPacketData creator = ServerBoundPacketData.create(ServerBoundPacket.PLAY_ENTITY_ACTION);
		VarNumberSerializer.writeVarInt(creator, entityId);
		VarNumberSerializer.writeVarInt(creator, actionId);
		VarNumberSerializer.writeVarInt(creator, jumpBoost);
		return creator;
	}

}
