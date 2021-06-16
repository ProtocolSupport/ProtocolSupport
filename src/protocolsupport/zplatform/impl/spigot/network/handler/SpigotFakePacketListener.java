package protocolsupport.zplatform.impl.spigot.network.handler;

import net.minecraft.network.NetworkManager;
import net.minecraft.network.PacketListener;
import net.minecraft.network.chat.IChatBaseComponent;
import protocolsupport.zplatform.impl.spigot.network.SpigotNetworkManagerWrapper;
import protocolsupport.zplatform.network.NetworkManagerWrapper;

public class SpigotFakePacketListener implements PacketListener {

	protected final NetworkManagerWrapper networkManager;

	public SpigotFakePacketListener(NetworkManagerWrapper networkManager) {
		this.networkManager = networkManager;
	}

	@Override
	public void a(IChatBaseComponent chat) {
	}

	@Override
	public NetworkManager a() {
		return ((SpigotNetworkManagerWrapper) this.networkManager).unwrap();
	}

}
