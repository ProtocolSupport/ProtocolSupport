package protocolsupport.protocol.pipeline.version;

import java.util.List;

import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.ByteToMessageDecoder;
import protocolsupport.api.Connection;
import protocolsupport.protocol.packet.middle.ServerBoundMiddlePacket;
import protocolsupport.protocol.packet.middleimpl.ServerBoundPacketData;
import protocolsupport.protocol.storage.NetworkDataCache;
import protocolsupport.protocol.utils.registry.MiddleTransformerRegistry;
import protocolsupport.protocol.utils.registry.MiddleTransformerRegistry.InitCallBack;
import protocolsupport.utils.netty.Allocator;
import protocolsupport.utils.netty.ChannelUtils;
import protocolsupport.utils.recyclable.RecyclableCollection;

public abstract class AbstractPacketDecoder extends ByteToMessageDecoder {

	protected final MiddleTransformerRegistry<ServerBoundMiddlePacket> registry = new MiddleTransformerRegistry<>();

	protected final Connection connection;
	protected final NetworkDataCache sharedstorage;
	public AbstractPacketDecoder(Connection connection, NetworkDataCache sharedstorage) {
		this.connection = connection;
		this.sharedstorage = sharedstorage;
		registry.setCallBack(new InitCallBack<ServerBoundMiddlePacket>() {
			@Override
			public void onInit(ServerBoundMiddlePacket object) {
				object.setConnection(AbstractPacketDecoder.this.connection);
				object.setSharedStorage(AbstractPacketDecoder.this.sharedstorage);
			}
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
