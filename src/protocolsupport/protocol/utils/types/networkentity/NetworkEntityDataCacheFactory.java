package protocolsupport.protocol.utils.types.networkentity;

public class NetworkEntityDataCacheFactory {

	public static NetworkEntityDataCache create(NetworkEntityType type) {
		switch (type) {
			case LAMA: {
				return new NetworkEntityLamaDataCache();
			}
			case ITEM: {
				return new NetworkEntityItemDataCache();
			}
			default: {
				return new NetworkEntityDataCache();
			}
		}
	}

}
