package protocolsupport.zplatform.network;

import java.net.InetSocketAddress;
import java.util.Collection;
import java.util.UUID;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import io.netty.channel.Channel;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import protocolsupport.ProtocolSupport;
import protocolsupport.api.utils.NetworkState;
import protocolsupport.api.utils.ProfileProperty;
import protocolsupport.protocol.packet.handler.IServerThreadTickListener;

public abstract class NetworkManagerWrapper {

	protected final Channel channel;

	protected BukkitTask syncTickTask;

	public NetworkManagerWrapper(Channel channel) {
		this.channel = channel;
		channel.eventLoop().submit(() -> syncTickTask = new BukkitRunnable() {
			@Override
			public void run() {
				if (!isConnected()) {
					cancel();
					return;
				}
				Object handler = getPacketListener();
				if (handler instanceof IServerThreadTickListener) {
					((IServerThreadTickListener) handler).tick();
				}
			}
		}.runTaskTimer(ProtocolSupport.getInstance(), 1, 1));
	}

	public Channel getChannel() {
		return channel;
	}

	public void cancelSyncTickTask() {
		if (syncTickTask != null) {
			syncTickTask.cancel();
		}
	}

	public abstract Object unwrap();

	public abstract InetSocketAddress getAddress();

	public InetSocketAddress getRawAddress() {
		return (InetSocketAddress) getChannel().remoteAddress();
	}

	public abstract void setAddress(InetSocketAddress address);

	public abstract boolean isConnected();

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
