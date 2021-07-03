package protocolsupport.zplatform.network;

import java.net.InetSocketAddress;
import java.util.Collection;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.bukkit.entity.Player;

import io.netty.channel.Channel;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import protocolsupport.api.chat.components.BaseComponent;
import protocolsupport.api.utils.NetworkState;
import protocolsupport.api.utils.ProfileProperty;

public abstract class NetworkManagerWrapper {

	protected final Channel channel;

	protected NetworkManagerWrapper(Channel channel) {
		this.channel = channel;
	}

	public Channel getChannel() {
		return channel;
	}

	public abstract Object unwrap();

	protected InetSocketAddress virtualHost;

	public InetSocketAddress getVirtualHost() {
		return virtualHost;
	}

	public void setVirtualHost(InetSocketAddress virtualHost) {
		this.virtualHost = virtualHost;
	}

	public abstract InetSocketAddress getAddress();

	public InetSocketAddress getRawAddress() {
		return (InetSocketAddress) getChannel().remoteAddress();
	}

	public abstract void setAddress(InetSocketAddress address);

	public abstract boolean isConnected();

	public abstract void close(BaseComponent closeMessage);

	public abstract void sendPacket(Object packet);

	public abstract void sendPacket(Object packet, GenericFutureListener<? extends Future<? super Void>> genericListener);

	public abstract void sendPacket(Object packet, GenericFutureListener<? extends Future<? super Void>> genericListener, int timeout, TimeUnit timeunit, Runnable timeoutListener);

	public abstract void sendPacketBlocking(Object packet, int timeout, TimeUnit timeunit) throws TimeoutException, InterruptedException;

	public abstract void sendPacketBlocking(Object packet, GenericFutureListener<? extends Future<? super Void>> genericListener, int timeout, TimeUnit timeunit) throws TimeoutException, InterruptedException;


	public abstract void setProtocol(NetworkState state);

	public abstract NetworkState getNetworkState();

	public abstract Object getPacketListener();

	public abstract void setPacketListener(Object listener);

	public abstract UUID getSpoofedUUID();

	public abstract Collection<ProfileProperty> getSpoofedProperties();

	public abstract void setSpoofedProfile(UUID uuid, Collection<ProfileProperty> properties);

	public abstract Player getBukkitPlayer();

}
