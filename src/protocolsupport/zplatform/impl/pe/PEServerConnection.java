package protocolsupport.zplatform.impl.pe;

import java.net.InetSocketAddress;
import java.text.MessageFormat;
import java.util.List;

import org.bukkit.Bukkit;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.epoll.EpollSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.ByteToMessageCodec;
import io.netty.handler.codec.DecoderException;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;
import protocolsupport.protocol.pipeline.common.VarIntFrameDecoder;
import protocolsupport.protocol.pipeline.common.VarIntFrameEncoder;
import protocolsupport.protocol.serializer.MiscSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.utils.netty.ChannelInitializer;
import protocolsupport.utils.netty.Compressor;
import protocolsupport.utils.netty.Decompressor;
import protocolsupport.zplatform.ServerPlatform;
import protocolsupport.zplatform.impl.encapsulated.EncapsulatedProtocolInfo;
import protocolsupport.zplatform.impl.encapsulated.EncapsulatedProtocolUtils;

public class PEServerConnection extends SimpleChannelInboundHandler<ByteBuf> {

	private final Channel clientchannel;
	private final ByteBuf handshakepacket;
	public PEServerConnection(Channel clientchannel, ByteBuf handshakepacket) {
		this.clientchannel = clientchannel;
		this.handshakepacket = handshakepacket;
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		ctx.writeAndFlush(handshakepacket).addListener(ChannelFutureListener.FIRE_EXCEPTION_ON_FAILURE);
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		if (ServerPlatform.get().getMiscUtils().isDebugging()) {
			System.err.println("PEProxy server connection exception occured");
			cause.printStackTrace();
		}
		ctx.channel().close();
		clientchannel.close();
	}

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, ByteBuf bytebuf) throws Exception {
		ByteBuf cbytebuf = Unpooled.copiedBuffer(bytebuf);
		clientchannel.eventLoop().execute(() ->
			clientchannel
			.writeAndFlush(cbytebuf)
			.addListener(ChannelFutureListener.FIRE_EXCEPTION_ON_FAILURE
		));
	}


	public static Channel connectToServer(Channel clientchannel, ByteBuf handshakepacket) {
		InetSocketAddress remote = (InetSocketAddress) clientchannel.remoteAddress();
		EventLoopGroup loopgroup = ServerPlatform.get().getMiscUtils().getServerEventLoop();
		Class<? extends Channel> channel = loopgroup instanceof EpollEventLoopGroup ? EpollSocketChannel.class : NioSocketChannel.class;
		handshakepacket.readerIndex(0);
		String serveraddr = Bukkit.getIp();
		if (serveraddr.isEmpty()) {
			serveraddr = "127.0.0.1";
		}
		return new Bootstrap()
		.channel(channel)
		.group(loopgroup)
		.handler(new ChannelInitializer() {
			@Override
			protected void initChannel(Channel channel) throws Exception {
				channel.pipeline()
				.addLast(new ChannelInboundHandlerAdapter() {
					@Override
					public void channelActive(ChannelHandlerContext ctx) throws Exception {
						channel.pipeline().remove(this);
						ctx.writeAndFlush(createHandshake(remote)).addListener(ChannelFutureListener.FIRE_EXCEPTION_ON_FAILURE);
						super.channelActive(ctx);
					}
				})
				.addLast("idlestatehandler", new IdleStateHandler(0, 5, 0))
				.addLast("keepalive", new ChannelInboundHandlerAdapter() {
					@Override
					public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
						if (evt instanceof IdleStateEvent) {
							if (((IdleStateEvent) evt).state() == IdleState.WRITER_IDLE) {
								ByteBuf rawemptyframedpacket = Unpooled.buffer();
								VarNumberSerializer.writeVarInt(rawemptyframedpacket, 0);
								ctx.writeAndFlush(rawemptyframedpacket);
							}
						}
					}
				})
				.addLast("framing", new ByteToMessageCodec<ByteBuf>() {
					private final VarIntFrameDecoder splitter = new VarIntFrameDecoder();
					private final VarIntFrameEncoder prepender = new VarIntFrameEncoder();
					@Override
					protected void decode(ChannelHandlerContext ctx, ByteBuf input, List<Object> list) throws Exception {
						if (!input.isReadable()) {
							return;
						}
						splitter.split(ctx, input, list);
					}
					@Override
					protected void encode(ChannelHandlerContext ctx, ByteBuf input, ByteBuf output) throws Exception {
						if (!input.isReadable()) {
							return;
						}
						prepender.prepend(ctx, input, output);
					}
				})
				.addLast("compression", new ByteToMessageCodec<ByteBuf>() {
					private final int maxPacketLength = 2 << 21;
					private final int threshold = ServerPlatform.get().getMiscUtils().getCompressionThreshold();
					private final Compressor compressor = Compressor.create();
					private final Decompressor decompressor = Decompressor.create();
					@Override
					public void channelInactive(ChannelHandlerContext ctx) throws Exception {
						super.channelInactive(ctx);
						compressor.recycle();
						decompressor.recycle();
					}
					@Override
					protected void encode(ChannelHandlerContext ctx, ByteBuf input, ByteBuf output) throws Exception {
						int readable = input.readableBytes();
						if (readable == 0) {
							return;
						}
						if (readable < threshold) {
							VarNumberSerializer.writeVarInt(output, 0);
							output.writeBytes(input);
						} else {
							VarNumberSerializer.writeVarInt(output, readable);
							output.writeBytes(compressor.compress(MiscSerializer.readAllBytes(input)));
						}
					}
					@Override
					protected void decode(ChannelHandlerContext ctx, ByteBuf input, List<Object> list) throws Exception {
						if (!input.isReadable()) {
							return;
						}
						int uncompressedlength = VarNumberSerializer.readVarInt(input);
						if (uncompressedlength == 0) {
							list.add(input.readBytes(input.readableBytes()));
						} else {
							if (uncompressedlength > maxPacketLength) {
								throw new DecoderException(MessageFormat.format("Badly compressed packet - size of {0} is larger than protocol maximum of {1}", uncompressedlength, maxPacketLength));
							}
							list.add(Unpooled.wrappedBuffer(decompressor.decompress(MiscSerializer.readAllBytes(input), uncompressedlength)));
						}
					}
				})
				.addLast("handler", new PEServerConnection(clientchannel, handshakepacket));
			}
		})
		.connect(serveraddr, Bukkit.getPort())
		.channel();
	}

	private static ByteBuf createHandshake(InetSocketAddress remote) {
		ByteBuf data = Unpooled.buffer();
		data.writeByte(0);
		EncapsulatedProtocolUtils.writeInfo(data, new EncapsulatedProtocolInfo(remote, true));
		return data;
	}

}
