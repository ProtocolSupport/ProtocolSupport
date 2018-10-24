package protocolsupport.protocol.packet.middle;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.storage.netcache.NetworkDataCache;
import protocolsupport.utils.Utils;

public abstract class MiddlePacket {

	protected final ConnectionImpl connection;
	protected final NetworkDataCache cache; //TODO: remove this field and use ConnectinImpl#getCache where needed
	public MiddlePacket(ConnectionImpl connection) {
		this.connection = connection;
		this.cache = connection.getCache();
	}

	@Override
	public String toString() {
		return Utils.toStringAllFields(this);
	}

}
