package protocolsupport.protocol;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.text.MessageFormat;

import org.bukkit.entity.Player;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.AttributeKey;
import protocolsupport.api.Connection;
import protocolsupport.api.Connection.PacketListener.PacketEvent;
import protocolsupport.api.Connection.PacketListener.RawPacketEvent;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.pipeline.ChannelHandlers;
import protocolsupport.protocol.storage.ProtocolStorage;
import protocolsupport.zplatform.network.NetworkManagerWrapper;

public class ConnectionImpl extends Connection {

	protected static final AttributeKey<ConnectionImpl> key = AttributeKey.valueOf("PSConnectionImpl");

	protected final NetworkManagerWrapper networkmanager;
	public ConnectionImpl(NetworkManagerWrapper networkmanager) {
		this.networkmanager = networkmanager;
	}

	public NetworkManagerWrapper getNetworkManagerWrapper() {
		return networkmanager;
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
	public InetSocketAddress getRawAddress() {
		return networkmanager.getRawAddress();
	}

	@Override
	public InetSocketAddress getAddress() {
		return networkmanager.getAddress();
	}

	@Override
	public void changeAddress(InetSocketAddress newRemote) {
		SocketAddress primaryaddr = networkmanager.getRawAddress();
		ProtocolStorage.addAddress(primaryaddr, newRemote);
		networkmanager.setAddress(newRemote);
	}

	@Override
	public Player getPlayer() {
		return networkmanager.getBukkitPlayer();
	}

	@Override
	public void sendPacket(Object packet) {
		runTask(() -> {
			try {
				ChannelHandlerContext ctx = networkmanager.getChannel().pipeline().context(ChannelHandlers.LOGIC);
				ctx.writeAndFlush(packet);
			} catch (Throwable t) {
				System.err.println("Error occured while packet sending");
				t.printStackTrace();
			}
		});
	}

	@Override
	public void receivePacket(Object packet) {
		runTask(() -> {
			try {
				ChannelHandlerContext ctx = networkmanager.getChannel().pipeline().context(ChannelHandlers.LOGIC);
				ctx.fireChannelRead(packet);
			} catch (Throwable t) {
				System.err.println("Error occured while packet receiving");
				t.printStackTrace();
			}
		});
	}

	@Override
	public void sendRawPacket(byte[] data) {
		ByteBuf dataInst = Unpooled.wrappedBuffer(data);
		runTask(() -> {
			try {
				ChannelHandlerContext ctx = networkmanager.getChannel().pipeline().context(ChannelHandlers.RAW_CAPTURE_SEND);
				ctx.writeAndFlush(dataInst);
			} catch (Throwable t) {
				System.err.println("Error occured while raw packet sending");
				t.printStackTrace();
			}
		});
	}

	@Override
	public void receiveRawPacket(byte[] data) {
		ByteBuf dataInst = Unpooled.wrappedBuffer(data);
		runTask(() -> {
			try {
				ChannelHandlerContext ctx = networkmanager.getChannel().pipeline().context(ChannelHandlers.RAW_CAPTURE_RECEIVE);
				ctx.fireChannelRead(dataInst);
			} catch (Throwable t) {
				System.err.println("Error occured while raw packet receiving");
				t.printStackTrace();
			}
		});
	}

	private void runTask(Runnable task) {
		if (networkmanager.getChannel().eventLoop().inEventLoop()) {
			task.run();
		} else {
			networkmanager.getChannel().eventLoop().submit(task);
		}
	}

	public static ConnectionImpl getFromChannel(Channel channel) {
		return channel.attr(key).get();
	}

	public void storeInChannel(Channel channel) {
		channel.attr(key).set(this);
	}

	public void setVersion(ProtocolVersion version) {
		this.version = version;
	}

	private final LPacketEvent packetevent = new LPacketEvent();

	private static class LPacketEvent extends PacketEvent {
		public void init(Object packet) {
			this.packet = packet;
			this.cancelled = false;
		}
	}

	public Object handlePacketSend(Object packet) {
		packetevent.init(packet);
		for (PacketListener listener : packetlisteners) {
			try {
				listener.onPacketSending(packetevent);
			} catch (Throwable t) {
				System.err.println("Error occured while handling packet sending");
				t.printStackTrace();
			}
		}
		return packetevent.isCancelled() ? null : packetevent.getPacket();
	}

	public Object handlePacketReceive(Object packet) {
		packetevent.init(packet);
		for (PacketListener listener : packetlisteners) {
			try {
				listener.onPacketReceiving(packetevent);
			} catch (Throwable t) {
				System.err.println("Error occured while handling packet receiving");
				t.printStackTrace();
			}
		}
		return packetevent.isCancelled() ? null : packetevent.getPacket();
	}

	private final LRawPacketEvent rawpacketevent = new LRawPacketEvent();

	private static class LRawPacketEvent extends RawPacketEvent {
		public void init(ByteBuf data) {
			this.data = data;
			this.cancelled = false;
		}
		public ByteBuf getDirectData() {
			return this.data;
		}
	}

	public ByteBuf handleRawPacketSend(ByteBuf data) {
		rawpacketevent.init(data);
		for (PacketListener listener : packetlisteners) {
			try {
				listener.onRawPacketSending(rawpacketevent);
			} catch (Throwable t) {
				System.err.println("Error occured while handling raw packet sending");
				t.printStackTrace();
			}
		}
		if (rawpacketevent.isCancelled()) {
			rawpacketevent.getDirectData().release();
			return null;
		} else {
			return rawpacketevent.getDirectData();
		}
	}

	public ByteBuf handleRawPacketReceive(ByteBuf data) {
		rawpacketevent.init(data);
		for (PacketListener listener : packetlisteners) {
			try {
				listener.onRawPacketReceiving(rawpacketevent);
			} catch (Throwable t) {
				System.err.println("Error occured while handling raw packet receiving");
				t.printStackTrace();
			}
		}
		if (rawpacketevent.isCancelled()) {
			rawpacketevent.getDirectData().release();
			return null;
		} else {
			return rawpacketevent.getDirectData();
		}
	}

	@Override
	public String toString() {
		return MessageFormat.format(
			"{0}(player: {1}, address: {2}, rawaddress: {3}, version: {4}, metadata: {5})",
			getClass().getName(), getPlayer(), getAddress(), getRawAddress(), getVersion(), metadata
		);
	}

}
