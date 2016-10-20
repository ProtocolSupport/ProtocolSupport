package protocolsupport.protocol.pipeline.version;

import java.util.List;

import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.ByteToMessageDecoder;
import protocolsupport.api.Connection;
import protocolsupport.protocol.packet.middle.ServerBoundMiddlePacket;
import protocolsupport.protocol.packet.middleimpl.ServerBoundPacketData;
import protocolsupport.protocol.storage.NetworkDataCache;
import protocolsupport.protocol.utils.registry.MiddleTransformerRegistry;
import protocolsupport.utils.netty.Allocator;
import protocolsupport.utils.netty.ChannelUtils;
import protocolsupport.utils.recyclable.RecyclableCollection;

public abstract class AbstractPacketDecoder extends ByteToMessageDecoder {

	protected final MiddleTransformerRegistry<ServerBoundMiddlePacket> registry = new MiddleTransformerRegistry<>();

	protected final Connection connection;
	protected final NetworkDataCache cache;
	public AbstractPacketDecoder(Connection connection, NetworkDataCache cache) {
		this.connection = connection;
		this.cache = cache;
		registry.setCallBack(object -> {
			object.setConnection(AbstractPacketDecoder.this.connection);
			object.setSharedStorage(AbstractPacketDecoder.this.cache);
		});
	}

	protected void addPackets(RecyclableCollection<ServerBoundPacketData> packets, List<Object> to) throws Exception {
		try {
			for (ServerBoundPacketData packetdata : packets) {
				ByteBuf receivedata = Allocator.allocateBuffer();
				ChannelUtils.writeVarInt(receivedata, packetdata.getPacketId());
				receivedata.writeBytes(packetdata);
				to.add(receivedata);
			}
		} finally {
			packets.recycle();
		}
	}

}
