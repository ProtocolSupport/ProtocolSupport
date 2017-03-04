package protocolsupport.protocol.packet.middle.clientbound.play;

import gnu.trove.map.TIntObjectMap;
import io.netty.buffer.ByteBuf;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.utils.datawatcher.DataWatcherDeserializer;
import protocolsupport.protocol.utils.datawatcher.DataWatcherObject;

public abstract class MiddleEntityMetadata extends MiddleEntity {

	protected TIntObjectMap<DataWatcherObject<?>> metadata;

	@Override
	public void readFromServerData(ByteBuf serverdata) {
		super.readFromServerData(serverdata);
		metadata = DataWatcherDeserializer.decodeData(serverdata, ProtocolVersion.getLatest());
	}

}
