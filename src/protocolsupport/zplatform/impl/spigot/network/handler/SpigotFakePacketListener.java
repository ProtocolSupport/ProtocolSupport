package protocolsupport.zplatform.impl.spigot.network.handler;

import net.minecraft.server.v1_16_R1.IChatBaseComponent;
import net.minecraft.server.v1_16_R1.NetworkManager;
import net.minecraft.server.v1_16_R1.PacketListener;
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
