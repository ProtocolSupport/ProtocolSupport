package protocolsupport.protocol;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.text.MessageFormat;
import java.util.Collection;

import org.bukkit.entity.Player;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import io.netty.handler.codec.EncoderException;
import io.netty.util.AttributeKey;
import io.netty.util.Recycler;
import io.netty.util.Recycler.Handle;
import io.netty.util.ReferenceCountUtil;
import protocolsupport.api.Connection;
import protocolsupport.api.Connection.PacketListener.PacketEvent;
import protocolsupport.api.Connection.PacketListener.RawPacketEvent;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.api.utils.NetworkState;
import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.handler.IPacketListener;
import protocolsupport.protocol.packet.middleimpl.IPacketData;
import protocolsupport.protocol.pipeline.ChannelHandlers;
import protocolsupport.protocol.pipeline.IPacketCodec;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.storage.ProtocolStorage;
import protocolsupport.protocol.storage.netcache.NetworkDataCache;
import protocolsupport.protocol.utils.authlib.GameProfile;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.zplatform.network.NetworkManagerWrapper;

public class ConnectionImpl extends Connection {

	protected static final AttributeKey<ConnectionImpl> key = AttributeKey.valueOf("PSConnectionImpl");

	protected final NetworkManagerWrapper networkmanager;
	public ConnectionImpl(NetworkManagerWrapper networkmanager) {
		this.networkmanager = networkmanager;
	}

	public NetworkManagerWrapper getNetworkManagerWrapper() {
		return networkmanager;
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
		networkmanager.close("Force connection close");
	}

	@Override
	public void disconnect(String message) {
		Player player = getPlayer();
		if (player != null) {
			player.kickPlayer(message);
			return;
		}
		Object packetListener = networkmanager.getPacketListener();
		if (packetListener instanceof IPacketListener) {
			((IPacketListener) packetListener).disconnect(message);
			return;
		}
		close();
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

	@Override
	public GameProfile getProfile() {
		return (GameProfile) super.getProfile();
	}

	@Override
	public NetworkState getNetworkState() {
		return networkmanager.getNetworkState();
	}

	@Override
	public void sendPacket(Object packet) {
		networkmanager.getChannel().eventLoop().submit(() -> {
			try {
				ChannelHandlerContext ctx = networkmanager.getChannel().pipeline().context(ChannelHandlers.LOGIC);
				if (ctx != null) {
					ctx.writeAndFlush(packet);
				}
			} catch (Throwable t) {
				System.err.println("Error occured while packet sending");
				t.printStackTrace();
			}
		});
	}

	@Override
	public void receivePacket(Object packet) {
		networkmanager.getChannel().eventLoop().submit(() -> {
			try {
				ChannelHandlerContext ctx = networkmanager.getChannel().pipeline().context(ChannelHandlers.LOGIC);
				if (ctx != null) {
					ctx.fireChannelRead(packet);
					ctx.fireChannelReadComplete();
				}
			} catch (Throwable t) {
				System.err.println("Error occured while packet receiving");
				t.printStackTrace();
			}
		});
	}

	@Override
	public void sendRawPacket(byte[] data) {
		ByteBuf dataInst = Unpooled.wrappedBuffer(data);
		networkmanager.getChannel().eventLoop().submit(() -> {
			try {
				ChannelHandlerContext ctx = networkmanager.getChannel().pipeline().context(ChannelHandlers.RAW_CAPTURE_SEND);
				if (ctx != null) {
					ctx.writeAndFlush(dataInst);
				}
			} catch (Throwable t) {
				System.err.println("Error occured while raw packet sending");
				t.printStackTrace();
			}
		});
	}

	@Override
	public void receiveRawPacket(byte[] data) {
		ByteBuf dataInst = Unpooled.wrappedBuffer(data);
		networkmanager.getChannel().eventLoop().submit(() -> {
			try {
				ChannelHandlerContext ctx = networkmanager.getChannel().pipeline().context(ChannelHandlers.RAW_CAPTURE_RECEIVE);
				if (ctx != null) {
					ctx.fireChannelRead(dataInst);
					ctx.fireChannelReadComplete();
				}
			} catch (Throwable t) {
				System.err.println("Error occured while raw packet receiving");
				t.printStackTrace();
			}
		});
	}

	public static ConnectionImpl getFromChannel(Channel channel) {
		return channel.attr(key).get();
	}

	public void storeInChannel(Channel channel) {
		channel.attr(key).set(this);
	}

	public void setVersion(ProtocolVersion version) {
		this.version = version;
	}

	//TODO: a separate class that handles middle writing?
	protected IPacketCodec codec;
	protected ChannelHandlerContext transformerEncoderCtx;
	protected ChannelHandlerContext transformerDecoderCtx;
	protected ClientboundPacketProcessor transformerEncoderHeadProcessor = new ClientboundPacketProcessor(this);
	protected ServerboundPacketProcessor transformerDecoderHeadProcessor = new ServerboundPacketProcessor(this);

	public void setTransformerContentexts(IPacketCodec codec, ChannelHandlerContext transformerEncoderCtx, ChannelHandlerContext transformerDecoderCtx) {
		this.codec = codec;
		this.transformerEncoderCtx = transformerEncoderCtx;
		this.transformerDecoderCtx = transformerDecoderCtx;
	}

	/**
	 * Adds clientbound processor. Added packet processor becomes first.
	 * @param processor packet processor
	 */
	public void addClientboundPacketProcessor(ClientboundPacketProcessor processor) {
		processor.next = transformerEncoderHeadProcessor;
		transformerEncoderHeadProcessor = processor;
	}

	/**
	 * Adds serverbound packet processor. Added packet processor becomes first.
	 * @param processor packet processor
	 */
	public void addServerboundPacketProcessor(ServerboundPacketProcessor processor) {
		processor.next = transformerDecoderHeadProcessor;
		transformerDecoderHeadProcessor = processor;
	}

	protected ChannelPromise transformerEncoderCurrentRealChannelPromise;

	/**
	 * Processes and writes data that was created as a result of clientbound packet transformation. <br>
	 * If promise is null, a void promise is used. <br>
	 * Passed collection is recycled after this function completes.
	 * @param packets packets data
	 * @param promise channel promise
	 * @param flush flush clientbound packets
	 */
	public void writeClientboundPackets(RecyclableCollection<? extends IPacketData> packets, ChannelPromise promise, boolean flush) {
		try {
			if (promise != null) {
				transformerEncoderCurrentRealChannelPromise = promise;
			}
			for (IPacketData packet : packets) {
				transformerEncoderHeadProcessor.process(packet);
			}
			if (flush) {
				transformerEncoderCtx.flush();
			}
		} finally {
			if (transformerEncoderCurrentRealChannelPromise != null) {
				transformerEncoderCurrentRealChannelPromise.setSuccess();
				transformerEncoderCurrentRealChannelPromise = null;
			}
			packets.recycleObjectOnly();
		}
	}

	/**
	 * Processes and writer data that was created as a result of serverbound packet transformation. <br>
	 * Passed collection is recycled after this function completes.
	 * @param packets packets data
	 * @param firereadcomplete fire read complete after reading all packets
	 */
	public void readServerboundPackets(RecyclableCollection<? extends IPacketData> packets, boolean firereadcomplete) {
		try {
			for (IPacketData packet : packets) {
				transformerDecoderHeadProcessor.process(packet);
			}
			if (firereadcomplete) {
				transformerDecoderCtx.fireChannelReadComplete();
			}
		} finally {
			packets.recycleObjectOnly();
		}
	}

	protected void writeClientboundPacket(IPacketCodec codec, IPacketData packetdata) {
		ByteBuf senddata = transformerEncoderCtx.alloc().heapBuffer(packetdata.getDataLength() + PacketType.MAX_PACKET_ID_LENGTH);
		try {
			codec.writePacketId(senddata, packetdata.getPacketType());
			packetdata.writeData(senddata);
			if (transformerEncoderCurrentRealChannelPromise != null) {
				ChannelPromise promise = transformerEncoderCurrentRealChannelPromise;
				transformerEncoderCurrentRealChannelPromise = null;
				transformerEncoderCtx.write(senddata, promise);
			} else {
				transformerEncoderCtx.write(senddata, transformerEncoderCtx.voidPromise());
			}
		} catch (Throwable t) {
			ReferenceCountUtil.safeRelease(senddata);
			throw new EncoderException(t);
		} finally {
			packetdata.recycle();
		}
	}

	protected void writeServerboundPacket(IPacketData packetdata) {
		ByteBuf senddata = transformerDecoderCtx.alloc().heapBuffer(packetdata.getDataLength() + PacketType.MAX_PACKET_ID_LENGTH);
		try {
			VarNumberSerializer.writeVarInt(senddata, packetdata.getPacketType().getId());
			packetdata.writeData(senddata);
			transformerDecoderCtx.fireChannelRead(senddata);
		} catch (Throwable t) {
			ReferenceCountUtil.safeRelease(senddata);
			throw new EncoderException(t);
		} finally {
			packetdata.recycle();
		}
	}

	public static class ClientboundPacketProcessor {

		protected final ConnectionImpl connection;
		public ClientboundPacketProcessor(ConnectionImpl connection) {
			this.connection = connection;
		}

		private ClientboundPacketProcessor next;

		private void write0(IPacketData packet) {
			if (next != null) {
				next.process(packet);
			} else {
				PacketType type = packet.getPacketType();
				switch (type.getDirection()) {
					case CLIENTBOUND: {
						connection.writeClientboundPacket(connection.codec, packet);
						return;
					}
					case SERVERBOUND: {
						connection.writeServerboundPacket(packet);
						connection.transformerDecoderCtx.fireChannelReadComplete();
						return;
					}
					case NONE: {
						packet.writeData(Unpooled.EMPTY_BUFFER);
						return;
					}
					default: {
						throw new IllegalStateException("Unknown direction");
					}
				}
			}
		}

		/**
		 * Actually reads/writes packet
		 * @param packet packet
		 * @param promise
		 */
		protected final void write(IPacketData packet) {
			write0(packet);
		}

		/**
		 * Processes data that was created as a result of clientbound packet transformation
		 * @param packet packet
		 */
		public void process(IPacketData packet) {
			write0(packet);
		}

	}

	public static class ServerboundPacketProcessor {

		protected final ConnectionImpl connection;
		public ServerboundPacketProcessor(ConnectionImpl connection) {
			this.connection = connection;
		}

		private ServerboundPacketProcessor next;

		private void read0(IPacketData packet) {
			if (next != null) {
				next.process(packet);
			} else {
				PacketType type = packet.getPacketType();
				switch (type.getDirection()) {
					case SERVERBOUND: {
						connection.writeServerboundPacket(packet);
						return;
					}
					case CLIENTBOUND: {
						connection.writeClientboundPacket(connection.codec, packet);
						connection.transformerEncoderCtx.flush();
						return;
					}
					case NONE: {
						packet.writeData(Unpooled.EMPTY_BUFFER);
						return;
					}
					default: {
						throw new IllegalStateException("Unknown direction");
					}
				}
			}
		}

		/**
		 * Actually reads/writes packet
		 * @param packet packet
		 */
		protected final void read(IPacketData packet) {
			read0(packet);
		}

		/**
		 * Processes data that was created as a result of serverbound packet transformation.
		 * @param packet packet
		 */
		public void process(IPacketData packet) {
			read0(packet);
		}

	}



	protected static class LPacketEvent extends PacketEvent implements AutoCloseable {

		protected static final Recycler<LPacketEvent> recycler = new Recycler<LPacketEvent>() {
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
			for (PacketListener listener : packetlisteners) {
				try {
					listener.onPacketSending(packetevent);
				} catch (Throwable t) {
					System.err.println("Error occured while handling packet sending");
					t.printStackTrace();
				}
			}
			if (!packetevent.isCancelled()) {
				storeTo.addAll(packetevent.getPackets());
			}
		}
	}

	public void handlePacketReceive(Object packet, Collection<Object> storeTo) {
		try (LPacketEvent packetevent = LPacketEvent.create(packet)) {
			for (PacketListener listener : packetlisteners) {
				try {
					listener.onPacketReceiving(packetevent);
				} catch (Throwable t) {
					System.err.println("Error occured while handling packet receiving");
					t.printStackTrace();
				}
			}
			if (!packetevent.isCancelled()) {
				storeTo.addAll(packetevent.getPackets());
			}
		}
	}

	protected static class LRawPacketEvent extends RawPacketEvent implements AutoCloseable {

		protected static final Recycler<LRawPacketEvent> recycler = new Recycler<LRawPacketEvent>() {
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
			for (PacketListener listener : packetlisteners) {
				try {
					listener.onRawPacketSending(rawpacketevent);
				} catch (Throwable t) {
					System.err.println("Error occured while handling raw packet sending");
					t.printStackTrace();
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
			for (PacketListener listener : packetlisteners) {
				try {
					listener.onRawPacketReceiving(rawpacketevent);
				} catch (Throwable t) {
					System.err.println("Error occured while handling raw packet receiving");
					t.printStackTrace();
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
			"{0}(profile: {1}, player: {2}, address: {3}, rawaddress: {4}, version: {5}, metadata: {6})",
			getClass().getName(), getProfile(), getPlayer(), getAddress(), getRawAddress(), getVersion(), metadata
		);
	}

}
