package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_15;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddleJigsawUpdate;
import protocolsupport.protocol.serializer.PositionSerializer;
import protocolsupport.protocol.serializer.StringSerializer;

//TODO: attempt to implement
public class JigsawUpdate extends MiddleJigsawUpdate {

	public JigsawUpdate(MiddlePacketInit init) {
		super(init);
		jointType = "aligned";
	}

	@Override
	protected void writeToServer() {
	}

	@Override
	protected void readClientData(ByteBuf clientdata) {
		PositionSerializer.readPositionTo(clientdata, position);
		StringSerializer.readVarIntUTF8String(clientdata, Short.MAX_VALUE); //attachment type
		pool = StringSerializer.readVarIntUTF8String(clientdata, Short.MAX_VALUE);
		finalState = StringSerializer.readVarIntUTF8String(clientdata, Short.MAX_VALUE);
	}

}
