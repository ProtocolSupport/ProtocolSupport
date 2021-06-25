package protocolsupport.protocol.packet.handler;

import protocolsupport.api.chat.components.BaseComponent;
import protocolsupport.zplatform.network.NetworkManagerWrapper;

public class AbstractLoginListenerBlackhole implements IPacketListener {

	protected final NetworkManagerWrapper networkManager;

	protected AbstractLoginListenerBlackhole(NetworkManagerWrapper networkmanager) {
		this.networkManager = networkmanager;
	}

	@Override
	public void destroy() {
	}

	@Override
	public void disconnect(BaseComponent message) {
		networkManager.close(message);
	}


}
