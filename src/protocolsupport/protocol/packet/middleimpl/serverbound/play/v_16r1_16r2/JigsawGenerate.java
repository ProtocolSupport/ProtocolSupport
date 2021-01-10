package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_16r1_16r2;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddleJigsawGenerate;
import protocolsupport.protocol.serializer.PositionSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;

public class JigsawGenerate extends MiddleJigsawGenerate {

	public JigsawGenerate(MiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void read(ByteBuf clientdata) {
		PositionSerializer.readPositionTo(clientdata, position);
		levels = VarNumberSerializer.readVarInt(clientdata);
		keep = clientdata.readBoolean();
	}

}
