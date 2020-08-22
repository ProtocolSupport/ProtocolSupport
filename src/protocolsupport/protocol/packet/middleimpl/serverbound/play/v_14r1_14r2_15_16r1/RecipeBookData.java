package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_14r1_14r2_15_16r1;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.packet.middle.ServerBoundMiddlePacket;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddleRecipeBookRecipe;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddleRecipeBookState;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddleRecipeBookState.RecipeBookType;
import protocolsupport.protocol.serializer.MiscSerializer;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.utils.EnumConstantLookups.EnumConstantLookup;

public class RecipeBookData extends ServerBoundMiddlePacket {

	public RecipeBookData(MiddlePacketInit init) {
		super(init);
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
	protected void readClientData(ByteBuf clientdata) {
		type = MiscSerializer.readVarIntEnum(clientdata, Type.CONSTANT_LOOKUP);
		switch (type) {
			case DISPLAYED_RECIPE: {
				recipeId = StringSerializer.readVarIntUTF8String(clientdata, Short.MAX_VALUE);
				break;
			}
			case RECIPE_BOOK_STATUS: {
				craftRecipeBookOpen = clientdata.readBoolean();
				craftRecipeBookFiltering = clientdata.readBoolean();
				smeltingRecipeBookOpen = clientdata.readBoolean();
				smeltingRecipeBookFiltering = clientdata.readBoolean();
				blastingRecipeBookOpen = clientdata.readBoolean();
				blastingRecipeBookFiltering = clientdata.readBoolean();
				smokingRecipeBookOpen = clientdata.readBoolean();
				smokingRecipeBookFiltering = clientdata.readBoolean();
				break;
			}
		}
	}

	@Override
	protected void writeToServer() {
		switch (type) {
			case DISPLAYED_RECIPE: {
				codec.read(MiddleRecipeBookRecipe.create(recipeId));
				break;
			}
			case RECIPE_BOOK_STATUS: {
				codec.read(MiddleRecipeBookState.create(RecipeBookType.CRAFTING, craftRecipeBookOpen, craftRecipeBookFiltering));
				codec.read(MiddleRecipeBookState.create(RecipeBookType.FURNACE, smeltingRecipeBookOpen, smeltingRecipeBookFiltering));
				codec.read(MiddleRecipeBookState.create(RecipeBookType.BLAST_FURNACE, blastingRecipeBookOpen, blastingRecipeBookFiltering));
				codec.read(MiddleRecipeBookState.create(RecipeBookType.SMOKER, smokingRecipeBookOpen, smokingRecipeBookFiltering));
				break;
			}
		}
	}

	protected static enum Type {
		DISPLAYED_RECIPE, RECIPE_BOOK_STATUS;
		public static final EnumConstantLookup<Type> CONSTANT_LOOKUP = new EnumConstantLookup<>(Type.class);
	}

}
