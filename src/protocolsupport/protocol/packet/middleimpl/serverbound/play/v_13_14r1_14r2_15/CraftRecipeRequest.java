package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_13_14r1_14r2_15;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddleCraftRecipeRequest;
import protocolsupport.protocol.serializer.StringSerializer;

public class CraftRecipeRequest extends MiddleCraftRecipeRequest {

	public CraftRecipeRequest(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	public void readClientData(ByteBuf clientdata) {
		windowId = clientdata.readUnsignedByte();
		recipeId = StringSerializer.readVarIntUTF8String(clientdata, Short.MAX_VALUE);
		all = clientdata.readBoolean();
	}

}
