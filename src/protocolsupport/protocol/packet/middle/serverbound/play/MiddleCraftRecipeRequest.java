package protocolsupport.protocol.packet.middle.serverbound.play;

import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middle.ServerBoundMiddlePacket;
import protocolsupport.protocol.packet.middleimpl.ServerBoundPacketData;
import protocolsupport.protocol.serializer.StringSerializer;

public abstract class MiddleCraftRecipeRequest extends ServerBoundMiddlePacket {

	public MiddleCraftRecipeRequest(MiddlePacketInit init) {
		super(init);
	}

	protected int windowId;
	protected String recipeId;
	protected boolean all;

	@Override
	protected void writeToServer() {
		ServerBoundPacketData craftreciperequest = ServerBoundPacketData.create(PacketType.SERVERBOUND_PLAY_CRAFT_RECIPE_REQUEST);
		craftreciperequest.writeByte(windowId);
		StringSerializer.writeVarIntUTF8String(craftreciperequest, recipeId);
		craftreciperequest.writeBoolean(all);
		codec.read(craftreciperequest);
	}

}
