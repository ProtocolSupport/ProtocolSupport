package protocolsupport.protocol.packet.middle.serverbound.play;

import protocolsupport.protocol.packet.ServerBoundPacket;
import protocolsupport.protocol.packet.middle.ServerBoundMiddlePacket;
import protocolsupport.protocol.packet.middleimpl.ServerBoundPacketData;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;


public abstract class MiddleCraftingGrid extends ServerBoundMiddlePacket {

	protected int windowId;
	protected int recipeId;
	protected boolean all;

	@Override
	public RecyclableCollection<ServerBoundPacketData> toNative() {
		ServerBoundPacketData creator = ServerBoundPacketData.create(ServerBoundPacket.PLAY_PREPARE_CRAFTING_GRID);
		creator.writeByte(windowId);
		VarNumberSerializer.writeVarInt(creator, recipeId);
		creator.writeBoolean(all);
		return RecyclableSingletonList.create(creator);
	}

}
