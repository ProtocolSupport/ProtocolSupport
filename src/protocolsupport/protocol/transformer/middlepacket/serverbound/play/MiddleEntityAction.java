package protocolsupport.protocol.transformer.middlepacket.serverbound.play;

import net.minecraft.server.v1_8_R3.Packet;
import protocolsupport.protocol.ServerBoundPacket;
import protocolsupport.protocol.transformer.middlepacket.ServerBoundMiddlePacket;
import protocolsupport.protocol.transformer.middlepacketimpl.PacketCreator;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public abstract class MiddleEntityAction extends ServerBoundMiddlePacket {

	protected int entityId;
	protected int actionId;
	protected int jumpBoost;

	@Override
	public RecyclableCollection<? extends Packet<?>> toNative() throws Exception {
		PacketCreator creator = PacketCreator.create(ServerBoundPacket.PLAY_ENTITY_ACTION.get());
		creator.writeVarInt(entityId);
		creator.writeVarInt(actionId);
		creator.writeVarInt(jumpBoost);
		return RecyclableSingletonList.create(creator.create());
	}

}
