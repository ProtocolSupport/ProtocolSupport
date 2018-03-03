package protocolsupport.protocol.pipeline.version.util.decoder;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.List;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.handler.codec.DecoderException;
import io.netty.handler.codec.MessageToMessageDecoder;
import protocolsupport.api.Connection;
import protocolsupport.protocol.packet.middle.ServerBoundMiddlePacket;
import protocolsupport.protocol.packet.middleimpl.ServerBoundPacketData;
import protocolsupport.protocol.serializer.MiscSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.storage.netcache.NetworkDataCache;
import protocolsupport.protocol.utils.registry.MiddleTransformerRegistry;
import protocolsupport.utils.netty.Allocator;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.zplatform.ServerPlatform;

public abstract class AbstractPacketDecoder extends MessageToMessageDecoder<ByteBuf> {

	protected final MiddleTransformerRegistry<ServerBoundMiddlePacket> registry = new MiddleTransformerRegistry<>();

	protected final Connection connection;
	public AbstractPacketDecoder(Connection connection, NetworkDataCache cache) {
		this.connection = connection;
		registry.setCallBack(object -> {
			object.setConnection(this.connection);
			object.setSharedStorage(cache);
		});
	}

	protected abstract int readPacketId(ByteBuf buffer);

	protected void decodeAndTransform(Channel channel, ByteBuf buffer, List<Object> to) throws InstantiationException, IllegalAccessException {
		ServerBoundMiddlePacket packetTransformer = registry.getTransformer(connection.getNetworkState(), readPacketId(buffer));
		packetTransformer.readFromClientData(buffer);
		try (RecyclableCollection<ServerBoundPacketData> data = processPackets(channel, packetTransformer.toNative())) {
			for (ServerBoundPacketData packetdata : data) {
				ByteBuf receivedata = Allocator.allocateBuffer();
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
				"Unable to transform or read packet data {1}", Arrays.toString(MiscSerializer.readAllBytes(data))
			), exception);
		} else {
			throw exception;
		}
	}

}
