package protocolsupport.zplatform.network;

import java.net.InetSocketAddress;
import java.util.UUID;

import javax.crypto.SecretKey;

import org.bukkit.entity.Player;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;

import io.netty.channel.Channel;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import net.minecraft.server.v1_11_R1.ChatComponentText;
import net.minecraft.server.v1_11_R1.EnumProtocol;
import net.minecraft.server.v1_11_R1.NetworkManager;
import net.minecraft.server.v1_11_R1.Packet;
import net.minecraft.server.v1_11_R1.PacketListener;
import net.minecraft.server.v1_11_R1.PlayerConnection;
import protocolsupport.protocol.packet.handler.IHasProfile;
import protocolsupport.protocol.pipeline.ChannelHandlers;

public class NetworkManagerWrapper {

	public static NetworkManagerWrapper getFromChannel(Channel channel) {
		return new NetworkManagerWrapper((NetworkManager) channel.pipeline().get(ChannelHandlers.NETWORK_MANAGER));
	}

	private final NetworkManager internal;
	public NetworkManagerWrapper(NetworkManager internal) {
		this.internal = internal;
	}

	public NetworkManager unwrap() {
		return internal;
	}

	public InetSocketAddress getAddress() {
		return (InetSocketAddress) internal.getSocketAddress();
	}

	public InetSocketAddress getRawAddress() {
		return (InetSocketAddress) internal.getRawAddress();
	}

	public void setAddress(InetSocketAddress address) {
		internal.l = address;
	}

	public boolean isConnected() {
		return internal.isConnected();
	}

	public Channel getChannel() {
		return internal.channel;
	}

	public void close(String closeMessage) {
		internal.close(new ChatComponentText(closeMessage));
	}

	public void sendPacket(Object packet) {
		internal.sendPacket((Packet<?>) packet);
	}

	public void sendPacket(Object packet, GenericFutureListener<? extends Future<? super Void>> genericListener, @SuppressWarnings("unchecked") GenericFutureListener<? extends Future<? super Void>>... futureListeners) {
		internal.sendPacket((Packet<?>) packet, genericListener, futureListeners);
	}

	public void setProtocol(NetworkListenerState state) {
		internal.setProtocol(EnumProtocol.values()[state.ordinal()]);
	}

	public PacketListener getPacketListener() {
		return internal.i();
	}

	public void setPacketListener(PacketListener listener) {
		internal.setPacketListener(listener);
	}

	public void enableEncryption(SecretKey key) {
		internal.a(key);
	}

	public UUID getSpoofedUUID() {
		return internal.spoofedUUID;
	}

	public void setSpoofedUUID(UUID uuid) {
		internal.spoofedUUID = uuid;
	}

	public Property[] getSpoofedProperties() {
		return internal.spoofedProfile;
	}

	public void setSpoofedProperties(Property[] properties) {
		internal.spoofedProfile = properties;
	}

	public Player getBukkitPlayer() {
		PacketListener listener =getPacketListener();
		if (listener instanceof PlayerConnection) {
			return ((PlayerConnection) listener).player.getBukkitEntity();
		}
		return null;
	}

	public String getUserName() {
		String username = null;
		PacketListener listener = getPacketListener();
		if (listener instanceof IHasProfile) {
			GameProfile profile = ((IHasProfile) listener).getProfile();
			if (profile != null) {
				username = profile.getName();
			}
		} else if (listener instanceof PlayerConnection) {
			username = ((PlayerConnection) listener).player.getProfile().getName();
		}
		return username;
	}

}
