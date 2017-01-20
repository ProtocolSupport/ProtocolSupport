package protocolsupport.zplatform.network;

import org.apache.commons.lang3.NotImplementedException;

import io.netty.channel.Channel;
import protocolsupport.zplatform.ServerImplementationType;
import protocolsupport.zplatform.impl.spigot.SpigotPlatformUtils;

public enum NetworkListenerState {
	HANDSHAKING, PLAY, STATUS, LOGIN;

	public static NetworkListenerState getFromChannel(Channel channel) {
		switch (ServerImplementationType.get()) {
			case SPIGOT: {
				return SpigotPlatformUtils.getNetStateFromChannel(channel);
			}
			default: {
				// TODO: implement for glowstone
				throw new NotImplementedException("Not implemented yet");
			}
		}
	}

}
