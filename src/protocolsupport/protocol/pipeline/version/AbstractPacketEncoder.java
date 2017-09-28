package protocolsupport.protocol.pipeline.version;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.EncoderException;
import protocolsupport.api.Connection;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.MiscSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.storage.NetworkDataCache;
import protocolsupport.protocol.utils.registry.MiddleTransformerRegistry;
import protocolsupport.utils.netty.Allocator;
import protocolsupport.utils.netty.MessageToMessageEncoder;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.zplatform.ServerPlatform;
import protocolsupport.zplatform.network.NetworkState;

public abstract class AbstractPacketEncoder extends MessageToMessageEncoder<ByteBuf> {

	public AbstractPacketEncoder(Connection connection, NetworkDataCache storage) {
		registry.setCallBack(object -> {
			object.setConnection(connection);
			object.setSharedStorage(storage);
		});
	}

	protected final MiddleTransformerRegistry<ClientBoundMiddlePacket> registry = new MiddleTransformerRegistry<>();

	@Override
	public void encode(ChannelHandlerContext ctx, ByteBuf input, List<Object> output) throws Exception {
		if (!input.isReadable()) {
			return;
		}
		NetworkState currentProtocol = ServerPlatform.get().getMiscUtils().getNetworkStateFromChannel(ctx.channel());
		ClientBoundMiddlePacket packetTransformer = null;
		try {
			packetTransformer = registry.getTransformer(currentProtocol, VarNumberSerializer.readVarInt(input));
			packetTransformer.readFromServerData(input);
			if (packetTransformer.postFromServerRead()) {
				try (RecyclableCollection<ClientBoundPacketData> data = packetTransformer.toData()) {
					for (ClientBoundPacketData packetdata : data) {
						ByteBuf senddata = Allocator.allocateBuffer();
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
					"Unable to transform or read middle packet {0} (data: {1})",
					Objects.toString(packetTransformer), Arrays.toString(MiscSerializer.readAllBytes(input))
				), exception);
			} else {
				throw exception;
			}
		}
	}

	protected abstract void writePacketId(ByteBuf to, int packetId);

	protected abstract int getNewPacketId(NetworkState currentProtocol, int oldPacketId);

}
