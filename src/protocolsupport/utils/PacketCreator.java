package protocolsupport.utils;

import java.io.IOException;

import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketListener;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.PacketDataSerializer;

public class PacketCreator extends PacketDataSerializer {

	private final Packet<? extends PacketListener> packet;
	public PacketCreator(Packet<? extends PacketListener> packet) {
		super(Allocator.allocateBuffer(), ProtocolVersion.getLatest());
		this.packet = packet;
	}

	public Packet<? extends PacketListener> create() throws Exception {
		try {
			packet.a(this);
			return packet;
		} finally {
			release();
		}
	}

	public static Packet<? extends PacketListener> createWithData(Packet<? extends PacketListener> packet, PacketDataSerializer serializer) throws IOException {
		packet.a(serializer);
		return packet;
	}

}
