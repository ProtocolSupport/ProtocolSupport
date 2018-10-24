package protocolsupport.protocol.pipeline.version.util.decoder;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.List;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.DecoderException;
import io.netty.handler.codec.MessageToMessageDecoder;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middle.ServerBoundMiddlePacket;
import protocolsupport.protocol.packet.middleimpl.ServerBoundPacketData;
import protocolsupport.protocol.serializer.MiscSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.utils.registry.MiddlePacketRegistry;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.zplatform.ServerPlatform;

public abstract class AbstractPacketDecoder extends MessageToMessageDecoder<ByteBuf> {

	protected final ConnectionImpl connection;
	protected final MiddlePacketRegistry<ServerBoundMiddlePacket> registry;

	public AbstractPacketDecoder(ConnectionImpl connection) {
		this.connection = connection;
		this.registry = new MiddlePacketRegistry<>(connection);
	}

	protected abstract int readPacketId(ByteBuf buffer);

	protected void decodeAndTransform(ChannelHandlerContext ctx, ByteBuf buffer, List<Object> to) {
		ServerBoundMiddlePacket packetTransformer = registry.getTransformer(connection.getNetworkState(), readPacketId(buffer));
		packetTransformer.readFromClientData(buffer);
		try (RecyclableCollection<ServerBoundPacketData> data = processPackets(ctx.channel(), packetTransformer.toNative())) {
			for (ServerBoundPacketData packetdata : data) {
				ByteBuf receivedata = ctx.channel().alloc().heapBuffer(packetdata.readableBytes() + VarNumberSerializer.MAX_LENGTH);
				VarNumberSerializer.writeVarInt(receivedata, packetdata.getPacketId());
				receivedata.writeBytes(packetdata);
				to.add(receivedata);
			}
		}
	}

	protected RecyclableCollection<ServerBoundPacketData> processPackets(Channel channel, RecyclableCollection<ServerBoundPacketData> data) {
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
