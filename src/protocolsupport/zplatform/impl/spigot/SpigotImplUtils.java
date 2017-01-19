package protocolsupport.zplatform.impl.spigot;

import net.minecraft.server.v1_11_R1.EnumProtocol;
import protocolsupport.zplatform.network.NetworkListenerState;

public class SpigotImplUtils {

	public static NetworkListenerState netStateFromEnumProtocol(EnumProtocol state) {
		switch (state) {
			case HANDSHAKING: {
				return NetworkListenerState.HANDSHAKING;
			}
			case PLAY: {
				return NetworkListenerState.PLAY;
			}
			case LOGIN: {
				return NetworkListenerState.LOGIN;
			}
			case STATUS: {
				return NetworkListenerState.STATUS;
			}
			default: {
				throw new IllegalArgumentException("Unknown state " + state);
			}
		}
	}

}
