package protocolsupport.api;

import java.net.InetSocketAddress;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import org.bukkit.entity.Player;

import com.google.common.base.Objects;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ReadOnlyByteBuf;

@SuppressWarnings("deprecation")
public abstract class Connection {

	protected volatile ProtocolVersion version = ProtocolVersion.UNKNOWN;

	/**
	 * Returns native network manager object <br>
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
	 * Returns remote address <br>
	 * This address can be spoofed
	 * @return remote address
	 */
	public abstract InetSocketAddress getAddress();

	/**
	 * Changes remote address <br>
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
	 * Returns {@link ProtocolVersion} <br>
	 * Returns {@link ProtocolVersion#UNKNOWN} if handshake packet is not yet received
	 * @return {@link ProtocolVersion}
	 */
	public ProtocolVersion getVersion() {
		return version;
	}

	/**
	 * Receives packet from client <br>
	 * Packet received by this method skips receive packet listener
	 * @param packet packet
	 */
	public abstract void receivePacket(Object packet);

	/**
	 * Sends packet to player <br>
	 * Packet sent by this method skips send packet listener
	 * @param packet packet
	 */
	public abstract void sendPacket(Object packet);

	/**
	 * Sends packet data to player <br>
	 * Packet sent by this method should use native client protocol (packet data including packet id)
	 * @param data packet data
	 */
	public abstract void sendRawPacket(byte[] data);

	/**
	 * Receives raw packet data from client <br>
	 * Packet received by this method should use native client protocol (packet data including packet id)
	 * @param data packet data
	 */
	public abstract void receiveRawPacket(byte[] data);


	protected final CopyOnWriteArrayList<PacketListener> packetlisteners = new CopyOnWriteArrayList<>();

	/**
	 * Adds packet listener
	 * @param listener packet listener
	 */
	public void addPacketListener(PacketListener listener) {
		packetlisteners.add(listener);
	}

	/**
	 * Removes packet listener
	 * @param listener packet listener
	 */
	public void removePacketListener(PacketListener listener) {
		packetlisteners.remove(listener);
	}

	/**
	 * Adds send packet listener
	 * @param listener send packet listener
	 */
	@Deprecated
	public void addPacketSendListener(PacketSendListener listener) {
		addPacketListener(new DeprecatedPacketListener(listener) {
			@Override
			public void onPacketSending(PacketEvent event) {
				boolean cansend = listener.onPacketSending(event.getPacket());
				if (!cansend) {
					event.setCancelled(true);
				}
			}
		});
	}

	/**
	 * Removes send packet listener
	 * @param listener send packet listener
	 */
	@Deprecated
	public void removePacketSendListener(PacketSendListener listener) {
		removePacketListener(new DeprecatedPacketListener(listener));
	}

	/**
	 * Adds receive packet listener
	 * @param listener receive packet listener
	 */
	@Deprecated
	public void addPacketReceiveListener(PacketReceiveListener listener) {
		addPacketListener(new DeprecatedPacketListener(listener) {
			@Override
			public void onPacketReceiving(PacketEvent event) {
				boolean cansend = listener.onPacketReceiving(event.getPacket());
				if (!cansend) {
					event.setCancelled(true);
				}
			}
		});
	}

	/**
	 * Removes receive packet listener
	 * @param listener receive packet listener
	 */
	@Deprecated
	public void removePacketReceiveListener(PacketReceiveListener listener) {
		removePacketListener(new DeprecatedPacketListener(listener));
	}


	protected final ConcurrentHashMap<String, Object> metadata = new ConcurrentHashMap<>();

	/**
	 * Adds any object to the internal map
	 * @param key map key
	 * @param obj value
	 */
	public void addMetadata(String key, Object obj) {
		metadata.put(key, obj);
	}

	/**
	 * Returns object from internal map by map key <br>
	 * Returns null if there wasn't any object by map key
	 * @param key map key
	 * @return value from internal map
	 */
	public Object getMetadata(String key) {
		return metadata.get(key);
	}

	/**
	 * Removes object from internal map by map key <br>
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

	public abstract static class PacketListener {

		/**
		 * Override to handle native packet sending <br>
		 * PacketEvent and it's data is only valid while handling the packet
		 * @param event packet event
		 */
		public void onPacketSending(PacketEvent event) {
		}

		/**
		 * Override to handle native packet receiving <br>
		 * PacketEvent and it's data is only valid while handling the packet <br>
		 * Based on client version this the received data might be a part of packet, not a full one
		 * @param event packet event
		 */
		public void onPacketReceiving(PacketEvent event) {
		}

		/**
		 * Override to handle raw packet sending <br>
		 * PacketEvent and it's data is only valid while handling the packet
		 * @param event packet event
		 */
		public void onRawPacketSending(RawPacketEvent event) {
		}

		/**
		 * Override to handle raw packet sending
		 * @param event packet event
		 */
		public void onRawPacketReceiving(RawPacketEvent event) {
		}

		public static class PacketEvent {

			protected Object packet;
			protected boolean cancelled;

			/**
			 * Returns packet
			 * @return native packet instance
			 */
			public Object getPacket() {
				return packet;
			}

			/**
			 * Sets packet
			 * @param packet native packet instance
			 */
			public void setPacket(Object packet) {
				this.packet = packet;
			}

			/**
			 * Returns if packet is cancelled
			 * @return true if packet is cancelled, false otherwise
			 */
			public boolean isCancelled() {
				return cancelled;
			}

			/**
			 * Sets if packet is cancelled
			 * @param cancelled true if packet is cancelled, false otherwise
			 */
			public void setCancelled(boolean cancelled) {
				this.cancelled = cancelled;
			}
		}

		public static class RawPacketEvent {

			protected ByteBuf data;
			protected boolean cancelled;

			/**
			 * Returns read only packet data
			 * @return read only packet data
			 */
			public ByteBuf getData() {
				return new ReadOnlyByteBuf(data);
			}

			/**
			 * Sets packet data <br>
			 * A copy of passed ByteBuf is made, and passed ByteBuf is not released
			 * @param data packet data
			 */
			public void setData(ByteBuf data) {
				this.data.release();
				this.data = data.copy();
			}

			/**
			 * Returns if packet is cancelled
			 * @return true if packet is cancelled, false otherwise
			 */
			public boolean isCancelled() {
				return cancelled;
			}

			/**
			 * Sets if packet is cancelled
			 * @param cancelled true if packet is cancelled, false otherwise
			 */
			public void setCancelled(boolean cancelled) {
				this.cancelled = cancelled;
			}

		}

	}

	private static class DeprecatedPacketListener extends PacketListener {
		private final Object deprecatedlistener;
		public DeprecatedPacketListener(Object deprecatedlistener) {
			this.deprecatedlistener = deprecatedlistener;
		}
		@Override
		public int hashCode() {
			return deprecatedlistener.hashCode();
		}
		@Override
		public boolean equals(Object obj) {
			if (obj instanceof DeprecatedPacketListener) {
				return Objects.equal(deprecatedlistener, ((DeprecatedPacketListener) obj).deprecatedlistener);
			}
			return false;
		}
	}

	@Deprecated
	@FunctionalInterface
	public static interface PacketSendListener {
		/**
		 * Override to handle packet sending <br>
		 * Return true to allow packet sending, false to deny
		 * @param packet packet
		 * @return true to allow packet sending, false to deny
		 */
		public boolean onPacketSending(Object packet);
	}

	@Deprecated
	@FunctionalInterface
	public static interface PacketReceiveListener {
		/**
		 * Override to handle packet receivingb <br>
		 * Return true to allow packet receiving, false to deny
		 * @param packet packet
		 * @return true to allow packet receiving, false to deny
		 */
		public boolean onPacketReceiving(Object packet);
	}

}
