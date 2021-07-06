package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_13_14r1_14r2_15_16r1_16r2_17r1_17r2;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.codec.MiscDataCodec;
import protocolsupport.protocol.codec.StringCodec;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddleCustomPayload;

public class CustomPayload extends MiddleCustomPayload {

	public CustomPayload(MiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void read(ByteBuf clientdata) {
		tag = StringCodec.readVarIntUTF8String(clientdata, Short.MAX_VALUE);
		data = MiscDataCodec.readAllBytesSlice(clientdata);
	}

	@Override
	protected String getServerTag(String tag) {
		return tag;
	}

}
