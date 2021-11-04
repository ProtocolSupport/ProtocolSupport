package protocolsupport.protocol.packet.middle.base.serverbound.play;

import protocolsupport.protocol.codec.StringCodec;
import protocolsupport.protocol.packet.ServerBoundPacketData;
import protocolsupport.protocol.packet.ServerBoundPacketType;
import protocolsupport.protocol.packet.middle.base.serverbound.ServerBoundMiddlePacket;

public abstract class MiddleCraftRecipeRequest extends ServerBoundMiddlePacket {

	protected MiddleCraftRecipeRequest(IMiddlePacketInit init) {
		super(init);
	}

	protected int windowId;
	protected String recipeId;
	protected boolean all;

	@Override
	protected void write() {
		ServerBoundPacketData craftreciperequest = ServerBoundPacketData.create(ServerBoundPacketType.PLAY_CRAFT_RECIPE_REQUEST);
		craftreciperequest.writeByte(windowId);
		StringCodec.writeVarIntUTF8String(craftreciperequest, recipeId);
		craftreciperequest.writeBoolean(all);
		io.writeServerbound(craftreciperequest);
	}

}
