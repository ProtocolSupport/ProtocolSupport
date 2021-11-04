package protocolsupport.protocol.packet.middle.impl.serverbound.play.v_7;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.codec.StringCodec;
import protocolsupport.protocol.packet.middle.base.serverbound.play.MiddleTabComplete;
import protocolsupport.protocol.packet.middle.impl.serverbound.IServerboundMiddlePacketV7;

public class TabComplete extends MiddleTabComplete implements IServerboundMiddlePacketV7 {

	public TabComplete(IMiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void read(ByteBuf clientdata) {
		id = 0;
		string = StringCodec.readVarIntUTF8String(clientdata, 256);
		if (string.equals("/")) {
			string = "";
		}
	}

}
