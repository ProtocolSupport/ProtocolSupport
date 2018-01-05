package protocolsupport.protocol.pipeline.version;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.DecoderException;
import io.netty.handler.codec.MessageToMessageDecoder;
import protocolsupport.api.Connection;
import protocolsupport.protocol.packet.middle.ServerBoundMiddlePacket;
import protocolsupport.protocol.packet.middleimpl.ServerBoundPacketData;
import protocolsupport.protocol.serializer.MiscSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.storage.NetworkDataCache;
import protocolsupport.protocol.utils.registry.MiddleTransformerRegistry;
import protocolsupport.utils.netty.Allocator;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.zplatform.ServerPlatform;

public abstract class AbstractPacketDecoder extends MessageToMessageDecoder<ByteBuf> {

	protected final MiddleTransformerRegistry<ServerBoundMiddlePacket> registry = new MiddleTransformerRegistry<>();

	protected final Connection connection;
	protected final NetworkDataCache cache;
	public AbstractPacketDecoder(Connection connection, NetworkDataCache cache) {
		this.connection = connection;
		this.cache = cache;
		registry.setCallBack(object -> {
			object.setConnection(this.connection);
			object.setSharedStorage(this.cache);
		});
	}

	protected void addPackets(RecyclableCollection<ServerBoundPacketData> packets, List<Object> to)  {
		try {
			for (ServerBoundPacketData packetdata : packets) {
				ByteBuf receivedata = Allocator.allocateBuffer();
				VarNumberSerializer.writeVarInt(receivedata, packetdata.getPacketId());
				receivedata.writeBytes(packetdata);
				to.add(receivedata);
			}
		} finally {
			packets.recycle();
		}
	}

	protected void throwFailedTransformException(Exception exception, ServerBoundMiddlePacket packet, ByteBuf data) throws Exception {
		if (ServerPlatform.get().getMiscUtils().isDebugging()) {
			data.readerIndex(0);
			throw new DecoderException(MessageFormat.format(
				"Unable to transform or read middle packet {0} (data: {1})",
				Objects.toString(packet), Arrays.toString(MiscSerializer.readAllBytes(data))
			), exception);
		} else {
			throw exception;
		}
	}

}
