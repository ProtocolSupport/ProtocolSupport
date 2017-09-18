package protocolsupport.zplatform.impl.pe;

import java.net.InetSocketAddress;
import java.text.MessageFormat;
import java.util.List;
import java.util.zip.Inflater;

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
import io.netty.handler.timeout.IdleStateHandler;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.pipeline.common.VarIntFrameDecoder;
import protocolsupport.protocol.pipeline.common.VarIntFrameEncoder;
import protocolsupport.protocol.serializer.MiscSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.utils.netty.ChannelInitializer;
import protocolsupport.utils.netty.Compressor;
import protocolsupport.zplatform.ServerPlatform;
import protocolsupport.zplatform.impl.encapsulated.EncapsulatedProtocolInfo;
import protocolsupport.zplatform.impl.encapsulated.EncapsulatedProtocolUtils;

public class PEProxyNetworkManager extends SimpleChannelInboundHandler<ByteBuf> {

	private Channel serverconnection;

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		super.channelInactive(ctx);
		if (serverconnection != null) {
			serverconnection.close();
		}
	}

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, ByteBuf bytebuf) throws Exception {
		ByteBuf cbytebuf = Unpooled.copiedBuffer(bytebuf);
		if (serverconnection == null) {
			serverconnection = connectToServer(ctx.channel(), cbytebuf);
		} else {
			serverconnection.eventLoop().execute(() -> serverconnection.writeAndFlush(cbytebuf));
		}
	}

	//TODO: rewrite this
	private static Channel connectToServer(Channel peclientchannel, ByteBuf loginpacket) {
		InetSocketAddress remote = (InetSocketAddress) peclientchannel.remoteAddress();
		int protocolversion = getProtocolVersion(loginpacket);
		EventLoopGroup loopgroup = ServerPlatform.get().getMiscUtils().getServerEventLoop();
		Class<? extends Channel> channel = loopgroup instanceof EpollEventLoopGroup ? EpollSocketChannel.class : NioSocketChannel.class;
		loginpacket.readerIndex(0);
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
						ctx.writeAndFlush(createHandshake(remote, protocolversion)).addListener(ChannelFutureListener.FIRE_EXCEPTION_ON_FAILURE);
						super.channelActive(ctx);
					}
				})
				.addLast("idlestatehandler", new IdleStateHandler(0, 5, 0))
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
					private final int maxPacketLength = (int) Math.pow(2, 7 * 3);
					private final Compressor compressor = Compressor.create();
					private final int threshold = ServerPlatform.get().getMiscUtils().getCompressionThreshold();
					private final Inflater inflater = new Inflater();
					@Override
					public void channelInactive(ChannelHandlerContext ctx) throws Exception {
						super.channelInactive(ctx);
						compressor.recycle();
						inflater.end();
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
							inflater.setInput(MiscSerializer.readAllBytes(input));
							byte[] uncompressed = new byte[uncompressedlength];
							inflater.inflate(uncompressed);
							list.add(Unpooled.wrappedBuffer(uncompressed));
							inflater.reset();
						}
					}
				})
				.addLast("handler", new SimpleChannelInboundHandler<ByteBuf>() {
					@Override
					public void channelActive(ChannelHandlerContext ctx) throws Exception {
						ctx.writeAndFlush(loginpacket).addListener(ChannelFutureListener.FIRE_EXCEPTION_ON_FAILURE);
					}
					@Override
					protected void channelRead0(ChannelHandlerContext ctx, ByteBuf bytebuf) throws Exception {
						peclientchannel.writeAndFlush(Unpooled.copiedBuffer(bytebuf)).addListener(ChannelFutureListener.FIRE_EXCEPTION_ON_FAILURE);
					}
					@Override
					public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
						if (ServerPlatform.get().getMiscUtils().isDebugging()) {
							System.err.println("PE proxy exception occured");
							cause.printStackTrace();
						}
						peclientchannel.close();
					}
					@Override
					public void channelInactive(ChannelHandlerContext ctx) throws Exception {
						super.channelInactive(ctx);
						peclientchannel.close();
					}
				});
			}
		})
		.connect(serveraddr, Bukkit.getPort())
		.channel();
	}

	private static ByteBuf createHandshake(InetSocketAddress remote, int protocolversion) {
		ByteBuf data = Unpooled.buffer();
		data.writeByte(0);
		EncapsulatedProtocolUtils.writeInfo(data, new EncapsulatedProtocolInfo(remote, true, ProtocolVersion.MINECRAFT_PE));
		return data;
	}

	private static int getProtocolVersion(ByteBuf loginpacket) {
		loginpacket.skipBytes(3); //packet id and headers
		return loginpacket.readInt();
	}

}
