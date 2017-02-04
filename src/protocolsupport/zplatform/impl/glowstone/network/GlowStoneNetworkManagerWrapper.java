package protocolsupport.zplatform.impl.glowstone.network;

import java.lang.reflect.Field;
import java.net.InetSocketAddress;
import java.text.MessageFormat;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

import javax.crypto.SecretKey;

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
import protocolsupport.utils.ReflectionUtils;
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

	private static final Field atomicSessionField = ReflectionUtils.getField(MessageHandler.class, "session");

	private final MessageHandler handler;
	public GlowStoneNetworkManagerWrapper(MessageHandler handler) {
		this.handler = handler;
	}

	@SuppressWarnings("unchecked")
	private GlowSession getSession() {
		try {
			return ((AtomicReference<GlowSession>) atomicSessionField.get(handler)).get();
		} catch (IllegalArgumentException | IllegalAccessException e) {
			throw new RuntimeException("Unable to get session from message handler", e);
		}
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
	public void enableEncryption(SecretKey key) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public UUID getSpoofedUUID() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setSpoofedUUID(UUID uuid) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ProfileProperty[] getSpoofedProperties() {
		// TODO Auto-generated method stub
		return null;
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
