package protocolsupport.zplatform.impl.spigot.network.handler;

import net.minecraft.server.v1_12_R1.IChatBaseComponent;
import net.minecraft.server.v1_12_R1.PacketHandshakingInListener;
import net.minecraft.server.v1_12_R1.PacketHandshakingInSetProtocol;
import protocolsupport.protocol.packet.handler.AbstractHandshakeListener;
import protocolsupport.protocol.packet.handler.AbstractLoginListener;
import protocolsupport.protocol.packet.handler.AbstractStatusListener;
import protocolsupport.zplatform.impl.spigot.SpigotMiscUtils;
import protocolsupport.zplatform.network.NetworkManagerWrapper;

public class SpigotHandshakeListener extends AbstractHandshakeListener implements PacketHandshakingInListener {

	private final boolean hasCompression;
	private final boolean fullEncryption;
	public SpigotHandshakeListener(NetworkManagerWrapper networkmanager, boolean hasCompression, boolean fullEncryption) {
		super(networkmanager);
		this.hasCompression = hasCompression;
		this.fullEncryption = fullEncryption;
	}

	@Override
	public void a(IChatBaseComponent msg) {
	}

	@Override
	public void a(PacketHandshakingInSetProtocol packet) {
		handleSetProtocol(SpigotMiscUtils.netStateFromEnumProtocol(packet.a()), packet.hostname, packet.port);
	}

	@Override
	public AbstractLoginListener getLoginListener(NetworkManagerWrapper networkManager, String hostname) {
		return new SpigotLoginListener(networkManager, hostname, hasCompression, fullEncryption);
	}

	@Override
	protected AbstractStatusListener getStatusListener(NetworkManagerWrapper networkManager) {
		return new SpigotStatusListener(networkManager);
	}

}
