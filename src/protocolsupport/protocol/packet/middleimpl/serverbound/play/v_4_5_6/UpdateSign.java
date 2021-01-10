package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_4_5_6;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddleUpdateSign;
import protocolsupport.protocol.serializer.PositionSerializer;
import protocolsupport.protocol.serializer.StringSerializer;

public class UpdateSign extends MiddleUpdateSign {

	public UpdateSign(MiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void read(ByteBuf clientdata) {
		PositionSerializer.readLegacyPositionSTo(clientdata, position);
		for (int i = 0; i < lines.length; i++) {
			lines[i] = StringSerializer.readShortUTF16BEString(clientdata, 15);
		}
	}

}
