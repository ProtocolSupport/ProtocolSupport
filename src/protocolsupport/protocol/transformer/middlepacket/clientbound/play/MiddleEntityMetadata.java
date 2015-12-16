package protocolsupport.protocol.transformer.middlepacket.clientbound.play;

import java.io.IOException;

import gnu.trove.map.TIntObjectMap;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.PacketDataSerializer;
import protocolsupport.protocol.storage.LocalStorage;
import protocolsupport.protocol.typeremapper.watchedentity.types.WatchedEntity;
import protocolsupport.utils.DataWatcherObject;
import protocolsupport.utils.DataWatcherSerializer;
import protocolsupport.utils.Utils;

public abstract class MiddleEntityMetadata<T> extends MiddleEntity<T> {

	protected WatchedEntity wentity;
	protected TIntObjectMap<DataWatcherObject> metadata;

	@Override
	public void readFromServerData(PacketDataSerializer serializer) throws IOException {
		super.readFromServerData(serializer);
		metadata = DataWatcherSerializer.decodeData(ProtocolVersion.MINECRAFT_1_8, Utils.toArray(serializer));
	}

	@Override
	public void handle(LocalStorage storage) {
		wentity = storage.getWatchedEntity(entityId);
	}

}
