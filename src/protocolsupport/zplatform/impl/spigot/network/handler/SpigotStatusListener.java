package protocolsupport.zplatform.impl.spigot.network.handler;

import net.minecraft.server.v1_14_R1.IChatBaseComponent;
import net.minecraft.server.v1_14_R1.PacketStatusInListener;
import net.minecraft.server.v1_14_R1.PacketStatusInPing;
import net.minecraft.server.v1_14_R1.PacketStatusInStart;
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
		handlePing(packet.b());
	}

}
