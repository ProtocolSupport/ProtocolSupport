package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_16r1_16r2;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddleJigsawUpdate;
import protocolsupport.protocol.serializer.PositionSerializer;
import protocolsupport.protocol.serializer.StringSerializer;

public class JigsawUpdate extends MiddleJigsawUpdate {

	public JigsawUpdate(MiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void readClientData(ByteBuf clientdata) {
		PositionSerializer.readPositionTo(clientdata, position);
		name = StringSerializer.readVarIntUTF8String(clientdata, Short.MAX_VALUE);
		target = StringSerializer.readVarIntUTF8String(clientdata, Short.MAX_VALUE);
		pool = StringSerializer.readVarIntUTF8String(clientdata, Short.MAX_VALUE);
		finalState = StringSerializer.readVarIntUTF8String(clientdata, Short.MAX_VALUE);
		jointType = StringSerializer.readVarIntUTF8String(clientdata, Short.MAX_VALUE);
	}

}
