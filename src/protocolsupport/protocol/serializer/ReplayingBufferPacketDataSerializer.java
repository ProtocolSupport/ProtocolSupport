package protocolsupport.protocol.serializer;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.utils.netty.ReplayingDecoderBuffer;

public class ReplayingBufferPacketDataSerializer extends PacketDataSerializer {

	public static ReplayingBufferPacketDataSerializer create(ProtocolVersion version) {
		return new ReplayingBufferPacketDataSerializer(new ReplayingDecoderBuffer(), version);
	}

	private final ReplayingDecoderBuffer wrappingbuf;

	protected ReplayingBufferPacketDataSerializer(ReplayingDecoderBuffer buf, ProtocolVersion version) {
		super(buf, version);
		this.wrappingbuf = buf;
	}

	public void setBuf(ByteBuf buf) {
		wrappingbuf.setCumulation(buf);
	}

}
