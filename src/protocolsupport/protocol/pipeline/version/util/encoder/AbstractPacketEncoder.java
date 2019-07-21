package protocolsupport.protocol.pipeline.version.util.encoder;

import java.text.MessageFormat;
import java.util.Arrays;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
import io.netty.handler.codec.DecoderException;
import io.netty.handler.codec.EncoderException;
import io.netty.util.ReferenceCountUtil;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.packet.middleimpl.IPacketData;
import protocolsupport.protocol.pipeline.IPacketCodec;
import protocolsupport.protocol.pipeline.version.util.MiddlePacketRegistry;
import protocolsupport.protocol.serializer.MiscSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.zplatform.ServerPlatform;

public abstract class AbstractPacketEncoder extends ChannelOutboundHandlerAdapter {

	protected final ConnectionImpl connection;
	protected final IPacketCodec codec;
	protected final MiddlePacketRegistry<ClientBoundMiddlePacket> registry;

	public AbstractPacketEncoder(ConnectionImpl connection, IPacketCodec codec) {
		this.connection = connection;
		this.codec = codec;
		this.registry = new MiddlePacketRegistry<>(connection);
	}

	@Override
	public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
		if (!(msg instanceof ByteBuf)) {
			super.write(ctx, msg, promise);
			return;
		}

		ByteBuf input = (ByteBuf) msg;
		if (!input.isReadable()) {
			input.release();
			promise.setSuccess();
			return;
		}

		try {
			ClientBoundMiddlePacket packetTransformer = registry.getTransformer(connection.getNetworkState(), VarNumberSerializer.readVarInt(input));
			packetTransformer.readFromServerData(input);

			if (input.isReadable()) {
				throw new DecoderException("Did not read all data from packet, bytes left: " + input.readableBytes());
			}
			if (!packetTransformer.postFromServerRead()) {
				promise.setSuccess();
				return;
			}

			boolean hadClientboundPacket = false;
			boolean hadServerboundPacket = false;
			try (RecyclableCollection<? extends IPacketData> packets = processPackets(ctx.channel(), packetTransformer.toData())) {
				for (IPacketData packet : packets) {
					PacketType type = packet.getPacketType();
					switch (type.getDirection()) {
						case CLIENTBOUND: {
							ByteBuf senddata = ctx.alloc().heapBuffer(packet.getDataLength() + PacketType.MAX_PACKET_ID_LENGTH);
							try {
								codec.writePacketId(senddata, type);
								packet.writeData(senddata);
								ctx.write(senddata, promise);
								promise = ctx.newPromise();
								hadClientboundPacket = true;
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
								connection.getTransformerDecoderCtx().fireChannelRead(senddata);
								hadServerboundPacket = true;
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
			if (!hadClientboundPacket) {
				promise.setSuccess();
			}
			if (hadServerboundPacket) {
				connection.getTransformerDecoderCtx().fireChannelReadComplete();
			}
		} catch (Exception exception) {
			if (ServerPlatform.get().getMiscUtils().isDebugging()) {
				input.readerIndex(0);
				throw new EncoderException(MessageFormat.format(
					"Unable to transform or read packet data {0}", Arrays.toString(MiscSerializer.readAllBytes(input))
				), exception);
			} else {
				throw exception;
			}
		} finally {
			input.release();
		}
	}

	protected RecyclableCollection<? extends IPacketData> processPackets(Channel channel, RecyclableCollection<? extends IPacketData> data) {
		return data;
	}

}
