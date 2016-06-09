package protocolsupport.injector.network;

import java.util.List;

import net.minecraft.server.v1_10_R1.NetworkManager;

public class NetworkManagerList {

	protected final List<NetworkManager> list;
	public NetworkManagerList(List<NetworkManager> list) {
		this.list = list;
	}

	public void add(NetworkManager manager) {
		list.add(manager);
	}

}
