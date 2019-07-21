package protocolsupport.protocol.packet.middle.serverbound.play;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middle.ServerBoundMiddlePacket;
import protocolsupport.protocol.packet.middleimpl.IPacketData;
import protocolsupport.protocol.packet.middleimpl.ServerBoundPacketData;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public abstract class MiddleCraftRecipeRequest extends ServerBoundMiddlePacket {

	public MiddleCraftRecipeRequest(ConnectionImpl connection) {
		super(connection);
	}

	protected int windowId;
	protected String recipeId;
	protected boolean all;

	@Override
	public RecyclableCollection<? extends IPacketData> toNative() {
		ServerBoundPacketData creator = ServerBoundPacketData.create(PacketType.SERVERBOUND_PLAY_CRAFT_RECIPE_REQUEST);
		creator.writeByte(windowId);
		StringSerializer.writeVarIntUTF8String(creator, recipeId);
		creator.writeBoolean(all);
		return RecyclableSingletonList.create(creator);
	}

}
