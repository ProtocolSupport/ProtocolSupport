package protocolsupport.protocol.utils.types.networkentity;

public class NetworkEntityDataCacheFactory {

	public static NetworkEntityDataCache create(NetworkEntityType type) {
		switch (type) {
			case LAMA: {
				return new NetworkEntityLamaDataCache();
			}
			default: {
				return new NetworkEntityDataCache();
			}
		}
	}

}
