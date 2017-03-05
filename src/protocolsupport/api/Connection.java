package protocolsupport.api;

import java.net.InetSocketAddress;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import org.bukkit.entity.Player;

public abstract class Connection {

	protected volatile ProtocolVersion version = ProtocolVersion.UNKNOWN;

	/**
	 * Returns native network manager object
	 * This can be anything, but for now it's NetworkManager for spigot and GlowSession for GlowStone
	 * @return native network manager object
	 */
	public abstract Object getNetworkManager();

	/**
	 * Returns if this connection is active
	 * @return true if connection is active
	 */
	public abstract boolean isConnected();

	/**
	 * Returns real remote address
	 * @return real remote address
	 */
	public abstract InetSocketAddress getRawAddress();

	/**
	 * Returns remote address
	 * This address can be spoofed
	 * @return remote address
	 */
	public abstract InetSocketAddress getAddress();

	/**
	 * Changes remote address
	 * This address will be available as parameter for ProtocolSupportAPI until connection close
	 * @param newRemote new remote address
	 */
	public abstract void changeAddress(InetSocketAddress newRemote);

	/**
	 * Returns {@link Player} object if possible
	 * @return {@link Player} object or null
	 */
	public abstract Player getPlayer();

	/**
	 * Returns {@link ProtocolVersion}
	 * Returns UNKNOWN if handshake packet is not yet received
	 * @return {@link ProtocolVersion}
	 */
	public ProtocolVersion getVersion() {
		return version;
	}

	/**
	 * Forces serverbound packet handing
	 * Packet received by this method skips receive packet listener
	 * @param packet serverbound packet
	 */
	public abstract void receivePacket(Object packet);

	/**
	 * Sends clientbound packet to player
	 * Packet sent by this method skips send packet listener
	 * @param packet clientbound packet
	 */
	public abstract void sendPacket(Object packet);

	protected final CopyOnWriteArrayList<PacketSendListener> sendListeners = new CopyOnWriteArrayList<>();
	protected final CopyOnWriteArrayList<PacketReceiveListener> receiveListeners = new CopyOnWriteArrayList<>();

	/**
	 * Adds send packet listener
	 * @param listener send packet listener
	 */
	public void addPacketSendListener(PacketSendListener listener) {
		sendListeners.add(listener);
	}

	/**
	 * Removes send packet listener
	 * @param listener send packet listener
	 */
	public void removePacketSendListener(PacketSendListener listener) {
		sendListeners.remove(listener);
	}

	/**
	 * Adds receive packet listener
	 * @param listener receive packet listener
	 */
	public void addPacketReceiveListener(PacketReceiveListener listener) {
		receiveListeners.add(listener);
	}

	/**
	 * Removes receive packet listener
	 * @param listener receive packet listener
	 */
	public void removePacketReceiveListener(PacketReceiveListener listener) {
		receiveListeners.remove(listener);
	}

	private final ConcurrentHashMap<String, Object> metadata = new ConcurrentHashMap<>();

	/**
	 * Adds any object to the internal map
	 * @param key map key
	 * @param obj value
	 */
	public void addMetadata(String key, Object obj) {
		metadata.put(key, obj);
	}

	/**
	 * Returns object from internal map by map key
	 * Returns null if there wasn't any object by map key
	 * @param key map key
	 * @return value from internal map
	 */
	public Object getMetadata(String key) {
		return metadata.get(key);
	}

	/**
	 * Removes object from internal map by map key
	 * Returns null if there wasn't any object by map key
	 * @param key map key
	 * @return deleted value from internal map
	 */
	public Object removeMetadata(String key) {
		return metadata.remove(key);
	}

	/**
	 * Returns if there is a value in internal map by map key
	 * @param key map key
	 * @return true is there was any object by map key
	 */
	public boolean hasMetadata(String key) {
		return metadata.containsKey(key);
	}

	@FunctionalInterface
	public static interface PacketSendListener {
		/**
		 * Override to handle packet sending
		 * Return true to allow packet sending, false to deny
		 * @param packet packet
		 * @return true to allow packet sending, false to deny
		 */
		public boolean onPacketSending(Object packet);
	}

	@FunctionalInterface
	public static interface PacketReceiveListener {
		/**
		 * Override to handle packet receiving
		 * Return true to allow packet receiving, false to deny
		 * @param packet packet
		 * @return true to allow packet receiving, false to deny
		 */
		public boolean onPacketReceiving(Object packet);
	}

}
