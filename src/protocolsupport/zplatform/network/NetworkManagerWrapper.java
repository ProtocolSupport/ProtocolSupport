package protocolsupport.zplatform.network;

import java.net.InetSocketAddress;
import java.util.Collection;
import java.util.UUID;

import org.bukkit.entity.Player;

import io.netty.channel.Channel;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import protocolsupport.api.utils.NetworkState;
import protocolsupport.api.utils.ProfileProperty;

public abstract class NetworkManagerWrapper {

	public abstract Object unwrap();

	public abstract InetSocketAddress getAddress();

	public InetSocketAddress getRawAddress() {
		return (InetSocketAddress) getChannel().remoteAddress();
	}

	public abstract void setAddress(InetSocketAddress address);

	public abstract boolean isConnected();

	public abstract Channel getChannel();

	public abstract void close(String closeMessage);

	public abstract void sendPacket(Object packet);

	public abstract void sendPacket(Object packet, GenericFutureListener<? extends Future<? super Void>> genericListener);

	public abstract void setProtocol(NetworkState state);

	public abstract NetworkState getNetworkState();

	public abstract Object getPacketListener();

	public abstract void setPacketListener(Object listener);

	public abstract UUID getSpoofedUUID();

	public abstract Collection<ProfileProperty> getSpoofedProperties();

	public abstract void setSpoofedProfile(UUID uuid, Collection<ProfileProperty> properties);

	public abstract Player getBukkitPlayer();

}
