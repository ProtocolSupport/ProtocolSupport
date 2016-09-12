package protocolsupport.protocol.serializer;

import io.netty.buffer.Unpooled;
import net.minecraft.server.v1_10_R1.PacketDataSerializer;
import protocolsupport.api.ProtocolVersion;

public class ChainedProtocolSupportPacketDataSerializer extends ProtocolSupportPacketDataSerializer {

	protected final PacketDataSerializer nativeserializer = new PacketDataSerializer(this);

	public ChainedProtocolSupportPacketDataSerializer() {
		super(Unpooled.buffer(), ProtocolVersion.getLatest());
	}

	public PacketDataSerializer getNativeSerializer() {
		return nativeserializer;
	}

}
