package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_8;

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
		if (clientdata.readBoolean()) {
			PositionSerializer.skipPosition(clientdata);
		}
	}

}
