package protocolsupport.zplatform.impl.spigot.network;

import java.net.InetSocketAddress;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.UUID;
import java.util.stream.Collectors;

import org.bukkit.entity.Player;

import com.mojang.authlib.properties.Property;

import io.netty.channel.Channel;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import net.minecraft.server.v1_14_R1.ChatComponentText;
import net.minecraft.server.v1_14_R1.NetworkManager;
import net.minecraft.server.v1_14_R1.Packet;
import net.minecraft.server.v1_14_R1.PacketListener;
import net.minecraft.server.v1_14_R1.PlayerConnection;
import protocolsupport.api.utils.NetworkState;
import protocolsupport.api.utils.ProfileProperty;
import protocolsupport.zplatform.impl.spigot.SpigotMiscUtils;
import protocolsupport.zplatform.network.NetworkManagerWrapper;

public class SpigotNetworkManagerWrapper extends NetworkManagerWrapper {

	private final NetworkManager internal;
	public SpigotNetworkManagerWrapper(NetworkManager internal) {
		this.internal = internal;
	}

	@Override
	public NetworkManager unwrap() {
		return internal;
	}

	@Override
	public InetSocketAddress getAddress() {
		return (InetSocketAddress) internal.getSocketAddress();
	}

	@Override
	public InetSocketAddress getRawAddress() {
		return (InetSocketAddress) internal.getRawAddress();
	}

	@Override
	public void setAddress(InetSocketAddress address) {
		internal.socketAddress = address;
	}

	@Override
	public boolean isConnected() {
		return internal.isConnected();
	}

	@Override
	public Channel getChannel() {
		return internal.channel;
	}

	@Override
	public void close(String closeMessage) {
		internal.close(new ChatComponentText(closeMessage));
	}

	@Override
	public void sendPacket(Object packet) {
		internal.sendPacket((Packet<?>) packet);
	}

	@Override
	public void sendPacket(Object packet, GenericFutureListener<? extends Future<? super Void>> genericListener) {
		internal.sendPacket((Packet<?>) packet, genericListener);
	}

	@Override
	public void setProtocol(NetworkState state) {
		internal.setProtocol(SpigotMiscUtils.netStateToProtocol(state));
	}

	@Override
	public NetworkState getNetworkState() {
		return SpigotMiscUtils.protocolToNetState(getChannel().attr(NetworkManager.c).get());
	}

	@Override
	public PacketListener getPacketListener() {
		return internal.i();
	}

	@Override
	public void setPacketListener(Object listener) {
		internal.setPacketListener((PacketListener) listener);
	}

	@Override
	public UUID getSpoofedUUID() {
		return internal.spoofedUUID;
	}

	@Override
	public Collection<ProfileProperty> getSpoofedProperties() {
		if (internal.spoofedProfile == null) {
			return Collections.emptyList();
		}
		return
			Arrays.asList(internal.spoofedProfile).stream()
			.map(prop -> new ProfileProperty(prop.getName(), prop.getValue(), prop.getSignature()))
			.collect(Collectors.toList());
	}

	@Override
	public void setSpoofedProfile(UUID uuid, Collection<ProfileProperty> properties) {
		internal.spoofedUUID = uuid;
		if (properties != null) {
			internal.spoofedProfile = properties.stream()
			.map(prop -> new Property(prop.getName(), prop.getValue(), prop.getSignature()))
			.collect(Collectors.toList())
			.toArray(new Property[0]);
		}
	}

	@Override
	public Player getBukkitPlayer() {
		PacketListener listener = getPacketListener();
		if (listener instanceof PlayerConnection) {
			return ((PlayerConnection) listener).player.getBukkitEntity();
		}
		return null;
	}

}
