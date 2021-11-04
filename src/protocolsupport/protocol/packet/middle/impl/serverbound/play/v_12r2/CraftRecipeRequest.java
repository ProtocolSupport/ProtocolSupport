package protocolsupport.protocol.packet.middle.impl.serverbound.play.v_12r2;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.codec.VarNumberCodec;
import protocolsupport.protocol.packet.middle.base.serverbound.ServerBoundMiddlePacket;
import protocolsupport.protocol.packet.middle.impl.serverbound.IServerboundMiddlePacketV12r2;

public class CraftRecipeRequest extends ServerBoundMiddlePacket implements IServerboundMiddlePacketV12r2 {

	public CraftRecipeRequest(IMiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void read(ByteBuf clientdata) {
		clientdata.readUnsignedByte();
		VarNumberCodec.readVarInt(clientdata);
		clientdata.readBoolean();
	}

	@Override
	protected void write() {
	}

}
