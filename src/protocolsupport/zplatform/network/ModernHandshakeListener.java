package protocolsupport.zplatform.network;

import org.apache.commons.lang3.NotImplementedException;

import protocolsupport.protocol.packet.handler.AbstractHandshakeListener;
import protocolsupport.zplatform.ServerPlatform;
import protocolsupport.zplatform.impl.spigot.network.handler.SpigotModernHandshakeListener;

public abstract class ModernHandshakeListener extends AbstractHandshakeListener {

	public static ModernHandshakeListener create(NetworkManagerWrapper networkmanager, boolean hasCompression) {
		switch (ServerPlatform.get()) {
			case SPIGOT: {
				return new SpigotModernHandshakeListener(networkmanager, hasCompression);
			}
			default: {
				// TODO: implement for glowstone
				throw new NotImplementedException("Not implemented yet");
			}
		}
	}

	protected final boolean hasCompression;
	public ModernHandshakeListener(NetworkManagerWrapper networkmanager, boolean hasCompression) {
		super(networkmanager);
		this.hasCompression = hasCompression;
	}

}
