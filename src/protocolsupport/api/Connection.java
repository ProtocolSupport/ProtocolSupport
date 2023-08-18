package protocolsupport.api;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledExecutorService;
import java.util.function.BiFunction;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.bukkit.entity.Player;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ReadOnlyByteBuf;
import protocolsupport.api.chat.components.BaseComponent;
import protocolsupport.api.utils.NetworkState;
import protocolsupport.api.utils.Profile;
import protocolsupport.api.utils.ProfileProperty;
import protocolsupport.protocol.utils.authlib.LoginProfile;

@SuppressWarnings("deprecation")
public abstract class Connection {

	protected final WrappedProfile profile = new WrappedProfile(new LoginProfile());
	protected volatile ProtocolVersion version = ProtocolVersion.UNKNOWN;

	/**
	 * Returns native network manager object <br>
	 * This can be anything, but for now it's NetworkManager for spigot and GlowSession for GlowStone
	 * @return native network manager object
	 */
	public abstract @Nonnull Object getNetworkManager();

	/**
	 * Returns if this connection is active
	 * @return true if connection is active
	 */
	public abstract boolean isConnected();

	/**
	 * Closes the connection
	 */
	public abstract void close();

	/**
	 * Disconnects client with sending disconnect message <br>
	 * If sending disconnect message is impossible, just closes the connection
	 * @param message disconnect message
	 */
	public abstract void disconnect(@Nonnull BaseComponent message);

	/**
	 * Disconnects client with sending disconnect message <br>
	 * If sending disconnect message is impossible, just closes the connection
	 * @param message disconnect message
	 */
	public void disconnect(@Nonnull String message) {
		disconnect(BaseComponent.fromMessage(message));
	}

	/**
	 * Returns address the player connected to/pinged
	 * @return address the player connected to/pinged
	 */
	public abstract @Nonnull InetSocketAddress getVirtualHost();

	/**
	 * Returns real remote address
	 * @return real remote address
	 */
	public abstract @Nonnull InetSocketAddress getRawAddress();

	/**
	 * Returns remote address <br>
	 * This address can be spoofed
	 * @return remote address
	 */
	public abstract @Nonnull InetSocketAddress getAddress();

	/**
	 * Changes remote address <br>
	 * This address will be available as parameter for ProtocolSupportAPI until connection close
	 * @param newRemote new remote address
	 */
	public abstract void changeAddress(@Nonnull InetSocketAddress newRemote);

	/**
	 * Returns {@link Profile} object
	 * @return {@link Profile} object
	 */
	public @Nonnull Profile getProfile() {
		return profile;
	}

	/**
	 * Returns {@link Player} object if possible
	 * @return {@link Player} object or null
	 */
	public abstract @Nullable Player getPlayer();

	/**
	 * Returns {@link ProtocolVersion} <br>
	 * Returns {@link ProtocolVersion#UNKNOWN} if handshake packet is not yet received
	 * @return {@link ProtocolVersion}
	 */
	public ProtocolVersion getVersion() {
		return version;
	}

	/**
	 * Returns native network i/o scheduled executor <br>
	 * This can be anything, but it's EventLoop on netty based servers
	 * @return {@link ScheduledExecutorService} which executes tasks in network i/o threads
	 */
	public abstract @Nonnull ScheduledExecutorService getIOExecutor();

	/**
	 * Receives packet from client
	 * @param packet packet
	 */
	public abstract void receivePacket(@Nonnull Object packet);

	/**
	 * Sends packet to player
	 * @param packet packet
	 */
	public abstract void sendPacket(@Nonnull Object packet);

	/**
	 * Sends packet data to player <br>
	 * Packet sent by this method should use native client protocol (packet data including packet id)
	 * @param data packet data
	 */
	public abstract void sendRawPacket(@Nonnull byte[] data);

	/**
	 * Receives raw packet data from client <br>
	 * Packet received by this method should use native client protocol (packet data including packet id)
	 * @param data packet data
	 */
	public abstract void receiveRawPacket(@Nonnull byte[] data);

	/**
	 * Gets the state at which server network is now
	 * @return network state
	 */
	public abstract @Nonnull NetworkState getNetworkState();


	protected final SortedMap<Integer, List<PacketListener>> sortedPacketListeners = new TreeMap<>(Comparator.comparing(k -> -k));
	protected volatile List<PacketListener> flatPacketListeners = Collections.emptyList();

	protected List<PacketListener> createFlatPacketListeners() {
		ArrayList<PacketListener> flatList = new ArrayList<>(sortedPacketListeners.size());
		for (List<PacketListener> priorityList : sortedPacketListeners.values()) {
			flatList.addAll(priorityList);
		}
		return flatList;
	}

	/**
	 * Adds packet listener with priority 0
	 * @param listener packet listener
	 */
	public void addPacketListener(@Nonnull PacketListener listener) {
		addPacketListener(listener, 0);
	}

	/**
	 * Adds packet listener with priority
	 * Listeners with higher priority number are invoked first
	 * @param listener packet listener
	 * @param priority priority
	 */
	public void addPacketListener(@Nonnull PacketListener listener, int priority) {
		synchronized (sortedPacketListeners) {
			sortedPacketListeners.computeIfAbsent(priority, k -> new ArrayList<>()).add(listener);
			flatPacketListeners = createFlatPacketListeners();
		}
	}

	/**
	 * Removes packet listener
	 * @param listener packet listener
	 */
	public void removePacketListener(@Nonnull PacketListener listener) {
		synchronized (sortedPacketListeners) {
			Iterator<Map.Entry<Integer, List<PacketListener>>> sortedPacketListenersIterator = sortedPacketListeners.entrySet().iterator();
			while (sortedPacketListenersIterator.hasNext()) {
				Map.Entry<Integer, List<PacketListener>> entry = sortedPacketListenersIterator.next();
				List<PacketListener> priorityList = entry.getValue();
				priorityList.remove(listener);
				if (priorityList.isEmpty()) {
					sortedPacketListenersIterator.remove();
				}
			}
			flatPacketListeners = createFlatPacketListeners();
		}
	}

	protected final ConcurrentHashMap<String, Object> metadata = new ConcurrentHashMap<>();

	/**
	 * Adds any object to the internal map
	 * @param key map key
	 * @param obj value
	 */
	public void addMetadata(@Nonnull String key, @Nonnull Object obj) {
		metadata.put(key, obj);
	}

	/**
	 * Returns object from internal map by map key <br>
	 * Returns null if there wasn't any object by map key
	 * @param key map key
	 * @return value from internal map
	 */
	@SuppressWarnings("unchecked")
	public <T> T getMetadata(@Nonnull String key) {
		return (T) metadata.get(key);
	}

	/**
	 * Removes object from internal map by map key <br>
	 * Returns null if there wasn't any object by map key
	 * @param key map key
	 * @return deleted value from internal map
	 */
	@SuppressWarnings("unchecked")
	public <T> T removeMetadata(@Nonnull String key) {
		return (T) metadata.remove(key);
	}

	/**
	 * Returns if there is a value in internal map by map key
	 * @param key map key
	 * @return true is there was any object by map key
	 */
	public boolean hasMetadata(@Nonnull String key) {
		return metadata.containsKey(key);
	}

	/**
	 * Computes key in internal map
	 * @param <V> value type
	 * @param key map key
	 * @param function value compute function
	 * @return computed value
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public <V> V computeMetadata(@Nonnull String key, @Nonnull BiFunction<String, V, V> function) {
		return (V) metadata.compute(key, (BiFunction) function);
	}

	public abstract static class PacketListener {

		/**
		 * Override to handle native packet sending <br>
		 * PacketEvent and it's data is only valid while handling the packet
		 * @param event packet event
		 */
		public void onPacketSending(@Nonnull PacketEvent event) {
		}

		/**
		 * Override to handle native packet receiving <br>
		 * PacketEvent and it's data is only valid while handling the packet <br>
		 * Based on client version this the received data might be a part of packet, not a full one
		 * @param event packet event
		 */
		public void onPacketReceiving(@Nonnull PacketEvent event) {
		}

		/**
		 * Override to handle raw packet sending <br>
		 * PacketEvent and it's data is only valid while handling the packet
		 * @param event packet event
		 */
		public void onRawPacketSending(@Nonnull RawPacketEvent event) {
		}

		/**
		 * Override to handle raw packet sending
		 * @param event packet event
		 */
		public void onRawPacketReceiving(@Nonnull RawPacketEvent event) {
		}

		public static class PacketEvent {

			protected Object mainpacket;
			protected boolean cancelled;
			protected ArrayList<Object> packets = new ArrayList<>();

			/**
			 * Returns main packet (packet that triggered this event)
			 * @return native packet instance
			 */
			public @Nonnull Object getPacket() {
				return mainpacket;
			}

			/**
			 * Returns all packets that will be processed <br>
			 * This collection can be used to manually position additional packets when interop with other plugins is required
			 * @return all native packets instances that will be processed
			 */
			public @Nonnull List<Object> getPackets() {
				return packets;
			}

			/**
			 * Adds packet before the main packet <br>
			 * This packet will be processed before the main packet
			 * @param packet native packet instance
			 */
			public void addPacketBefore(@Nonnull Object packet) {
				packets.add(packets.indexOf(mainpacket), packet);
			}

			/**
			 * Adds packet after the main packet
			 * This packet will be processed after the main packet
			 * @param packet native packet instance
			 */
			public void addPacketAfter(@Nonnull Object packet) {
				packets.add(packets.indexOf(mainpacket) + 1, packet);
			}

			/**
			 * Sets main packet
			 * @param packet native packet instance
			 */
			public void setPacket(@Nonnull Object packet) {
				this.mainpacket = packet;
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
			public @Nonnull ByteBuf getData() {
				return new ReadOnlyByteBuf(data);
			}

			/**
			 * Sets packet data <br>
			 * A copy of passed ByteBuf is made, and passed ByteBuf is not released
			 * @param data packet data
			 */
			public void setData(@Nonnull ByteBuf data) {
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

	protected static class WrappedProfile extends Profile {

		protected volatile Profile profile;

		public WrappedProfile(Profile profile) {
			this.profile = profile;
		}

		public Profile get() {
			return profile;
		}

		public void set(Profile profile) {
			this.profile = profile;
		}

		@Override
		public boolean isOnlineMode() {
			return profile.isOnlineMode();
		}

		@Override
		public String getOriginalName() {
			return profile.getOriginalName();
		}

		@Override
		public UUID getOriginalUUID() {
			return profile.getOriginalUUID();
		}

		@Override
		public String getName() {
			return profile.getName();
		}

		@Override
		public UUID getUUID() {
			return profile.getUUID();
		}

		@Override
		public Set<String> getPropertiesNames() {
			return profile.getPropertiesNames();
		}

		@Override
		public Set<ProfileProperty> getProperties(String name) {
			return profile.getProperties(name);
		}

	}

}
