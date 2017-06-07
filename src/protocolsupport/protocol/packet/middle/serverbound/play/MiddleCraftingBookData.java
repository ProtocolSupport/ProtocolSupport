package protocolsupport.protocol.packet.middle.serverbound.play;

import java.text.MessageFormat;

import protocolsupport.protocol.packet.ServerBoundPacket;
import protocolsupport.protocol.packet.middle.ServerBoundMiddlePacket;
import protocolsupport.protocol.packet.middleimpl.ServerBoundPacketData;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public abstract class MiddleCraftingBookData extends ServerBoundMiddlePacket {

	protected static final int TYPE_DISPLAYED_RECIPE = 1;
	protected static final int TYPE_CRAFTING_BOOK_STATUS = 2;

	protected int type;
	protected int recipeId;
	protected boolean craftingBookOpen;
	protected boolean recipeFilterEnabled;

	@Override
	public RecyclableCollection<ServerBoundPacketData> toNative() {
		ServerBoundPacketData creator = ServerBoundPacketData.create(ServerBoundPacket.PLAY_CRAFTING_BOOK_DATA);
		VarNumberSerializer.writeVarInt(creator, type);
		switch (type) {
			case TYPE_DISPLAYED_RECIPE: {
				creator.writeInt(recipeId);
				break;
			}
			case TYPE_CRAFTING_BOOK_STATUS: {
				creator.writeBoolean(craftingBookOpen);
				creator.writeBoolean(recipeFilterEnabled);
				break;
			}
			default: {
				throw new IllegalArgumentException(MessageFormat.format("Unknown crafting book data type {0}", type));
			}
		}
		return RecyclableSingletonList.create(creator);
	}

}
