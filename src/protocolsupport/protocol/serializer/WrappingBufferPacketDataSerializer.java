package protocolsupport.protocol.serializer;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.utils.netty.WrappingBuffer;

public class WrappingBufferPacketDataSerializer extends PacketDataSerializer {

	public static WrappingBufferPacketDataSerializer create(ProtocolVersion version) {
		return new WrappingBufferPacketDataSerializer(new WrappingBuffer(), version);
	}

	private final WrappingBuffer wrappingbuf;

	protected WrappingBufferPacketDataSerializer(WrappingBuffer buf, ProtocolVersion version) {
		super(buf, version);
		this.wrappingbuf = buf;
	}

	public void setBuf(ByteBuf buf) {
		wrappingbuf.setBuf(buf);
	}

}
