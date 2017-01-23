package protocolsupport.protocol.packet.middle.clientbound.play;

import gnu.trove.map.TIntObjectMap;
import protocolsupport.protocol.serializer.ProtocolSupportPacketDataSerializer;
import protocolsupport.protocol.utils.datawatcher.DataWatcherDeserializer;
import protocolsupport.protocol.utils.datawatcher.DataWatcherObject;

public abstract class MiddleEntityMetadata<T> extends MiddleEntity<T> {

	protected TIntObjectMap<DataWatcherObject<?>> metadata;

	@Override
	public void readFromServerData(ProtocolSupportPacketDataSerializer serializer) {
		super.readFromServerData(serializer);
		metadata = DataWatcherDeserializer.decodeData(serializer);
	}

}
