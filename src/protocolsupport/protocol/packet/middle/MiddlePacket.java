package protocolsupport.protocol.packet.middle;

import protocolsupport.api.Connection;
import protocolsupport.protocol.storage.NetworkDataCache;
import protocolsupportbuildprocessor.annotations.NeedsNoArgConstructor;

@NeedsNoArgConstructor
public abstract class MiddlePacket {

	protected Connection connection;

	public final void setConnection(Connection connection) {
		this.connection = connection;
	}

	protected NetworkDataCache cache;

	public void setSharedStorage(NetworkDataCache sharedstorage) {
		this.cache = sharedstorage;
	}

}
