package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_13;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddleRecipeBookData;
import protocolsupport.protocol.serializer.MiscSerializer;
import protocolsupport.protocol.serializer.StringSerializer;

public class RecipeBookData extends MiddleRecipeBookData {

	public RecipeBookData(ConnectionImpl connection) {
		super(connection);
		blastingRecipeBookOpen = false;
		blastingRecipeBookFiltering = false;
		smokingRecipeBookOpen = false;
		smokingRecipeBookFiltering = false;
	}

	@Override
	public void readFromClientData(ByteBuf clientdata) {
		type = MiscSerializer.readVarIntEnum(clientdata, Type.CONSTANT_LOOKUP);
		switch (type) {
			case DISPLAYED_RECIPE: {
				recipeId = StringSerializer.readString(clientdata, version);
				break;
			}
			case RECIPE_BOOK_STATUS: {
				craftRecipeBookOpen = clientdata.readBoolean();
				craftRecipeBookFiltering = clientdata.readBoolean();
				smeltingRecipeBookOpen = clientdata.readBoolean();
				smeltingRecipeBookFiltering = clientdata.readBoolean();
				break;
			}
		}
	}

}
