package protocolsupport.protocol.pipeline;

import java.util.Arrays;
import java.util.NoSuchElementException;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.packet.PacketData;
import protocolsupport.protocol.packet.PacketType;

public abstract class IPacketIdCodec {

	protected final PacketIdTransformerRegistry registry = new PacketIdTransformerRegistry();

	public abstract int readPacketId(ByteBuf from);

	protected abstract void writePacketId(PacketData<?> to, int packetId);

	public void writeServerBoundPacketId(PacketData<?> to) {
		writePacketId(to, to.getPacketType().getId());
	}

	public void writeClientBoundPacketId(PacketData<?> to) {
		writePacketId(to, registry.getPacketId(to.getPacketType()));
	}


	protected static class PacketIdTransformerRegistry {
		protected static final int NO_ENTRY = -1;

		protected final int[] registry = new int[PacketType.getCount()];
		{
			Arrays.fill(registry, NO_ENTRY);
		}

		public void register(PacketType type, int newPacketId) {
			registry[type.getOrdinal()] = newPacketId;
		}

		public int getPacketId(PacketType type) {
			int id = registry[type.getOrdinal()];
			if (id == NO_ENTRY) {
				throw new NoSuchElementException("No packet id found for and packet id " + type.getId());
			}
			return id;
		}
	}

}
