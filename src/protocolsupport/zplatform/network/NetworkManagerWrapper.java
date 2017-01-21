package protocolsupport.zplatform.network;

import java.net.InetSocketAddress;
import java.util.UUID;

import javax.crypto.SecretKey;

import org.bukkit.entity.Player;

import com.mojang.authlib.properties.Property;

import io.netty.channel.Channel;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

public abstract class NetworkManagerWrapper {

	public abstract Object unwrap();

	public abstract InetSocketAddress getAddress();

	public abstract InetSocketAddress getRawAddress();

	public abstract void setAddress(InetSocketAddress address);

	public abstract boolean isConnected();

	public abstract Channel getChannel();

	public abstract void close(String closeMessage);

	public abstract void sendPacket(Object packet);

	public abstract void sendPacket(Object packet, GenericFutureListener<? extends Future<? super Void>> genericListener, @SuppressWarnings("unchecked") GenericFutureListener<? extends Future<? super Void>>... futureListeners);

	public abstract void setProtocol(NetworkState state);

	public abstract Object getPacketListener();

	public abstract void setPacketListener(Object listener);

	public abstract void enableEncryption(SecretKey key);

	public abstract UUID getSpoofedUUID();

	public abstract void setSpoofedUUID(UUID uuid);

	public abstract Property[] getSpoofedProperties();

	public abstract void setSpoofedProperties(Property[] properties);

	public abstract Player getBukkitPlayer();

	public abstract String getUserName();

}
