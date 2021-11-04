package protocolsupport.protocol.packet.middle.impl.serverbound.play.v_8;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.codec.PositionCodec;
import protocolsupport.protocol.codec.StringCodec;
import protocolsupport.protocol.packet.middle.base.serverbound.play.MiddleTabComplete;
import protocolsupport.protocol.packet.middle.impl.serverbound.IServerboundMiddlePacketV8;

public class TabComplete extends MiddleTabComplete implements IServerboundMiddlePacketV8 {

	public TabComplete(IMiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void read(ByteBuf clientdata) {
		id = 0;
		string = StringCodec.readVarIntUTF8String(clientdata, 256);
		if (clientdata.readBoolean()) {
			PositionCodec.skipPositionL(clientdata);
		}
	}

}
