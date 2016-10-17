package protocolsupport.protocol.pipeline.version;

import java.util.List;

import protocolsupport.api.unsafe.Connection;
import protocolsupport.protocol.packet.middle.ServerBoundMiddlePacket;
import protocolsupport.protocol.packet.middleimpl.PacketCreator;
import protocolsupport.protocol.pipeline.IPacketDecoder;
import protocolsupport.protocol.storage.NetworkDataCache;
import protocolsupport.protocol.utils.registry.MiddleTransformerRegistry;
import protocolsupport.protocol.utils.registry.MiddleTransformerRegistry.InitCallBack;
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

	protected void addPackets(RecyclableCollection<PacketCreator> packets, List<Object> to) throws Exception {
		try {
			for (PacketCreator creator : packets) {
				to.add(creator.create());
			}
		} finally {
			packets.recycle();
		}
	}

}
