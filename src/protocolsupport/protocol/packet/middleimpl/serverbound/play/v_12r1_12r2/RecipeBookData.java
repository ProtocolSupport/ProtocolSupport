package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_12r1_12r2;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.packet.middle.ServerBoundMiddlePacket;
import protocolsupport.protocol.serializer.VarNumberSerializer;

public class RecipeBookData extends ServerBoundMiddlePacket {

	public RecipeBookData(MiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void readClientData(ByteBuf clientdata) {
		int type = VarNumberSerializer.readVarInt(clientdata);
		switch (type) {
			case 0: {
				clientdata.readInt();
				break;
			}
			case 1: {
				clientdata.readBoolean();
				clientdata.readBoolean();
				break;
			}
		}
	}

	@Override
	protected void writeToServer() {
	}

}
