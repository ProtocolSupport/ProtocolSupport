package protocolsupport.protocol;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.text.MessageFormat;
import java.util.Collection;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;

import org.bukkit.entity.Player;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoop;
import io.netty.util.AttributeKey;
import io.netty.util.Recycler;
import io.netty.util.Recycler.Handle;
import protocolsupport.ProtocolSupport;
import protocolsupport.api.Connection;
import protocolsupport.api.Connection.PacketListener.PacketEvent;
import protocolsupport.api.Connection.PacketListener.RawPacketEvent;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.api.chat.components.BaseComponent;
import protocolsupport.api.chat.components.TextComponent;
import protocolsupport.api.utils.NetworkState;
import protocolsupport.protocol.packet.handler.IPacketListener;
import protocolsupport.protocol.pipeline.ChannelHandlers;
import protocolsupport.protocol.pipeline.IPacketDataChannelIO;
import protocolsupport.protocol.pipeline.IPacketIdCodec;
import protocolsupport.protocol.pipeline.common.LogicHandler;
import protocolsupport.protocol.storage.ProtocolStorage;
import protocolsupport.protocol.storage.netcache.NetworkDataCache;
import protocolsupport.protocol.utils.authlib.LoginProfile;
import protocolsupport.zplatform.ServerPlatform;
import protocolsupport.zplatform.network.NetworkManagerWrapper;

public class ConnectionImpl extends Connection {

	protected static final AttributeKey<ConnectionImpl> key = AttributeKey.valueOf("PSConnectionImpl");

	public static ConnectionImpl getFromChannel(Channel channel) {
		return channel.attr(key).get();
	}


	protected final NetworkManagerWrapper networkmanager;
	protected final PacketDataChannelIO packetdataIO;
	protected LogicHandler logicHandler;
	protected ChannelHandlerContext logicHandlerCtx;
	protected ChannelHandlerContext rawSendCtx;
	protected ChannelHandlerContext rawRecvCtx;

	public ConnectionImpl(NetworkManagerWrapper networkmanager) {
		this.networkmanager = networkmanager;
		this.packetdataIO = new PacketDataChannelIO(this);
		this.networkmanager.getChannel().attr(key).set(this);
	}

	public void destroy() {
		if (networkmanager.getPacketListener() instanceof IPacketListener ilistener) {
			ilistener.destroy();
		}
		if (packetdataIO != null) {
			packetdataIO.destroy();
		}
	}

	public void setVersion(ProtocolVersion version) {
		this.version = version;
	}

	public void initPacketDataIO(IPacketIdCodec packetIdCodec) {
		ChannelPipeline pipeline = networkmanager.getChannel().pipeline();
		this.logicHandlerCtx = pipeline.context(ChannelHandlers.LOGIC);
		this.logicHandler = (LogicHandler) logicHandlerCtx.handler();
		this.rawSendCtx = pipeline.context(ChannelHandlers.RAW_CAPTURE_SEND);
		this.rawRecvCtx = pipeline.context(ChannelHandlers.RAW_CAPTURE_RECEIVE);
		this.packetdataIO.init(packetIdCodec, pipeline.context(ChannelHandlers.ENCODER_TRANSFORMER), pipeline.context(ChannelHandlers.DECODER_TRANSFORMER));
	}

	public NetworkManagerWrapper getNetworkManagerWrapper() {
		return networkmanager;
	}

	public IPacketDataChannelIO getPacketDataIO() {
		return packetdataIO;
	}

	@Override
	public Object getNetworkManager() {
		return networkmanager.unwrap();
	}

	@Override
	public boolean isConnected() {
		return networkmanager.isConnected();
	}

	@Override
	public void close() {
		networkmanager.close(new TextComponent("Force connection close"));
	}

	@Override
	public void disconnect(BaseComponent message) {
		Player player = getPlayer();
		if (player != null) {
			player.kickPlayer(message.toLegacyText());
			return;
		}
		if (networkmanager.getPacketListener() instanceof IPacketListener ilistener) {
			ilistener.disconnect(message);
			return;
		}
		close();
	}

	@Override
	public void disconnect(String message) {
		disconnect(BaseComponent.fromMessage(message));
	}

	@Override
	public InetSocketAddress getVirtualHost() {
		return networkmanager.getVirtualHost();
	}

	@Override
	public InetSocketAddress getRawAddress() {
		return networkmanager.getRawAddress();
	}

	@Override
	public InetSocketAddress getAddress() {
		return networkmanager.getAddress();
	}

	@Override
	public void changeAddress(InetSocketAddress newRemote) {
		SocketAddress primaryaddr = networkmanager.getRawAddress();
		ProtocolStorage.addAddress(primaryaddr, newRemote);
		networkmanager.setAddress(newRemote);
	}

	@Override
	public Player getPlayer() {
		return networkmanager.getBukkitPlayer();
	}

	public LoginProfile getLoginProfile() {
		return (LoginProfile) profile;
	}

	public void setWrappedProfile(Player player) {
		profile = ServerPlatform.get().getMiscUtils().createWrappedProfile(getLoginProfile(), player);
	}

	@Override
	public NetworkState getNetworkState() {
		return networkmanager.getNetworkState();
	}

	@Override
	public EventLoop getIOExecutor() {
		return networkmanager.getChannel().eventLoop();
	}

	public <V> Future<V> submitIOTask(Callable<V> task) {
		return getIOExecutor().submit(() -> {
			if (!isConnected()) {
				return null;
			}
			return task.call();
		});
	}

	public void submitIOTask(Runnable task) {
		getIOExecutor().submit(() -> {
			try {
				if (!isConnected()) {
					return;
				}
				task.run();
			} catch (Throwable e) {
				ProtocolSupport.logErrorWarning("Unhandled exception occured in task submitted to event loop", e);
			}
		});
	}

	@Override
	public void sendPacket(Object packet) {
		submitIOTask(() -> {
			try {
				logicHandler.write(logicHandlerCtx, packet, logicHandlerCtx.voidPromise());
				logicHandler.flush(logicHandlerCtx);
			} catch (Exception e) {
				logicHandlerCtx.channel().pipeline().fireExceptionCaught(e);
			}
		});
	}

	@Override
	public void receivePacket(Object packet) {
		submitIOTask(() -> {
			try {
				logicHandlerCtx.fireChannelRead(packet);
				logicHandlerCtx.fireChannelReadComplete();
			} catch (Exception e) {
				logicHandlerCtx.channel().pipeline().fireExceptionCaught(e);
			}
		});
	}

	@Override
	public void sendRawPacket(byte[] data) {
		ByteBuf dataInst = Unpooled.wrappedBuffer(data);
		submitIOTask(() -> {
			rawSendCtx.writeAndFlush(dataInst);
		});
	}

	@Override
	public void receiveRawPacket(byte[] data) {
		ByteBuf dataInst = Unpooled.wrappedBuffer(data);
		submitIOTask(() -> {
			rawRecvCtx.fireChannelRead(dataInst);
			rawRecvCtx.fireChannelReadComplete();
		});
	}


	protected static class LPacketEvent extends PacketEvent implements AutoCloseable {

		protected static final Recycler<LPacketEvent> recycler = new Recycler<>() {
			@Override
			protected LPacketEvent newObject(Handle<LPacketEvent> handle) {
				return new LPacketEvent(handle);
			}
		};

		public static LPacketEvent create(Object packet) {
			LPacketEvent packetevent = recycler.get();
			packetevent.mainpacket = packet;
			packetevent.packets.add(packet);
			return packetevent;
		}

		protected final Handle<LPacketEvent> handle;

		protected LPacketEvent(Handle<LPacketEvent> handle) {
			this.handle = handle;
		}

		public void recycle() {
			this.mainpacket = null;
			this.packets.clear();
			this.cancelled = false;
			this.handle.recycle(this);
		}

		@Override
		public void close() {
			recycle();
		}

	}

	public void handlePacketSend(Object packet, Collection<Object> storeTo) {
		try (LPacketEvent packetevent = LPacketEvent.create(packet)) {
			for (PacketListener listener : flatPacketListeners) {
				try {
					listener.onPacketSending(packetevent);
				} catch (Throwable t) {
					ProtocolSupport.logErrorWarning("Unhandled exception occured while handling packet sending", t);
				}
			}
			if (!packetevent.isCancelled()) {
				storeTo.addAll(packetevent.getPackets());
			}
		}
	}

	public void handlePacketReceive(Object packet, Collection<Object> storeTo) {
		try (LPacketEvent packetevent = LPacketEvent.create(packet)) {
			for (PacketListener listener : flatPacketListeners) {
				try {
					listener.onPacketReceiving(packetevent);
				} catch (Throwable t) {
					ProtocolSupport.logErrorWarning("Unhandled exception occured while handling packet receiving", t);
				}
			}
			if (!packetevent.isCancelled()) {
				storeTo.addAll(packetevent.getPackets());
			}
		}
	}

	protected static class LRawPacketEvent extends RawPacketEvent implements AutoCloseable {

		protected static final Recycler<LRawPacketEvent> recycler = new Recycler<>() {
			@Override
			protected LRawPacketEvent newObject(Handle<LRawPacketEvent> handle) {
				return new LRawPacketEvent(handle);
			}
		};

		public static LRawPacketEvent create(ByteBuf data) {
			LRawPacketEvent packetevent = recycler.get();
			packetevent.data = data;
			packetevent.cancelled = false;
			return packetevent;
		}

		protected final Handle<LRawPacketEvent> handle;
		protected LRawPacketEvent(Handle<LRawPacketEvent> handle) {
			this.handle = handle;
		}

		public void recycle() {
			this.handle.recycle(this);
		}

		public ByteBuf getDirectData() {
			return this.data;
		}

		@Override
		public void close() {
			recycle();
		}

	}

	public ByteBuf handleRawPacketSend(ByteBuf data) {
		try (LRawPacketEvent rawpacketevent = LRawPacketEvent.create(data)) {
			for (PacketListener listener : flatPacketListeners) {
				try {
					listener.onRawPacketSending(rawpacketevent);
				} catch (Throwable t) {
					ProtocolSupport.logErrorWarning("Unhandled exception occured while handling raw packet sending", t);
				}
			}
			if (rawpacketevent.isCancelled()) {
				rawpacketevent.getDirectData().release();
				return null;
			} else {
				return rawpacketevent.getDirectData();
			}
		}
	}

	public ByteBuf handleRawPacketReceive(ByteBuf data) {
		try (LRawPacketEvent rawpacketevent = LRawPacketEvent.create(data)) {
			for (PacketListener listener : flatPacketListeners) {
				try {
					listener.onRawPacketReceiving(rawpacketevent);
				} catch (Throwable t) {
					ProtocolSupport.logErrorWarning("Unhandled exception occured while handling rawpacket receiving", t);
				}
			}
			if (rawpacketevent.isCancelled()) {
				rawpacketevent.getDirectData().release();
				return null;
			} else {
				return rawpacketevent.getDirectData();
			}
		}
	}

	protected final NetworkDataCache cache = new NetworkDataCache();

	public NetworkDataCache getCache() {
		return cache;
	}

	@Override
	public String toString() {
		return MessageFormat.format(
			"{0}(profile: {1}, player: {2}, address: {3}, rawaddress: {4}, version: {5})",
			getClass().getName(), getProfile(), getPlayer(), getAddress(), getRawAddress(), getVersion()
		);
	}

}
