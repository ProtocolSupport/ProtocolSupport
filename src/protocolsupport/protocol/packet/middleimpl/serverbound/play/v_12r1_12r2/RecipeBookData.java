package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_12r1_12r2;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.codec.VarNumberCodec;
import protocolsupport.protocol.packet.middle.ServerBoundMiddlePacket;

public class RecipeBookData extends ServerBoundMiddlePacket {

	public RecipeBookData(MiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void read(ByteBuf clientdata) {
		int type = VarNumberCodec.readVarInt(clientdata);
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
	protected void write() {
	}

}
