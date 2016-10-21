package protocolsupport.protocol;

import java.net.InetSocketAddress;

import org.bukkit.entity.Player;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandler;
import net.minecraft.server.v1_10_R1.CancelledPacketHandleException;
import net.minecraft.server.v1_10_R1.NetworkManager;
import net.minecraft.server.v1_10_R1.Packet;
import net.minecraft.server.v1_10_R1.PacketListener;
import protocolsupport.api.Connection;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.pipeline.ChannelHandlers;
import protocolsupport.utils.netty.ChannelUtils;

public class ConnectionImpl extends Connection {

	private final NetworkManager networkmanager;
	public ConnectionImpl(NetworkManager networkmanager, ProtocolVersion version) {
		super(version);
		this.networkmanager = networkmanager;
	}

	@Override
	public Object getNetworkManager() {
		return networkmanager;
	}

	@Override
	public boolean isConnected() {
		return networkmanager.isConnected();
	}

	@Override
	public InetSocketAddress getAddress() {
		return (InetSocketAddress) networkmanager.getSocketAddress();
	}

	@Override
	public Player getPlayer() {
		return ChannelUtils.getBukkitPlayer(networkmanager);
	}

	@Override
	public void receivePacket(Object packet) {
		@SuppressWarnings("unchecked")
		final Packet<PacketListener> packetInst = (Packet<PacketListener>) packet;
		if (networkmanager.channel.isOpen()) {
			try {
				packetInst.a(networkmanager.i());
			} catch (CancelledPacketHandleException ex) {
			}
		}
	}

	@Override
	public void sendPacket(Object packet) {
		@SuppressWarnings("unchecked")
		final Packet<PacketListener> packetInst = (Packet<PacketListener>) packet;
		Runnable packetSend = () -> {
			try {
				ChannelHandlerContext encoderContext = networkmanager.channel.pipeline().context(ChannelHandlers.ENCODER);
				ChannelOutboundHandler encoderChannelHandler = (ChannelOutboundHandler) encoderContext.handler();
				encoderChannelHandler.write(encoderContext, packetInst, encoderContext.voidPromise());
			} catch (Exception e) {
				e.printStackTrace();
			}
		};
		if (networkmanager.channel.eventLoop().inEventLoop()) {
			packetSend.run();
		} else {
			networkmanager.channel.eventLoop().submit(packetSend);
		}
	}

	public void setVersion(ProtocolVersion version) {
		this.version = version;
	}

	public boolean handlePacketSend(Object packet) {
		boolean canSend = true;
		for (PacketSendListener listener : sendListeners) {
			if (!listener.onPacketSending(packet)) {
				canSend = false;
			}
		}
		return canSend;
	}

	public boolean handlePacketReceive(Object packet) {
		boolean canReceive = true;
		for (PacketReceiveListener listener : receiveListeners) {
			if (!listener.onPacketReceiving(packet)) {
				canReceive = false;
			}
		}
		return canReceive;
	}

}
