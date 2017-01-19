package protocolsupport.protocol;

import java.net.InetSocketAddress;

import org.bukkit.entity.Player;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandler;
import io.netty.util.AttributeKey;
import net.minecraft.server.v1_11_R1.CancelledPacketHandleException;
import net.minecraft.server.v1_11_R1.Packet;
import net.minecraft.server.v1_11_R1.PacketListener;
import protocolsupport.api.Connection;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.pipeline.ChannelHandlers;
import protocolsupport.zplatform.network.NetworkManagerWrapper;

public class ConnectionImpl extends Connection {

	private static final AttributeKey<ConnectionImpl> key = AttributeKey.valueOf("PSConnectionImpl");

	public static ConnectionImpl getFromChannel(Channel channel) {
		return channel.attr(key).get();
	}

	private final NetworkManagerWrapper networkmanager;
	public ConnectionImpl(NetworkManagerWrapper networkmanager, ProtocolVersion version) {
		super(version);
		this.networkmanager = networkmanager;
	}

	public void storeInChannel(Channel channel) {
		channel.attr(key).set(this);
	}

	@Override
	public Object getNetworkManager() {
		return networkmanager.unwrap();
	}

	@Override
	public boolean isConnected() {
		return networkmanager.isConnected();
	}

	@Override
	public InetSocketAddress getAddress() {
		return networkmanager.getAddress();
	}

	@Override
	public Player getPlayer() {
		return networkmanager.getBukkitPlayer();
	}

	@Override
	public void receivePacket(Object packet) {
		@SuppressWarnings("unchecked")
		final Packet<PacketListener> packetInst = (Packet<PacketListener>) packet;
		if (networkmanager.getChannel().isOpen()) {
			try {
				packetInst.a((PacketListener) networkmanager.getPacketListener());
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
				ChannelHandlerContext encoderContext = networkmanager.getChannel().pipeline().context(ChannelHandlers.ENCODER);
				ChannelOutboundHandler encoderChannelHandler = (ChannelOutboundHandler) encoderContext.handler();
				encoderChannelHandler.write(encoderContext, packetInst, encoderContext.voidPromise());
			} catch (Exception e) {
				e.printStackTrace();
			}
		};
		if (networkmanager.getChannel().eventLoop().inEventLoop()) {
			packetSend.run();
		} else {
			networkmanager.getChannel().eventLoop().submit(packetSend);
		}
	}

	public void setVersion(ProtocolVersion version) {
		this.version = version;
	}

	public boolean handlePacketSend(Object packet) {
		boolean canSend = true;
		for (PacketSendListener listener : sendListeners) {
			try {
				if (!listener.onPacketSending(packet)) {
					canSend = false;
				}
			} catch (Throwable t) {
				System.err.println("Error occured while handling packet sending");
				t.printStackTrace();
			}
		}
		return canSend;
	}

	public boolean handlePacketReceive(Object packet) {
		boolean canReceive = true;
		for (PacketReceiveListener listener : receiveListeners) {
			try {
				if (!listener.onPacketReceiving(packet)) {
					canReceive = false;
				}
			} catch (Throwable t) {
				System.err.println("Error occured while handling packet receiving");
				t.printStackTrace();
			}
		}
		return canReceive;
	}

}
