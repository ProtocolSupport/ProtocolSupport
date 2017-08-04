package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_12r2;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddleCraftingGrid;
import protocolsupport.protocol.serializer.VarNumberSerializer;

public class CraftingGrid extends MiddleCraftingGrid {

	@Override
	public void readFromClientData(ByteBuf clientdata) {
		windowId = clientdata.readUnsignedByte();
		recipeId = VarNumberSerializer.readVarInt(clientdata);
		all = clientdata.readBoolean();
	}

}
