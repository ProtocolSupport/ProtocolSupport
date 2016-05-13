package protocolsupport.protocol.packet.mcpe.handler;

import net.minecraft.server.v1_9_R2.EntityPlayer;
import net.minecraft.server.v1_9_R2.IChatBaseComponent;
import net.minecraft.server.v1_9_R2.MinecraftServer;
import net.minecraft.server.v1_9_R2.NetworkManager;
import net.minecraft.server.v1_9_R2.PlayerConnection;

public class PEPlayerConnection extends PlayerConnection {

	public PEPlayerConnection(MinecraftServer minecraftserver, NetworkManager networkmanager, EntityPlayer entityplayer) {
		super(minecraftserver, networkmanager, entityplayer);
	}

	@Override
	public void a(IChatBaseComponent comp) {
		super.a(comp);
		networkManager.close(comp);
	}

}
