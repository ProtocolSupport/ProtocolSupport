package protocolsupport.protocol.packet.middleimpl;

import java.io.IOException;

import io.netty.buffer.ByteBuf;
import io.netty.util.Recycler;
import net.minecraft.server.v1_10_R1.Packet;
import net.minecraft.server.v1_10_R1.PacketDataSerializer;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.ServerBoundPacket;
import protocolsupport.protocol.serializer.ProtocolSupportPacketDataSerializer;
import protocolsupport.utils.netty.Allocator;
import protocolsupport.utils.recyclable.Recyclable;

public class PacketCreator extends ProtocolSupportPacketDataSerializer implements Recyclable {

	private static final Recycler<PacketCreator> RECYCLER = new Recycler<PacketCreator>() {
		@Override
		protected PacketCreator newObject(Recycler.Handle handle) {
			return new PacketCreator(handle);
		}
	};

	public static PacketCreator create(ServerBoundPacket packet) {
		PacketCreator packetdata = RECYCLER.get();
		packetdata.packet = packet;
		return packetdata;
	}

	private final Recycler.Handle handle;
	private PacketCreator(Recycler.Handle handle) {
		super(Allocator.allocateUnpooledBuffer(), ProtocolVersion.getLatest());
		this.handle = handle;
	}

	private final PacketDataSerializer nativeSerializer = new PacketDataSerializer(this);

	private ServerBoundPacket packet;

	public ServerBoundPacket getPacketType() {
		return packet;
	}

	public Packet<?> create() throws Exception {
		Packet<?> npacket = packet.get();
		npacket.a(nativeSerializer);
		return npacket;
	}

	@Override
	public void setBuf(ByteBuf buf) {
	}

	@Override
	protected void finalize() {
		release();
	}

	@Override
	public void close() throws IOException {
		recycle();
	}

	@Override
	public void recycle() {
		clear();
		packet = null;
		RECYCLER.recycle(this, handle);
	}

}
