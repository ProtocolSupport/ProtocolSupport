package protocolsupport.protocol.pipeline;

import java.util.Arrays;
import java.util.NoSuchElementException;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.packet.PacketType;

public abstract class IPacketCodec {

	protected final PacketIdTransformerRegistry registry = new PacketIdTransformerRegistry();

	public abstract int readPacketId(ByteBuf from);

	protected abstract void writePacketId(ByteBuf to, int packetId);

	public void writePacketId(ByteBuf to, PacketType type) {
		writePacketId(to, registry.getPacketId(type));
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
