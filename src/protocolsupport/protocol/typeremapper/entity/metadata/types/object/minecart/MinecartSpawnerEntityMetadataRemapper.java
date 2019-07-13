package protocolsupport.protocol.typeremapper.entity.metadata.types.object.minecart;

import protocolsupport.protocol.typeremapper.entity.metadata.NetworkEntityMetadataObjectRemapper;
import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObject;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;
import protocolsupport.protocol.types.networkentity.NetworkEntity;
import protocolsupport.utils.CollectionsUtils.ArrayMap;

public class MinecartSpawnerEntityMetadataRemapper extends MinecartEntityMetadataRemapper {

	public static final MinecartSpawnerEntityMetadataRemapper INSTANCE = new MinecartSpawnerEntityMetadataRemapper();

	public MinecartSpawnerEntityMetadataRemapper() {
		addRemap(new NetworkEntityMetadataObjectRemapper() {
			@Override
			public void remap(NetworkEntity entity, ArrayMap<NetworkEntityMetadataObject<?>> original, ArrayMap<NetworkEntityMetadataObject<?>> remapped) {
				//Simulate spawnerMinecart in Pocket. TODO: Fix with ids.
				//remapped.put(PeMetaBase.MINECART_BLOCK, new DataWatcherObjectSVarInt(52));
				//remapped.put(PeMetaBase.MINECART_OFFSET, new DataWatcherObjectSVarInt(6));
				//remapped.put(PeMetaBase.MINECART_DISPLAY, new DataWatcherObjectByte((byte) 1));
			}
		}, ProtocolVersionsHelper.ALL_PE);
	}
	
}
