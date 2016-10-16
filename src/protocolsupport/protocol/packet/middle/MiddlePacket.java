package protocolsupport.protocol.packet.middle;

import protocolsupport.api.unsafe.Connection;
import protocolsupportbuildprocessor.annotations.NeedsNoArgConstructor;

@NeedsNoArgConstructor
public abstract class MiddlePacket {

	protected Connection connection;

	public final void setConnection(Connection connection) {
		this.connection = connection;
	}

}
