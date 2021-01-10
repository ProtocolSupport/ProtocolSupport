package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_13_14r1_14r2_15_16r1_16r2;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddleCustomPayload;
import protocolsupport.protocol.serializer.MiscSerializer;
import protocolsupport.protocol.serializer.StringSerializer;

public class CustomPayload extends MiddleCustomPayload {

	public CustomPayload(MiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void read(ByteBuf clientdata) {
		tag = StringSerializer.readVarIntUTF8String(clientdata, Short.MAX_VALUE);
		data = MiscSerializer.readAllBytesSlice(clientdata);
	}

	@Override
	protected String getServerTag(String tag) {
		return tag;
	}

}
