package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_12r2;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.packet.middle.ServerBoundMiddlePacket;
import protocolsupport.protocol.serializer.VarNumberSerializer;

public class CraftRecipeRequest extends ServerBoundMiddlePacket {

	public CraftRecipeRequest(MiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void read(ByteBuf clientdata) {
		clientdata.readUnsignedByte();
		VarNumberSerializer.readVarInt(clientdata);
		clientdata.readBoolean();
	}

	@Override
	protected void write() {
	}

}
