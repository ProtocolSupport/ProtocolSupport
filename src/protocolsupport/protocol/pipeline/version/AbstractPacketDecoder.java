package protocolsupport.protocol.pipeline.version;

import java.util.List;

import net.minecraft.server.v1_10_R1.EnumProtocol;
import net.minecraft.server.v1_10_R1.EnumProtocolDirection;
import net.minecraft.server.v1_10_R1.Packet;
import net.minecraft.server.v1_10_R1.PacketDataSerializer;
import protocolsupport.api.Connection;
import protocolsupport.protocol.packet.middle.ServerBoundMiddlePacket;
import protocolsupport.protocol.packet.middleimpl.ServerBoundPacketData;
import protocolsupport.protocol.pipeline.IPacketDecoder;
import protocolsupport.protocol.storage.NetworkDataCache;
import protocolsupport.protocol.utils.registry.MiddleTransformerRegistry;
import protocolsupport.protocol.utils.registry.MiddleTransformerRegistry.InitCallBack;
import protocolsupport.utils.netty.WrappingBuffer;
import protocolsupport.utils.recyclable.RecyclableCollection;

public abstract class AbstractPacketDecoder implements IPacketDecoder {

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

	private final WrappingBuffer wrapper = new WrappingBuffer();
	private final PacketDataSerializer nativeSerializer = new PacketDataSerializer(wrapper);

	protected void addPackets(EnumProtocol protocol, RecyclableCollection<ServerBoundPacketData> packets, List<Object> to) throws Exception {
		try {
			for (ServerBoundPacketData creator : packets) {
				Packet<?> packet = protocol.a(EnumProtocolDirection.SERVERBOUND, creator.getPacketId());
				wrapper.setBuf(creator);
				packet.a(nativeSerializer);
				to.add(packet);
			}
		} finally {
			packets.recycle();
		}
	}

}
