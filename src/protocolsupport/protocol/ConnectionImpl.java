package protocolsupport.protocol;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.text.MessageFormat;

import org.bukkit.entity.Player;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandler;
import io.netty.util.AttributeKey;
import protocolsupport.api.Connection;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.pipeline.ChannelHandlers;
import protocolsupport.protocol.storage.ProtocolStorage;
import protocolsupport.zplatform.network.NetworkManagerWrapper;

public abstract class ConnectionImpl extends Connection {

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
	public void sendRawPacket(byte[] data) {
		ByteBuf dataInst = Unpooled.wrappedBuffer(data);
		Runnable packetSend = () -> {
			try {
				ChannelHandlerContext encoderContext = networkmanager.getChannel().pipeline().context(ChannelHandlers.ENCODER_TRANSFORMER);
				ChannelOutboundHandler encoderChannelHandler = (ChannelOutboundHandler) encoderContext.handler();
				encoderChannelHandler.write(encoderContext, dataInst, encoderContext.voidPromise());
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

	public static ConnectionImpl getFromChannel(Channel channel) {
		return channel.attr(key).get();
	}

	public void storeInChannel(Channel channel) {
		channel.attr(key).set(this);
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

	@Override
	public String toString() {
		return MessageFormat.format(
			"{0}(player: {1}, address: {2}, rawaddress: {3}, version: {4}, metadata: {5}, rlisteners: {6}, slisteners: {7})",
			getClass().getName(), getPlayer(), getAddress(), getRawAddress(), getVersion(), metadata, receiveListeners, sendListeners
		);
	}

}
