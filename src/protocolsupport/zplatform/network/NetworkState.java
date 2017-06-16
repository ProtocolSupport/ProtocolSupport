package protocolsupport.zplatform.network;

import java.text.MessageFormat;

public enum NetworkState {
	HANDSHAKING, PLAY, STATUS, LOGIN;

	public static NetworkState byNetworkId(int id) {
		switch (id) {
			case 0: {
				return HANDSHAKING;
			}
			case 1: {
				return STATUS;
			}
			case 2: {
				return LOGIN;
			}
			case 3: {
				return PLAY;
			}
			default: {
				throw new IllegalArgumentException(MessageFormat.format("Unknown network state id {0}", id));
			}
		}
	}

}
