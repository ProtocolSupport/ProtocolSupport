package protocolsupport.zplatform.impl.spigot.network.handler;

import net.minecraft.server.v1_13_R2.IChatBaseComponent;
import net.minecraft.server.v1_13_R2.PacketHandshakingInListener;
import net.minecraft.server.v1_13_R2.PacketHandshakingInSetProtocol;
import protocolsupport.protocol.packet.handler.AbstractHandshakeListener;
import protocolsupport.protocol.packet.handler.AbstractLoginListener;
import protocolsupport.protocol.packet.handler.AbstractStatusListener;
import protocolsupport.zplatform.impl.spigot.SpigotMiscUtils;
import protocolsupport.zplatform.network.NetworkManagerWrapper;

public class SpigotHandshakeListener extends AbstractHandshakeListener implements PacketHandshakingInListener {

	public SpigotHandshakeListener(NetworkManagerWrapper networkmanager) {
		super(networkmanager);
	}

	@Override
	public void a(IChatBaseComponent msg) {
	}

	@Override
	public void a(PacketHandshakingInSetProtocol packet) {
		handleSetProtocol(SpigotMiscUtils.protocolToNetState(packet.b()), packet.hostname, packet.port);
	}

	@Override
	public AbstractLoginListener getLoginListener(NetworkManagerWrapper networkManager, String hostname) {
		return new SpigotLoginListener(networkManager, hostname);
	}

	@Override
	protected AbstractStatusListener getStatusListener(NetworkManagerWrapper networkManager) {
		return new SpigotStatusListener(networkManager);
	}

}
