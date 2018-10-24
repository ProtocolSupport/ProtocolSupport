package protocolsupport.zplatform.impl.spigot.injector.network;

import java.util.List;

import net.minecraft.server.v1_13_R2.NetworkManager;

public class SpigotNetworkManagerList {

	protected final List<NetworkManager> list;
	public SpigotNetworkManagerList(List<NetworkManager> list) {
		this.list = list;
	}

	public void add(NetworkManager manager) {
		list.add(manager);
	}

}
