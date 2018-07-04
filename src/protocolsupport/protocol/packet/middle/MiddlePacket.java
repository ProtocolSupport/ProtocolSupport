package protocolsupport.protocol.packet.middle;

import protocolsupport.api.Connection;
import protocolsupport.protocol.storage.netcache.NetworkDataCache;
import protocolsupport.utils.Utils;
import protocolsupportbuildprocessor.annotations.NeedsNoArgConstructor;

@NeedsNoArgConstructor
public abstract class MiddlePacket {

	protected Connection connection;

	public void setConnection(Connection connection) {
		this.connection = connection;
	}

	protected NetworkDataCache cache;

	public void setSharedStorage(NetworkDataCache sharedstorage) {
		this.cache = sharedstorage;
	}

	@Override
	public String toString() {
		return Utils.toStringAllFields(this);
	}

}
