package protocolsupport.protocol.transformer.mcpe.handler;

import net.minecraft.server.v1_9_R1.EntityPlayer;
import net.minecraft.server.v1_9_R1.IChatBaseComponent;
import net.minecraft.server.v1_9_R1.MinecraftServer;
import net.minecraft.server.v1_9_R1.NetworkManager;
import net.minecraft.server.v1_9_R1.PlayerConnection;

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
