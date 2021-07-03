package protocolsupport.zplatform.impl.spigot.network;

import java.net.InetSocketAddress;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

import org.bukkit.entity.Player;

import com.mojang.authlib.properties.Property;

import io.netty.channel.Channel;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import io.netty.util.concurrent.ScheduledFuture;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.PacketListener;
import net.minecraft.network.protocol.Packet;
import net.minecraft.server.network.PlayerConnection;
import protocolsupport.api.chat.components.BaseComponent;
import protocolsupport.api.utils.NetworkState;
import protocolsupport.api.utils.ProfileProperty;
import protocolsupport.zplatform.impl.spigot.SpigotMiscUtils;
import protocolsupport.zplatform.network.NetworkManagerWrapper;

public class SpigotNetworkManagerWrapper extends NetworkManagerWrapper {

	private final NetworkManager internal;

	public SpigotNetworkManagerWrapper(Channel channel, NetworkManager internal) {
		super(channel);
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
		internal.l = address;
	}

	@Override
	public boolean isConnected() {
		return internal.isConnected();
	}

	@Override
	public void close(BaseComponent closeMessage) {
		internal.close(SpigotMiscUtils.toPlatformMessage(closeMessage));
	}

	@Override
	public void sendPacket(Object packet) {
		internal.sendPacket((Packet<?>) packet);
	}

	@Override
	public void sendPacket(Object packet, GenericFutureListener<? extends Future<? super Void>> genericListener) {
		internal.sendPacket((Packet<?>) packet, genericListener);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void sendPacket(Object packet, GenericFutureListener<? extends Future<? super Void>> genericListener, int timeout, TimeUnit timeunit, Runnable timeoutListener) {
		AtomicReference<ScheduledFuture<?>> timeoutFutureRef = new AtomicReference<>(channel.eventLoop().schedule(timeoutListener, timeout, timeunit));
		sendPacket(packet, future -> {
			ScheduledFuture<?> timeoutFuture = timeoutFutureRef.get();
			boolean done = timeoutFuture.cancel(false);
			timeoutFuture.cancel(false);
			if (!done) {
				((GenericFutureListener) genericListener).operationComplete(future);
			}
		});
	}

	@Override
	public void sendPacketBlocking(Object packet, int timeout, TimeUnit timeunit) throws TimeoutException, InterruptedException {
		if (channel.eventLoop().inEventLoop()) {
			throw new IllegalStateException("Can't send packet with timeout in event loop");
		}
		CountDownLatch latch = new CountDownLatch(1);
		sendPacket(packet, future -> latch.countDown());
		if (!latch.await(timeout, timeunit)) {
			throw new TimeoutException();
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void sendPacketBlocking(Object packet, GenericFutureListener<? extends Future<? super Void>> genericListener, int timeout, TimeUnit timeunit) throws TimeoutException, InterruptedException {
		if (channel.eventLoop().inEventLoop()) {
			throw new IllegalStateException("Can't send packet with timeout in event loop");
		}
		AtomicBoolean flag = new AtomicBoolean(false);
		CountDownLatch latch = new CountDownLatch(1);
		sendPacket(packet, future -> {
			try {
				if (flag.compareAndSet(false, true)) {
					((GenericFutureListener) genericListener).operationComplete(future);
				}
			} finally {
				latch.countDown();
			}
		});
		if (!latch.await(timeout, timeunit) && flag.compareAndSet(false, true)) {
			throw new TimeoutException();
		}
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
		return internal.j();
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
			return ((PlayerConnection) listener).b.getBukkitEntity();
		}
		return null;
	}

}
