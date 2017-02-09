package protocolsupport.zplatform.impl.glowstone.network;

import java.net.InetSocketAddress;
import java.text.MessageFormat;
import java.util.UUID;
import java.util.stream.Collectors;

import org.bukkit.entity.Player;

import com.flowpowered.network.Message;
import com.flowpowered.network.protocol.AbstractProtocol;

import io.netty.channel.Channel;
import io.netty.util.AttributeKey;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import net.glowstone.net.GlowSession;
import net.glowstone.net.pipeline.MessageHandler;
import net.glowstone.net.protocol.ProtocolType;
import protocolsupport.api.events.PlayerPropertiesResolveEvent.ProfileProperty;
import protocolsupport.zplatform.impl.glowstone.GlowStoneMiscUtils;
import protocolsupport.zplatform.network.NetworkManagerWrapper;
import protocolsupport.zplatform.network.NetworkState;

public class GlowStoneNetworkManagerWrapper extends NetworkManagerWrapper {

	private static final AttributeKey<Object> packet_listener_key = AttributeKey.valueOf("ps_packet_listener");

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
		return handler.getSession().get();
	}

	@Override
	public Object unwrap() {
		return handler;
	}

	@Override
	public InetSocketAddress getAddress() {
		return getSession().getAddress();
	}

	@Override
	public void setAddress(InetSocketAddress address) {
		// TODO Auto-generated method stub

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
	public void setProtocol(NetworkState state) {
		getSession().setProtocol(GlowStoneMiscUtils.netStateToProtocol(state));
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
		return getSession().getProxyData().getProfile().getUniqueId();
	}

	@Override
	public void setSpoofedUUID(UUID uuid) {
		// TODO Auto-generated method stub

	}

	@Override
	public ProfileProperty[] getSpoofedProperties() {
		return getSession().getProxyData().getProfile().getProperties()
		.stream()
		.map(property -> new ProfileProperty(property.getName(), property.getValue(), property.getSignature()))
		.collect(Collectors.toList())
		.toArray(new ProfileProperty[0]);
	}

	@Override
	public void setSpoofedProperties(ProfileProperty[] properties) {
		// TODO Auto-generated method stub

	}

	@Override
	public Player getBukkitPlayer() {
		return getSession().getPlayer();
	}

}
