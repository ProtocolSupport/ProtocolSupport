package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_13_14;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddleCraftRecipeRequest;
import protocolsupport.protocol.serializer.StringSerializer;

public class CraftRecipeRequest extends MiddleCraftRecipeRequest {

	public CraftRecipeRequest(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	public void readFromClientData(ByteBuf clientdata) {
		windowId = clientdata.readUnsignedByte();
		recipeId = StringSerializer.readString(clientdata, version);
		all = clientdata.readBoolean();
	}

}
