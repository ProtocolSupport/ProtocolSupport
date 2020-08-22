package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_16r2;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddleRecipeBookState;
import protocolsupport.protocol.serializer.MiscSerializer;

public class RecipeBookState extends MiddleRecipeBookState {

	public RecipeBookState(MiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void readClientData(ByteBuf clientdata) {
		bookType = MiscSerializer.readVarIntEnum(clientdata, RecipeBookType.CONSTANT_LOOKUP);
		bookOpen = clientdata.readBoolean();
		bookFiltering = clientdata.readBoolean();
	}

}
