package protocolsupport.protocol.packet.middle.impl.serverbound.play.v_12r1__12r2;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.codec.VarNumberCodec;
import protocolsupport.protocol.packet.middle.base.serverbound.ServerBoundMiddlePacket;
import protocolsupport.protocol.packet.middle.impl.serverbound.IServerboundMiddlePacketV12r1;
import protocolsupport.protocol.packet.middle.impl.serverbound.IServerboundMiddlePacketV12r2;

public class RecipeBookData extends ServerBoundMiddlePacket implements
IServerboundMiddlePacketV12r1,
IServerboundMiddlePacketV12r2 {

	public RecipeBookData(IMiddlePacketInit init) {
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
