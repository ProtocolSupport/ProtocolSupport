package protocolsupport.protocol.pipeline;

import java.util.Arrays;
import java.util.NoSuchElementException;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.PacketData;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.packet.middleimpl.ServerBoundPacketData;

public abstract class IPacketIdCodec {

	protected final PacketIdTransformerRegistry registry = new PacketIdTransformerRegistry(ClientBoundPacketType.getValuesCount());

	public abstract int readPacketId(ByteBuf from);

	protected abstract void writePacketId(PacketData<?, ?> to, int packetId);

	public void writeServerBoundPacketId(ServerBoundPacketData to) {
		writePacketId(to, to.getPacketType().getId());
	}

	public void writeClientBoundPacketId(ClientBoundPacketData to) {
		writePacketId(to, registry.getPacketId(to.getPacketType()));
	}


	protected static class PacketIdTransformerRegistry {

		protected static final int NO_ENTRY = -1;

		protected final int[] registry;

		public PacketIdTransformerRegistry(int size) {
			this.registry = new int[size];
			Arrays.fill(registry, NO_ENTRY);
		}

		public void register(ClientBoundPacketType type, int newPacketId) {
			registry[type.ordinal()] = newPacketId;
		}

		public int getPacketId(ClientBoundPacketType type) {
			int id = registry[type.ordinal()];
			if (id == NO_ENTRY) {
				throw new NoSuchElementException("No packet id found for and packet id " + type.getId());
			}
			return id;
		}
	}

}
