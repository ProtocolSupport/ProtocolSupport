package protocolsupport.protocol.packet.middle.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.serializer.StringSerializer;

public abstract class MiddleCraftRecipeConfirm extends ClientBoundMiddlePacket {

	public MiddleCraftRecipeConfirm(ConnectionImpl connection) {
		super(connection);
	}

	protected int windowId;
	protected String recipeId;

	@Override
	public void readFromServerData(ByteBuf serverdata) {
		windowId = serverdata.readUnsignedByte();
		recipeId = StringSerializer.readVarIntUTF8String(serverdata);
	}

}
