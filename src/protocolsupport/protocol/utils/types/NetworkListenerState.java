package protocolsupport.protocol.utils.types;

import io.netty.channel.Channel;
import net.minecraft.server.v1_10_R1.NetworkManager;

public enum NetworkListenerState {
	HANDSHAKING, PLAY, STATUS, LOGIN;

	public static NetworkListenerState getFromChannel(Channel channel) {
		return NetworkListenerState.values()[channel.attr(NetworkManager.c).get().ordinal()];
	}
}
