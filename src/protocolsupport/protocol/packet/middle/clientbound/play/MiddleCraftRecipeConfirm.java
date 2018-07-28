package protocolsupport.protocol.packet.middle.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;

public abstract class MiddleCraftRecipeConfirm extends ClientBoundMiddlePacket {

	protected int windowId;
	protected String recipeId;

	@Override
	public void readFromServerData(ByteBuf serverdata) {
		windowId = serverdata.readUnsignedByte();
		recipeId = StringSerializer.readString(serverdata, ProtocolVersionsHelper.LATEST_PC);
	}

}
