package protocolsupport.api.unsafe.pemetadata;

import protocolsupport.protocol.utils.types.NetworkEntity;

public class DefaultPEMetaProvider extends PEMetaProvider {

	@Override
	public float getEntitySize(NetworkEntity entity) {
		return 1f;
	}

}
