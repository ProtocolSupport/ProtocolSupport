package protocolsupport.protocol.packet.middlepacket.clientbound.play;

import java.io.IOException;

import gnu.trove.map.TIntObjectMap;

import protocolsupport.protocol.PacketDataSerializer;
import protocolsupport.protocol.utils.datawatcher.DataWatcherDeserializer;
import protocolsupport.protocol.utils.datawatcher.DataWatcherObject;
import protocolsupport.utils.netty.ChannelUtils;

public abstract class MiddleEntityMetadata<T> extends MiddleEntity<T> {

	protected TIntObjectMap<DataWatcherObject<?>> metadata;

	@Override
	public void readFromServerData(PacketDataSerializer serializer) throws IOException {
		super.readFromServerData(serializer);
		metadata = DataWatcherDeserializer.decodeData(ChannelUtils.toArray(serializer));
	}

}
