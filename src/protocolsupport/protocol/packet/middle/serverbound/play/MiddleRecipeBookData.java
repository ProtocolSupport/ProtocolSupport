package protocolsupport.protocol.packet.middle.serverbound.play;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middle.ServerBoundMiddlePacket;
import protocolsupport.protocol.packet.middleimpl.ServerBoundPacketData;
import protocolsupport.protocol.serializer.MiscSerializer;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.utils.EnumConstantLookups;

public abstract class MiddleRecipeBookData extends ServerBoundMiddlePacket {

	public MiddleRecipeBookData(ConnectionImpl connection) {
		super(connection);
	}

	protected Type type;
	protected String recipeId;
	protected boolean craftRecipeBookOpen;
	protected boolean craftRecipeBookFiltering;
	protected boolean smeltingRecipeBookOpen;
	protected boolean smeltingRecipeBookFiltering;
	protected boolean blastingRecipeBookOpen;
	protected boolean blastingRecipeBookFiltering;
	protected boolean smokingRecipeBookOpen;
	protected boolean smokingRecipeBookFiltering;

	@Override
	public void writeToServer() {
		ServerBoundPacketData recipebookdata = ServerBoundPacketData.create(PacketType.SERVERBOUND_PLAY_RECIPE_BOOK_DATA);
		MiscSerializer.writeVarIntEnum(recipebookdata, type);
		switch (type) {
			case DISPLAYED_RECIPE: {
				StringSerializer.writeVarIntUTF8String(recipebookdata, recipeId);
				break;
			}
			case RECIPE_BOOK_STATUS: {
				recipebookdata.writeBoolean(craftRecipeBookOpen);
				recipebookdata.writeBoolean(craftRecipeBookFiltering);
				recipebookdata.writeBoolean(smeltingRecipeBookOpen);
				recipebookdata.writeBoolean(smeltingRecipeBookFiltering);
				recipebookdata.writeBoolean(blastingRecipeBookOpen);
				recipebookdata.writeBoolean(blastingRecipeBookFiltering);
				recipebookdata.writeBoolean(smokingRecipeBookOpen);
				recipebookdata.writeBoolean(smokingRecipeBookFiltering);
				break;
			}
		}
		codec.read(recipebookdata);
	}

	public static enum Type {
		DISPLAYED_RECIPE, RECIPE_BOOK_STATUS;
		public static final EnumConstantLookups.EnumConstantLookup<Type> CONSTANT_LOOKUP = new EnumConstantLookups.EnumConstantLookup<>(Type.class);
	}

}
