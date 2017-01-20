package protocolsupport.zplatform.network;

import org.apache.commons.lang3.NotImplementedException;

import protocolsupport.protocol.packet.handler.AbstractHandshakeListener;
import protocolsupport.zplatform.ServerPlatform;
import protocolsupport.zplatform.impl.spigot.network.handler.SpigotLegacyHandshakeListener;

public abstract class LegacyHandshakeListener extends AbstractHandshakeListener {

	public static LegacyHandshakeListener create(NetworkManagerWrapper networkmanager) {
		switch (ServerPlatform.get()) {
			case SPIGOT: {
				return new SpigotLegacyHandshakeListener(networkmanager);
			}
			default: {
				// TODO: implement for glowstone
				throw new NotImplementedException("Not implemented yet");
			}
		}
	}

	protected LegacyHandshakeListener(NetworkManagerWrapper networkmanager) {
		super(networkmanager);
	}

}
