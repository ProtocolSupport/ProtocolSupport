package protocolsupport.zplatform.network;

import java.net.InetSocketAddress;
import java.util.UUID;

import org.bukkit.entity.Player;

import io.netty.channel.Channel;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import protocolsupport.api.events.PlayerPropertiesResolveEvent.ProfileProperty;
import protocolsupport.protocol.packet.handler.IHasProfile;
import protocolsupport.protocol.utils.authlib.GameProfile;

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

	public abstract void sendPacket(Object packet, GenericFutureListener<? extends Future<? super Void>> genericListener, @SuppressWarnings("unchecked") GenericFutureListener<? extends Future<? super Void>>... futureListeners);

	public abstract void setProtocol(NetworkState state);

	public abstract Object getPacketListener();

	public abstract void setPacketListener(Object listener);

	public abstract UUID getSpoofedUUID();

	public abstract ProfileProperty[] getSpoofedProperties();

	public abstract void setSpoofedProfile(UUID uuid, ProfileProperty[] properties);

	public abstract Player getBukkitPlayer();

	public String getUserName() {
		Player player = getBukkitPlayer();
		if (player != null) {
			return player.getName();
		} else {
			Object listener = getPacketListener();
			if (listener instanceof IHasProfile) {
				GameProfile profile = ((IHasProfile) listener).getProfile();
				return profile != null ? profile.getName() : null;
			} else {
				return null;
			}
		}
	}

}
