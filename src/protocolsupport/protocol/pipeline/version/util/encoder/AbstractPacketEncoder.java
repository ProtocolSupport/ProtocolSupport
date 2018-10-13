package protocolsupport.protocol.pipeline.version.util.encoder;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.List;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.DecoderException;
import io.netty.handler.codec.EncoderException;
import protocolsupport.api.Connection;
import protocolsupport.api.utils.NetworkState;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.MiscSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.utils.registry.MiddlePacketRegistry;
import protocolsupport.utils.netty.MessageToMessageEncoder;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.zplatform.ServerPlatform;

public abstract class AbstractPacketEncoder extends MessageToMessageEncoder<ByteBuf> {

	protected final Connection connection;
	protected final MiddlePacketRegistry<ClientBoundMiddlePacket> registry;
	public AbstractPacketEncoder(ConnectionImpl connection) {
		this.connection = connection;
		this.registry = new MiddlePacketRegistry<>(connection);
	}

	@Override
	public void encode(ChannelHandlerContext ctx, ByteBuf input, List<Object> output) throws Exception {
		if (!input.isReadable()) {
			return;
		}
		NetworkState currentProtocol = connection.getNetworkState();
		try {
			ClientBoundMiddlePacket packetTransformer = registry.getTransformer(currentProtocol, VarNumberSerializer.readVarInt(input));
			packetTransformer.readFromServerData(input);
			if (input.isReadable()) {
				throw new DecoderException("Did not read all data from packet, bytes left: " + input.readableBytes());
			}
			if (packetTransformer.postFromServerRead()) {
				try (RecyclableCollection<ClientBoundPacketData> data = processPackets(ctx.channel(), packetTransformer.toData())) {
					for (ClientBoundPacketData packetdata : data) {
						ByteBuf senddata = ctx.alloc().heapBuffer(packetdata.readableBytes() + VarNumberSerializer.MAX_LENGTH);
						writePacketId(senddata, getNewPacketId(currentProtocol, packetdata.getPacketId()));
						senddata.writeBytes(packetdata);
						output.add(senddata);
					}
				}
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
		}
	}

	protected RecyclableCollection<ClientBoundPacketData> processPackets(Channel channel, RecyclableCollection<ClientBoundPacketData> data) {
		return data;
	}

	protected abstract void writePacketId(ByteBuf to, int packetId);

	protected abstract int getNewPacketId(NetworkState currentProtocol, int oldPacketId);

}
