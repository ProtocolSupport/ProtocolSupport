package protocolsupport.protocol.packet.middle.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.serializer.StringSerializer;

public abstract class MiddleCraftRecipeConfirm extends ClientBoundMiddlePacket {

	public MiddleCraftRecipeConfirm(MiddlePacketInit init) {
		super(init);
	}

	protected int windowId;
	protected String recipeId;

	@Override
	protected void decode(ByteBuf serverdata) {
		windowId = serverdata.readUnsignedByte();
		recipeId = StringSerializer.readVarIntUTF8String(serverdata);
	}

}
