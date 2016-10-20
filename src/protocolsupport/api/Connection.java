package protocolsupport.api;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import org.bukkit.entity.Player;

public abstract class Connection {

	protected ProtocolVersion version;
	public Connection(ProtocolVersion version) {
		this.version = version;
	}

	public abstract Object getNetworkManager();

	public abstract boolean isConnected();

	public abstract InetSocketAddress getAddress();

	public abstract Player getPlayer();

	public ProtocolVersion getVersion() {
		return version;
	}

	public abstract void receivePacket(Object packet) throws ExecutionException;

	public abstract void sendPacket(Object packet);

	protected final ArrayList<PacketSendListener> sendListeners = new ArrayList<>();
	protected final ArrayList<PacketReceiveListener> receiveListeners = new ArrayList<>();

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

	@FunctionalInterface
	public static interface PacketSendListener {
		public boolean onPacketSending(Object packet);
	}

	@FunctionalInterface
	public static interface PacketReceiveListener {
		public boolean onPacketReceiving(Object packet);
	}

}
