package protocolsupport.protocol.packet.middle.serverbound.play;

import net.minecraft.server.v1_9_R2.Packet;
import net.minecraft.server.v1_9_R2.Vector3f;
import protocolsupport.protocol.packet.ServerBoundPacket;
import protocolsupport.protocol.packet.middle.ServerBoundMiddlePacket;
import protocolsupport.protocol.packet.middleimpl.PacketCreator;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public abstract class MiddleUseEntity extends ServerBoundMiddlePacket {

	protected int entityId;
	protected int action;
	protected Vector3f interactedAt;
	protected int usedHand;

	@Override
	public RecyclableCollection<? extends Packet<?>> toNative() throws Exception {
		PacketCreator creator = PacketCreator.create(ServerBoundPacket.PLAY_USE_ENTITY.get());
		creator.writeVarInt(entityId);
		creator.writeVarInt(action);
		if (action == 2) {
			creator.writeFloat(interactedAt.getX());
			creator.writeFloat(interactedAt.getY());
			creator.writeFloat(interactedAt.getZ());
		}
		creator.writeVarInt(usedHand);
		return RecyclableSingletonList.create(creator.create());
	}

}
