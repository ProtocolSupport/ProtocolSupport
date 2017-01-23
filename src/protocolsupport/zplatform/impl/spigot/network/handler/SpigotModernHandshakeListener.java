package protocolsupport.zplatform.impl.spigot.network.handler;

import net.minecraft.server.v1_11_R1.IChatBaseComponent;
import net.minecraft.server.v1_11_R1.PacketHandshakingInListener;
import net.minecraft.server.v1_11_R1.PacketHandshakingInSetProtocol;
import protocolsupport.protocol.packet.handler.AbstractHandshakeListener;
import protocolsupport.protocol.packet.handler.AbstractLoginListener;
import protocolsupport.protocol.packet.handler.AbstractStatusListener;
import protocolsupport.zplatform.impl.spigot.SpigotMiscUtils;
import protocolsupport.zplatform.network.NetworkManagerWrapper;

public class SpigotModernHandshakeListener extends AbstractHandshakeListener implements PacketHandshakingInListener {

	private final boolean hasCompression;
	public SpigotModernHandshakeListener(NetworkManagerWrapper networkmanager, boolean hasCompression) {
		super(networkmanager);
		this.hasCompression = hasCompression;
	}

	@Override
	public void a(IChatBaseComponent msg) {
	}

	@Override
	public void a(PacketHandshakingInSetProtocol packet) {
		handleSetProtocol(packet.b(), SpigotMiscUtils.netStateFromEnumProtocol(packet.a()), packet.hostname, packet.port);
	}

	@Override
	public AbstractLoginListener getLoginListener(NetworkManagerWrapper networkManager, String hostname) {
		return new SpigotModernLoginListener(networkManager, hostname, hasCompression);
	}

	@Override
	protected AbstractStatusListener getStatusListener(NetworkManagerWrapper networkManager) {
		return new SpigotStatusListener(networkManager);
	}

}
