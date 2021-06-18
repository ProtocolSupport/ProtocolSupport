package protocolsupport.protocol.packet.middle.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.codec.StringCodec;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;

public abstract class MiddleCraftRecipeConfirm extends ClientBoundMiddlePacket {

	protected MiddleCraftRecipeConfirm(MiddlePacketInit init) {
		super(init);
	}

	protected int windowId;
	protected String recipeId;

	@Override
	protected void decode(ByteBuf serverdata) {
		windowId = serverdata.readUnsignedByte();
		recipeId = StringCodec.readVarIntUTF8String(serverdata);
	}

}
