package protocolsupport.protocol.pipeline.version.util.decoder;

import io.netty.channel.Channel;
import protocolsupport.api.Connection;
import protocolsupport.protocol.packet.middleimpl.ServerBoundPacketData;
import protocolsupport.protocol.storage.netcache.NetworkDataCache;
import protocolsupport.protocol.typeremapper.legacy.LegacyAnimatePacketReorderer;
import protocolsupport.utils.recyclable.RecyclableCollection;

public class AbstractModernWithReorderPacketDecoder extends AbstractModernPacketDecoder {

	public AbstractModernWithReorderPacketDecoder(Connection connection, NetworkDataCache sharedstorage) {
		super(connection, sharedstorage);
	}

	protected final LegacyAnimatePacketReorderer animateReorderer = new LegacyAnimatePacketReorderer();

	@Override
	protected RecyclableCollection<ServerBoundPacketData> processPackets(Channel channel, RecyclableCollection<ServerBoundPacketData> data) {
		return animateReorderer.orderPackets(data);
	}

}
