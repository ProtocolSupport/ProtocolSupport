package protocolsupport.zplatform.impl.spigot.network.handler;

import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.network.protocol.status.PacketStatusInListener;
import net.minecraft.network.protocol.status.PacketStatusInPing;
import net.minecraft.network.protocol.status.PacketStatusInStart;
import protocolsupport.protocol.packet.handler.AbstractStatusListener;
import protocolsupport.zplatform.network.NetworkManagerWrapper;

public class SpigotStatusListener extends AbstractStatusListener implements PacketStatusInListener {

	public SpigotStatusListener(NetworkManagerWrapper networkmanager) {
		super(networkmanager);
	}

	@Override
	public void a(IChatBaseComponent msg) {
	}

	@Override
	public void a(PacketStatusInStart packet) {
		handleStatusRequest();
	}

	@Override
	public void a(PacketStatusInPing packet) {
		handlePing(packet.a());
	}

	@Override
	public boolean a() {
		return networkManager.isConnected();
	}

}
