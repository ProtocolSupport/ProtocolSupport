package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_12;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddleCraftingBookData;
import protocolsupport.protocol.serializer.MiscSerializer;

public class CraftingBookData extends MiddleCraftingBookData {

	@Override
	public void readFromClientData(ByteBuf clientdata, ProtocolVersion version) {
		type = MiscSerializer.readEnum(clientdata, Type.class);
		switch (type) {
			case DISPLAYED_RECIPE: {
				recipeId = clientdata.readInt();
				break;
			}
			case CRAFTING_BOOK_STATUS: {
				craftingBookOpen = clientdata.readBoolean();
				recipeFilterEnabled = clientdata.readBoolean();
				break;
			}
		}
	}

}
