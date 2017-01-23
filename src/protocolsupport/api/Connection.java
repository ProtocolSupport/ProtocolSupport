package protocolsupport.api;

import java.net.InetSocketAddress;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import org.bukkit.entity.Player;

public abstract class Connection {

	protected volatile ProtocolVersion version = ProtocolVersion.UNKNOWN;

	public abstract Object getNetworkManager();

	public abstract boolean isConnected();

	public abstract InetSocketAddress getAddress();

	public abstract Player getPlayer();

	public ProtocolVersion getVersion() {
		return version;
	}

	public abstract void receivePacket(Object packet);

	public abstract void sendPacket(Object packet);

	protected final CopyOnWriteArrayList<PacketSendListener> sendListeners = new CopyOnWriteArrayList<>();
	protected final CopyOnWriteArrayList<PacketReceiveListener> receiveListeners = new CopyOnWriteArrayList<>();

	public void addPacketSendListener(PacketSendListener listener) {
		sendListeners.add(listener);
	}

	public void removePacketSendListener(PacketSendListener listener) {
		sendListeners.remove(listener);
	}

	public void addPacketReceiveListener(PacketReceiveListener listener) {
		receiveListeners.add(listener);
	}

	public void removePacketReceiveListener(PacketReceiveListener listener) {
		receiveListeners.remove(listener);
	}

	private final ConcurrentHashMap<String, Object> metadata = new ConcurrentHashMap<>();

	public void addMetadata(String key, Object obj) {
		metadata.put(key, obj);
	}

	public Object getMetadata(String key) {
		return metadata.get(key);
	}

	public Object removeMetadata(String key) {
		return metadata.remove(key);
	}

	public boolean hasMetadata(String key) {
		return metadata.containsKey(key);
	}

	@FunctionalInterface
	public static interface PacketSendListener {
		public boolean onPacketSending(Object packet);
	}

	@FunctionalInterface
	public static interface PacketReceiveListener {
		public boolean onPacketReceiving(Object packet);
	}

}
