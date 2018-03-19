package protocolsupport.protocol.utils.types.networkentity;

public class NetworkEntityDataCacheFactory {

	public static NetworkEntityDataCache create(NetworkEntityType type) {
		switch (type) {
			default: {
				return new NetworkEntityDataCache();
			}
		}
	}

}
