package protocolsupport.zplatform.impl.spigot.network.handler;

import net.minecraft.network.NetworkManager;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.network.protocol.login.PacketLoginInCustomPayload;
import net.minecraft.network.protocol.login.PacketLoginInEncryptionBegin;
import net.minecraft.network.protocol.login.PacketLoginInListener;
import net.minecraft.network.protocol.login.PacketLoginInStart;
import protocolsupport.protocol.packet.handler.AbstractLoginListenerBlackhole;
import protocolsupport.zplatform.impl.spigot.network.SpigotNetworkManagerWrapper;
import protocolsupport.zplatform.network.NetworkManagerWrapper;

public class SpigotLoginBlackholeListener extends AbstractLoginListenerBlackhole implements PacketLoginInListener {

	public SpigotLoginBlackholeListener(NetworkManagerWrapper networkmanager) {
		super(networkmanager);
	}

	@Override
	public NetworkManager a() {
		return ((SpigotNetworkManagerWrapper) this.networkManager).unwrap();
	}

	@Override
	public void a(IChatBaseComponent message) {
	}

	@Override
	public void a(PacketLoginInStart packetlogininstart) {
	}

	@Override
	public void a(PacketLoginInEncryptionBegin packetlogininencryptionbegin) {
	}

	@Override
	public void a(PacketLoginInCustomPayload packetloginincustompayload) {
	}

}
