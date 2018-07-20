package protocolsupport.protocol.packet.middle.serverbound.play;

import protocolsupport.protocol.packet.ServerBoundPacket;
import protocolsupport.protocol.packet.middle.ServerBoundMiddlePacket;
import protocolsupport.protocol.packet.middleimpl.ServerBoundPacketData;
import protocolsupport.protocol.serializer.MiscSerializer;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.utils.EnumConstantLookups;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public abstract class MiddleRecipeBookData extends ServerBoundMiddlePacket {

	protected Type type;
	protected String recipeId;
	protected boolean craftRecipeBookOpen;
	protected boolean craftRecipeBookFiltering;
	protected boolean smeltingRecipeBookOpen;
	protected boolean smeltingRecipeBookFiltering;

	@Override
	public RecyclableCollection<ServerBoundPacketData> toNative() {
		ServerBoundPacketData creator = ServerBoundPacketData.create(ServerBoundPacket.PLAY_RECIPE_BOOK_DATA);
		MiscSerializer.writeVarIntEnum(creator, type);
		switch (type) {
			case DISPLAYED_RECIPE: {
				StringSerializer.writeString(creator, ProtocolVersionsHelper.LATEST_PC, recipeId);
				break;
			}
			case RECIPE_BOOK_STATUS: {
				creator.writeBoolean(craftRecipeBookOpen);
				creator.writeBoolean(craftRecipeBookFiltering);
				creator.writeBoolean(smeltingRecipeBookOpen);
				creator.writeBoolean(smeltingRecipeBookFiltering);
				break;
			}
		}
		return RecyclableSingletonList.create(creator);
	}

	public static enum Type {
		DISPLAYED_RECIPE, RECIPE_BOOK_STATUS;
		public static final EnumConstantLookups.EnumConstantLookup<Type> CONSTANT_LOOKUP = new EnumConstantLookups.EnumConstantLookup<>(Type.class);
	}

}
