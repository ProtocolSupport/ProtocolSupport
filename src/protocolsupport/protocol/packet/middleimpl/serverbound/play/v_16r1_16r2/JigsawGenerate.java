package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_16r1_16r2;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.codec.PositionCodec;
import protocolsupport.protocol.codec.VarNumberCodec;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddleJigsawGenerate;

public class JigsawGenerate extends MiddleJigsawGenerate {

	public JigsawGenerate(MiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void read(ByteBuf clientdata) {
		PositionCodec.readPosition(clientdata, position);
		levels = VarNumberCodec.readVarInt(clientdata);
		keep = clientdata.readBoolean();
	}

}
