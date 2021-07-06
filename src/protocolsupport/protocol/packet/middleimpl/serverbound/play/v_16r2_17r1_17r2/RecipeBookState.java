package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_16r2_17r1_17r2;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.codec.MiscDataCodec;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddleRecipeBookState;

public class RecipeBookState extends MiddleRecipeBookState {

	public RecipeBookState(MiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void read(ByteBuf clientdata) {
		bookType = MiscDataCodec.readVarIntEnum(clientdata, RecipeBookType.CONSTANT_LOOKUP);
		bookOpen = clientdata.readBoolean();
		bookFiltering = clientdata.readBoolean();
	}

}
