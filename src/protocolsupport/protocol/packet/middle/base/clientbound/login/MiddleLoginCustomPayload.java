package protocolsupport.protocol.packet.middle.base.clientbound.login;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.codec.MiscDataCodec;
import protocolsupport.protocol.codec.StringCodec;
import protocolsupport.protocol.codec.VarNumberCodec;
import protocolsupport.protocol.packet.middle.base.clientbound.ClientBoundMiddlePacket;

public abstract class MiddleLoginCustomPayload extends ClientBoundMiddlePacket {

	protected MiddleLoginCustomPayload(IMiddlePacketInit init) {
		super(init);
	}

	protected int id;
	protected String tag;
	protected ByteBuf data;

	@Override
	protected void decode(ByteBuf serverdata) {
		id = VarNumberCodec.readVarInt(serverdata);
		tag = StringCodec.readVarIntUTF8String(serverdata);
		data = MiscDataCodec.readAllBytesSlice(serverdata);
	}

	@Override
	protected void cleanup() {
		tag = null;
		data = null;
	}

}
