package protocolsupport.protocol.serializer;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.utils.netty.ReplayingDecoderBuffer;

public class ReplayingSupportPacketDataSerializer extends ProtocolSupportPacketDataSerializer {

	protected final ReplayingDecoderBuffer replaying;

	public ReplayingSupportPacketDataSerializer(ProtocolVersion version) {
		super(new ReplayingDecoderBuffer(), version);
		replaying = (ReplayingDecoderBuffer) this.buf;
	}

	@Override
	public void setBuf(ByteBuf buf) {
		replaying.setCumulation(buf);
	}

}
