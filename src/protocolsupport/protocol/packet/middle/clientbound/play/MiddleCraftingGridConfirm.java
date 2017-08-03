package protocolsupport.protocol.packet.middle.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.serializer.VarNumberSerializer;

public abstract class MiddleCraftingGridConfirm extends ClientBoundMiddlePacket {

	protected int windowId;
	protected int recipeId;

	@Override
	public void readFromServerData(ByteBuf serverdata) {
		windowId = serverdata.readUnsignedByte();
		recipeId = VarNumberSerializer.readVarInt(serverdata);
	}

}
