package protocolsupport.protocol.transformer.middlepacketimpl;

import io.netty.util.Recycler;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketListener;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.PacketDataSerializer;
import protocolsupport.utils.netty.Allocator;

public class PacketCreator extends PacketDataSerializer {

	private static final Recycler<PacketCreator> RECYCLER = new Recycler<PacketCreator>() {
		@Override
		protected PacketCreator newObject(Recycler.Handle handle) {
			return new PacketCreator(handle);
		}
	};

	public static PacketCreator create(Packet<? extends PacketListener> packet) {
		PacketCreator packetdata = RECYCLER.get();
		packetdata.packet = packet;
		return packetdata;
	}

	private final Recycler.Handle handle;
	private PacketCreator(Recycler.Handle handle) {
		super(Allocator.allocateUnpooledBuffer(), ProtocolVersion.getLatest());
		this.handle = handle;
	}

	private Packet<?> packet;

	public Packet<?> create() throws Exception {
		try {
			packet.a(this);
			return packet;
		} finally {
			clear();
			packet = null;
			RECYCLER.recycle(this, handle);
		}
	}

	@Override
	protected void finalize() {
		release();
	}

}
