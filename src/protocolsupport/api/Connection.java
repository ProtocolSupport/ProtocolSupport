package protocolsupport.api;

import java.net.InetSocketAddress;
import java.util.concurrent.ExecutionException;

import org.bukkit.entity.Player;

import net.minecraft.server.v1_10_R1.CancelledPacketHandleException;
import net.minecraft.server.v1_10_R1.NetworkManager;
import net.minecraft.server.v1_10_R1.Packet;
import net.minecraft.server.v1_10_R1.PacketListener;
import protocolsupport.utils.netty.ChannelUtils;

public class Connection {

	private final NetworkManager networkmanager;
	private final ProtocolVersion version;
	public Connection(Object networkmanager, ProtocolVersion version) {
		this.networkmanager = (NetworkManager) networkmanager;
		this.version = version;
	}

	public Object getNetworkManager() {
		return networkmanager;
	}

	public boolean isConnected() {
		return networkmanager.isConnected();
	}

	public InetSocketAddress getAddress() {
		return (InetSocketAddress) networkmanager.getSocketAddress();
	}

	public Player getPlayer() {
		return ChannelUtils.getBukkitPlayer(networkmanager.channel);
	}

	public ProtocolVersion getVersion() {
		return version;
	}

	public void receivePacket(Object packet) throws ExecutionException {
		@SuppressWarnings("unchecked")
		final Packet<PacketListener> packetInst = (Packet<PacketListener>) packet;
		Runnable packetReceive = new Runnable() {
			@Override
			public void run() {
				if (networkmanager.channel.isOpen()) {
					try {
						packetInst.a(networkmanager.i());
					} catch (CancelledPacketHandleException ex) {
					}
				}
			}
		};
		if (networkmanager.channel.eventLoop().inEventLoop()) {
			try {
				packetReceive.run();
			} catch (Throwable t) {
				throw new ExecutionException(t);
			}
		} else {
			try {
				networkmanager.channel.eventLoop().submit(packetReceive).get();
			} catch (InterruptedException e) {
			}
		}
	}

	public void sendPacket(Object packet) {
		networkmanager.sendPacket((Packet<?>) packet);
	}

}
