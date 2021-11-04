package protocolsupport.protocol.pipeline;

import java.util.Arrays;
import java.util.NoSuchElementException;

import protocolsupport.protocol.packet.ClientBoundPacketData;
import protocolsupport.protocol.packet.ClientBoundPacketType;

public abstract class AbstractPacketIdCodec implements IPacketIdCodec {

	protected final ClientboundPacketIdTransformerRegistry registry = new ClientboundPacketIdTransformerRegistry(ClientBoundPacketType.getValuesCount());

	protected abstract void writeClientboundPacketId(ClientBoundPacketData to, int packetId);

	@Override
	public void writeClientBoundPacketId(ClientBoundPacketData to) {
		writeClientboundPacketId(to, registry.getPacketId(to.getPacketType()));
	}


	protected static class ClientboundPacketIdTransformerRegistry {

		protected static final int NO_ENTRY = -1;

		protected final int[] registry;

		public ClientboundPacketIdTransformerRegistry(int size) {
			this.registry = new int[size];
			Arrays.fill(registry, NO_ENTRY);
		}

		public void register(ClientBoundPacketType type, int newPacketId) {
			registry[type.ordinal()] = newPacketId;
		}

		public int getPacketId(ClientBoundPacketType type) {
			int id = registry[type.ordinal()];
			if (id == NO_ENTRY) {
				throw new NoSuchElementException("No packet id found for clientbound packet type " + type.name());
			}
			return id;
		}
	}

}
