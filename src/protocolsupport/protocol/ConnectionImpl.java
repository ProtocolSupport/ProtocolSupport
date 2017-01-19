package protocolsupport.protocol;

import io.netty.channel.Channel;
import io.netty.util.AttributeKey;
import protocolsupport.api.Connection;
import protocolsupport.api.ProtocolVersion;

public abstract class ConnectionImpl extends Connection {

	protected static final AttributeKey<ConnectionImpl> key = AttributeKey.valueOf("PSConnectionImpl");

	public static ConnectionImpl getFromChannel(Channel channel) {
		return channel.attr(key).get();
	}

	public void storeInChannel(Channel channel) {
		channel.attr(key).set(this);
	}

	public void setVersion(ProtocolVersion version) {
		this.version = version;
	}

}
