package protocolsupport.protocol.typeremapper.basic;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.locks.StampedLock;
import java.util.function.BiFunction;

import protocolsupport.protocol.typeremapper.basic.CustomPayloadTransformerRegistry.CustomPayloadTransformerTable;
import protocolsupport.protocol.typeremapper.utils.MappingRegistry;
import protocolsupport.protocol.typeremapper.utils.MappingTable;
import protocolsupportbuildprocessor.Preload;

@Preload
public class CustomPayloadTransformerRegistry extends MappingRegistry<CustomPayloadTransformerTable> {

	public static final CustomPayloadTransformerRegistry INSTANCE = new CustomPayloadTransformerRegistry();

	@Override
	protected CustomPayloadTransformerTable createTable() {
		return new CustomPayloadTransformerTable();
	}

	public static class CustomPayloadTransformerTable extends MappingTable {

		protected final StampedLock lock = new StampedLock();

		protected final Map<String, CustomPayloadTransformer> byServerName = new HashMap<>();
		protected final Map<String, CustomPayloadTransformer> byClientName = new HashMap<>();

		public CustomPayloadTransformer getByServerName(String name) {
			long stamp = lock.readLock();
			try {
				return byServerName.get(name);
			} finally {
				lock.unlockRead(stamp);
			}
		}

		public CustomPayloadTransformer getByClientName(String name) {
			long stamp = lock.readLock();
			try {
				return byClientName.get(name);
			} finally {
				lock.unlockRead(stamp);
			}
		}

		public void set(CustomPayloadTransformer transformer) {
			long stamp = lock.writeLock();
			try {
				String serverName = transformer.getServerTag();
				if (byServerName.containsKey(serverName)) {
					throw new IllegalStateException("Server name " + serverName + " is already handled by another transformer");
				}
				String clientName = transformer.getServerTag();
				if (byClientName.containsKey(clientName)) {
					throw new IllegalStateException("Client name " + clientName + " is already handled by another transformer");
				}
				byServerName.put(serverName, transformer);
				byClientName.put(clientName, transformer);
			} finally {
				lock.unlockWrite(stamp);
			}
		}

		public void remove(String name) {
			long stamp = lock.writeLock();
			try {
				CustomPayloadTransformer transformer = byServerName.remove(name);
				if (transformer != null) {
					byClientName.remove(transformer.getClientTag());
				} else {
					transformer = byClientName.remove(name);
					if (transformer != null) {
						byServerName.remove(transformer.getServerTag());
					}
				}
			} finally {
				lock.unlockWrite(stamp);
			}
		}

	}

	public static class CustomPayloadTransformer {

		protected final String serverTag;
		protected final String clientTag;

		protected BiFunction<ConcurrentMap<Object, Object>, byte[], byte[]> dataTransformerServerbound;
		protected BiFunction<ConcurrentMap<Object, Object>, byte[], byte[]> dataTransformerClientbound;

		public CustomPayloadTransformer(
			String serverName, String clientName,
			BiFunction<ConcurrentMap<Object, Object>, byte[], byte[]> dataTransformerServerbound,
			BiFunction<ConcurrentMap<Object, Object>, byte[], byte[]> dataTransformerClientbound
		) {
			this.serverTag = serverName;
			this.clientTag = clientName;
			this.dataTransformerServerbound = dataTransformerServerbound;
			this.dataTransformerClientbound = dataTransformerClientbound;
		}

		public String getServerTag() {
			return serverTag;
		}

		public String getClientTag() {
			return clientTag;
		}

		public byte[] transformDataServerbound(ConcurrentMap<Object, Object> metadata, byte[] data) {
			return dataTransformerServerbound.apply(metadata, data);
		}

		public byte[] transformDataClientbound(ConcurrentMap<Object, Object> metadata, byte[] data) {
			return dataTransformerClientbound.apply(metadata, data);
		}

	}

}