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

	public boolean handlePacketSend(Object packet) {
		boolean canSend = true;
		for (PacketSendListener listener : sendListeners) {
			try {
				if (!listener.onPacketSending(packet)) {
					canSend = false;
				}
			} catch (Throwable t) {
				System.err.println("Error occured while handling packet sending");
				t.printStackTrace();
			}
		}
		return canSend;
	}

	public boolean handlePacketReceive(Object packet) {
		boolean canReceive = true;
		for (PacketReceiveListener listener : receiveListeners) {
			try {
				if (!listener.onPacketReceiving(packet)) {
					canReceive = false;
				}
			} catch (Throwable t) {
				System.err.println("Error occured while handling packet receiving");
				t.printStackTrace();
			}
		}
		return canReceive;
	}

}
