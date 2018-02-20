package protocolsupport.zplatform.impl.glowstone.network;

import java.net.InetSocketAddress;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.bukkit.entity.Player;

import com.flowpowered.network.Message;
import com.flowpowered.network.protocol.AbstractProtocol;

import io.netty.channel.Channel;
import io.netty.util.AttributeKey;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import net.glowstone.entity.meta.profile.GlowPlayerProfile;
import net.glowstone.net.GlowSession;
import net.glowstone.net.ProxyData;
import net.glowstone.net.pipeline.MessageHandler;
import net.glowstone.net.protocol.ProtocolType;
import protocolsupport.api.events.PlayerPropertiesResolveEvent.ProfileProperty;
import protocolsupport.api.utils.NetworkState;
import protocolsupport.zplatform.impl.glowstone.GlowStoneMiscUtils;
import protocolsupport.zplatform.network.NetworkManagerWrapper;

public class GlowStoneNetworkManagerWrapper extends NetworkManagerWrapper {

	private static final AttributeKey<Object> packet_listener_key = AttributeKey.valueOf("ps_packet_listener");
	private static final UUID fakeUUID = UUID.randomUUID();

	public static Object getPacketListener(GlowSession session) {
		return session.getChannel().attr(packet_listener_key).get();
	}

	public static GlowStoneNetworkManagerWrapper getFromChannel(Channel channel) {
		return new GlowStoneNetworkManagerWrapper((MessageHandler) channel.pipeline().get(GlowStoneChannelHandlers.NETWORK_MANAGER));
	}

	private final MessageHandler handler;
	public GlowStoneNetworkManagerWrapper(MessageHandler handler) {
		this.handler = handler;
	}

	public GlowSession getSession() {
		return handler.getSession();
	}

	@Override
	public Object unwrap() {
		return handler;
	}

	public NetworkState getProtocol() {
		AbstractProtocol proto = getSession().getProtocol();
		for (ProtocolType type : ProtocolType.values()) {
			if (type.getProtocol() == proto) {
				return GlowStoneMiscUtils.protocolToNetState(type);
			}
		}
		throw new IllegalStateException(MessageFormat.format("Unkown protocol {0}", proto));
	}

	@Override
	public InetSocketAddress getAddress() {
		return getSession().getAddress();
	}

	@Override
	public void setAddress(InetSocketAddress address) {
		ProxyData old = getSession().getProxyData();
		if (old != null) {
			getSession().setProxyData(new ProxyData(null, null, address, null, old.getProfile().getUniqueId(), new ArrayList<>(old.getProfile().getProperties())));
		} else {
			getSession().setProxyData(new ProxyData(null, null, address, null, fakeUUID, Collections.emptyList()));
		}
	}

	@Override
	public boolean isConnected() {
		return getChannel().isOpen();
	}

	@Override
	public Channel getChannel() {
		return getSession().getChannel();
	}

	@Override
	public void close(String closeMessage) {
		getSession().disconnect(closeMessage);
	}

	@Override
	public void sendPacket(Object packet) {
		getSession().send((Message) packet);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void sendPacket(Object packet, GenericFutureListener<? extends Future<? super Void>> genericListener, GenericFutureListener<? extends Future<? super Void>>... futureListeners) {
		getSession().sendWithFuture((Message) packet).addListener(genericListener).addListeners(futureListeners);
	}

	@Override
	public void setProtocol(NetworkState state) {
		getSession().setProtocol(GlowStoneMiscUtils.netStateToProtocol(state));
	}

	private static final ProtocolType[] protocols = ProtocolType.values();

	@Override
	public NetworkState getNetworkState() {
		AbstractProtocol proto = getSession().getProtocol();
		for (ProtocolType type : protocols) {
			if (type.getProtocol() == proto) {
				return GlowStoneMiscUtils.protocolToNetState(type);
			}
		}
		throw new IllegalStateException(MessageFormat.format("Unknown protocol {0}", proto));
	}

	@Override
	public Object getPacketListener() {
		return getPacketListener(getSession());
	}

	@Override
	public void setPacketListener(Object listener) {
		getChannel().attr(packet_listener_key).set(listener);
	}

	@Override
	public UUID getSpoofedUUID() {
		GlowPlayerProfile profile = getSpoofedProfile();
		return profile != null ? profile.getUniqueId() : null;
	}

	@Override
	public ProfileProperty[] getSpoofedProperties() {
		GlowPlayerProfile profile = getSpoofedProfile();
		return profile == null ? null :
		profile.getProperties().stream()
		.map(property -> new ProfileProperty(property.getName(), property.getValue(), property.getSignature()))
		.collect(Collectors.toList())
		.toArray(new ProfileProperty[0]);
	}

	private GlowPlayerProfile getSpoofedProfile() {
		ProxyData proxydata = getSession().getProxyData();
		return proxydata != null ? proxydata.getProfile("?[]___PSFakeProfile!!!!!!!") : null;
	}

	@Override
	public void setSpoofedProfile(UUID uuid, ProfileProperty[] properties) {
		ProxyData old = getSession().getProxyData();
		List<com.destroystokyo.paper.profile.ProfileProperty> glowproperties = Collections.emptyList();
		if (properties != null) {
			glowproperties = Arrays.stream(properties)
			.map(prop -> new com.destroystokyo.paper.profile.ProfileProperty(prop.getName(), prop.getValue(), prop.getSignature()))
			.collect(Collectors.toList());
		}
		if (old != null) {
			getSession().setProxyData(new ProxyData(null, null, old.getAddress(), null, uuid, glowproperties));
		} else {
			getSession().setProxyData(new ProxyData(null, null, getAddress(), null, uuid, glowproperties));
		}
	}

	@Override
	public Player getBukkitPlayer() {
		return getSession().getPlayer();
	}

}
