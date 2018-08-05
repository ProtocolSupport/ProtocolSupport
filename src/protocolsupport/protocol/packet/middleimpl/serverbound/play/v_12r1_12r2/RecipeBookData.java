package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_12r1_12r2;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middle.ServerBoundMiddlePacket;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddleRecipeBookData;
import protocolsupport.protocol.packet.middleimpl.ServerBoundPacketData;
import protocolsupport.protocol.serializer.MiscSerializer;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableEmptyList;

public class RecipeBookData extends ServerBoundMiddlePacket {

	public RecipeBookData(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	public RecyclableCollection<ServerBoundPacketData> toNative() {
		return RecyclableEmptyList.get();
	}

	@Override
	public void readFromClientData(ByteBuf clientdata) {
		MiddleRecipeBookData.Type type = MiscSerializer.readVarIntEnum(clientdata, MiddleRecipeBookData.Type.CONSTANT_LOOKUP);
		switch (type) {
			case DISPLAYED_RECIPE: {
				clientdata.readInt();
				break;
			}
			case RECIPE_BOOK_STATUS: {
				clientdata.readBoolean();
				clientdata.readBoolean();
				break;
			}
		}
	}

}
