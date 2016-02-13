package protocolsupport.protocol.transformer.middlepacket.clientbound.play;

import java.io.IOException;

import gnu.trove.map.TIntObjectMap;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.PacketDataSerializer;
import protocolsupport.utils.DataWatcherObject;
import protocolsupport.utils.DataWatcherSerializer;
import protocolsupport.utils.netty.ChannelUtils;

public abstract class MiddleEntityMetadata<T> extends MiddleEntity<T> {

	protected TIntObjectMap<DataWatcherObject> metadata;

	@Override
	public void readFromServerData(PacketDataSerializer serializer) throws IOException {
		super.readFromServerData(serializer);
		metadata = DataWatcherSerializer.decodeData(ProtocolVersion.MINECRAFT_1_8, ChannelUtils.toArray(serializer));
	}

}
