package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_13_14r1_14r2;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleCraftRecipeConfirm;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.StringSerializer;

public class CraftRecipeConfirm extends MiddleCraftRecipeConfirm {

	public CraftRecipeConfirm(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	public void writeToClient() {
		ClientBoundPacketData craftrecipeconfirm = ClientBoundPacketData.create(PacketType.CLIENTBOUND_PLAY_CRAFT_RECIPE_CONFIRM);
		craftrecipeconfirm.writeByte(windowId);
		StringSerializer.writeVarIntUTF8String(craftrecipeconfirm, recipeId);
		codec.write(craftrecipeconfirm);
	}

}
