package protocolsupport.protocol.packet.middle.serverbound.play;

import protocolsupport.protocol.packet.ServerBoundPacket;
import protocolsupport.protocol.packet.middle.ServerBoundMiddlePacket;
import protocolsupport.protocol.packet.middleimpl.ServerBoundPacketData;
import protocolsupport.protocol.serializer.MiscSerializer;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public abstract class MiddleCraftingBookData extends ServerBoundMiddlePacket {

	protected Type type;
	protected int recipeId;
	protected boolean craftingBookOpen;
	protected boolean recipeFilterEnabled;

	@Override
	public RecyclableCollection<ServerBoundPacketData> toNative() {
		ServerBoundPacketData creator = ServerBoundPacketData.create(ServerBoundPacket.PLAY_CRAFTING_BOOK_DATA);
		MiscSerializer.writeEnum(creator, type);
		switch (type) {
			case DISPLAYED_RECIPE: {
				creator.writeInt(recipeId);
				break;
			}
			case CRAFTING_BOOK_STATUS: {
				creator.writeBoolean(craftingBookOpen);
				creator.writeBoolean(recipeFilterEnabled);
				break;
			}
		}
		return RecyclableSingletonList.create(creator);
	}

	protected static enum Type {
		DISPLAYED_RECIPE, CRAFTING_BOOK_STATUS;
	}

}
