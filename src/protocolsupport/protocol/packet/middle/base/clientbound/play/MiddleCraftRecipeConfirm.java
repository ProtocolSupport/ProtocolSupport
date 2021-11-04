package protocolsupport.protocol.packet.middle.base.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.codec.StringCodec;
import protocolsupport.protocol.packet.middle.base.clientbound.ClientBoundMiddlePacket;

public abstract class MiddleCraftRecipeConfirm extends ClientBoundMiddlePacket {

	protected MiddleCraftRecipeConfirm(IMiddlePacketInit init) {
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
