package protocolsupport.zplatform.impl.spigot.network.handler;

import net.minecraft.network.NetworkManager;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.network.protocol.handshake.PacketHandshakingInListener;
import net.minecraft.network.protocol.handshake.PacketHandshakingInSetProtocol;
import protocolsupport.protocol.packet.handler.AbstractHandshakeListener;
import protocolsupport.protocol.packet.handler.AbstractLoginListener;
import protocolsupport.protocol.packet.handler.AbstractLoginListenerBlackhole;
import protocolsupport.protocol.packet.handler.AbstractStatusListener;
import protocolsupport.zplatform.impl.spigot.SpigotMiscUtils;
import protocolsupport.zplatform.impl.spigot.network.SpigotNetworkManagerWrapper;
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
		handleSetProtocol(SpigotMiscUtils.protocolToNetState(packet.b()), packet.c, packet.d);
	}

	@Override
	protected AbstractLoginListenerBlackhole getLoginBlackholeListener(NetworkManagerWrapper networkManager) {
		return new SpigotLoginBlackholeListener(networkManager);
	}

	@Override
	public AbstractLoginListener getLoginListener(NetworkManagerWrapper networkManager) {
		return new SpigotLoginListener(networkManager);
	}

	@Override
	protected AbstractStatusListener getStatusListener(NetworkManagerWrapper networkManager) {
		return new SpigotStatusListener(networkManager);
	}

	@Override
	public NetworkManager a() {
		return ((SpigotNetworkManagerWrapper) this.networkManager).unwrap();
	}

}
