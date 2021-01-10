package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_9r1_9r2_10_11_12r1_12r2;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddleTabComplete;
import protocolsupport.protocol.serializer.PositionSerializer;
import protocolsupport.protocol.serializer.StringSerializer;

public class TabComplete extends MiddleTabComplete {

	public TabComplete(MiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void read(ByteBuf clientdata) {
		id = 0;
		string = StringSerializer.readVarIntUTF8String(clientdata, 256);
		if (string.equals("/")) {
			string = "";
		}
		clientdata.readBoolean();
		if (clientdata.readBoolean()) {
			PositionSerializer.skipPosition(clientdata);
		}
	}

}
