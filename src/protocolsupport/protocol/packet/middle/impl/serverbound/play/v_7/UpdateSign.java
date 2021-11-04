package protocolsupport.protocol.packet.middle.impl.serverbound.play.v_7;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.codec.PositionCodec;
import protocolsupport.protocol.codec.StringCodec;
import protocolsupport.protocol.packet.middle.base.serverbound.play.MiddleUpdateSign;
import protocolsupport.protocol.packet.middle.impl.serverbound.IServerboundMiddlePacketV7;

public class UpdateSign extends MiddleUpdateSign implements IServerboundMiddlePacketV7 {

	public UpdateSign(IMiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void read(ByteBuf clientdata) {
		PositionCodec.readPositionISI(clientdata, position);
		for (int i = 0; i < lines.length; i++) {
			lines[i] = StringCodec.readVarIntUTF8String(clientdata, 15);
		}
	}

}
