package protocolsupport.zplatform.impl.spigot.network.handler;

import net.minecraft.server.v1_11_R1.IChatBaseComponent;
import net.minecraft.server.v1_11_R1.PacketHandshakingInListener;
import net.minecraft.server.v1_11_R1.PacketHandshakingInSetProtocol;
import protocolsupport.protocol.packet.handler.AbstractLoginListener;
import protocolsupport.zplatform.impl.spigot.SpigotImplUtils;
import protocolsupport.zplatform.network.LegacyHandshakeListener;
import protocolsupport.zplatform.network.NetworkManagerWrapper;

public class SpigotLegacyHandshakeListener extends LegacyHandshakeListener implements PacketHandshakingInListener {

	public SpigotLegacyHandshakeListener(NetworkManagerWrapper networkmanager) {
		super(networkmanager);
	}

	@Override
	public void a(IChatBaseComponent msg) {
	}

	@Override
	public void a(PacketHandshakingInSetProtocol packet) {
		handleSetProtocol(packet.b(), SpigotImplUtils.netStateFromEnumProtocol(packet.a()), packet.hostname, packet.port);
	}

	@Override
	public AbstractLoginListener getLoginListener(NetworkManagerWrapper networkManager, String hostname) {
		return new SpigotLegacyLoginListener(networkManager, hostname);
	}

}
