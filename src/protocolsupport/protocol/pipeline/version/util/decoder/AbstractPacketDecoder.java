package protocolsupport.protocol.pipeline.version.util.decoder;

import java.text.MessageFormat;
import java.util.Arrays;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.DecoderException;
import io.netty.handler.codec.EncoderException;
import io.netty.util.ReferenceCountUtil;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middle.ServerBoundMiddlePacket;
import protocolsupport.protocol.packet.middleimpl.IPacketData;
import protocolsupport.protocol.pipeline.IPacketCodec;
import protocolsupport.protocol.pipeline.version.util.MiddlePacketRegistry;
import protocolsupport.protocol.serializer.MiscSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.zplatform.ServerPlatform;

public abstract class AbstractPacketDecoder extends SimpleChannelInboundHandler<ByteBuf> {

	protected final ConnectionImpl connection;
	protected final IPacketCodec codec;
	protected final MiddlePacketRegistry<ServerBoundMiddlePacket> registry;

	public AbstractPacketDecoder(ConnectionImpl connection, IPacketCodec codec) {
		this.connection = connection;
		this.codec = codec;
		this.registry = new MiddlePacketRegistry<>(connection);
	}

	protected void decodeAndTransform(ChannelHandlerContext ctx, ByteBuf input) {
		ServerBoundMiddlePacket packetTransformer = registry.getTransformer(connection.getNetworkState(), codec.readPacketId(input));
		packetTransformer.readFromClientData(input);
		try (RecyclableCollection<? extends IPacketData> packets = processPackets(ctx.channel(), packetTransformer.toNative())) {
			for (IPacketData packet : packets) {
				PacketType type = packet.getPacketType();
				switch (type.getDirection()) {
					case CLIENTBOUND: {
						ByteBuf senddata = ctx.alloc().heapBuffer(packet.getDataLength() + PacketType.MAX_PACKET_ID_LENGTH);
						try {
							codec.writePacketId(senddata, type);
							packet.writeData(senddata);
							connection.getTransformerEncoderCtx().writeAndFlush(senddata);
						} catch (Throwable t) {
							ReferenceCountUtil.safeRelease(senddata);
							throw new EncoderException(t);
						}
						break;
					}
					case SERVERBOUND: {
						ByteBuf senddata = ctx.alloc().heapBuffer(packet.getDataLength() + PacketType.MAX_PACKET_ID_LENGTH);
						try {
							VarNumberSerializer.writeVarInt(senddata, type.getId());
							packet.writeData(senddata);
							ctx.fireChannelRead(senddata);
						} catch (Throwable t) {
							ReferenceCountUtil.safeRelease(senddata);
							throw new EncoderException(t);
						}
						break;
					}
					case NONE: {
						packet.writeData(Unpooled.EMPTY_BUFFER);
						break;
					}
				}
			}
		}
	}

	protected RecyclableCollection<? extends IPacketData> processPackets(Channel channel, RecyclableCollection<? extends IPacketData> data) {
		return data;
	}

	protected void throwFailedTransformException(Exception exception, ByteBuf data) throws Exception {
		if (ServerPlatform.get().getMiscUtils().isDebugging()) {
			data.readerIndex(0);
			throw new DecoderException(MessageFormat.format(
				"Unable to transform or read packet data {0}", Arrays.toString(MiscSerializer.readAllBytes(data))
			), exception);
		} else {
			throw exception;
		}
	}

}
