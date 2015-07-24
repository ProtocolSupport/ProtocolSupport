package protocolsupport.protocol.transformer.mcpe.handler;

import net.minecraft.server.v1_8_R3.EntityPlayer;
import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.MinecraftServer;
import net.minecraft.server.v1_8_R3.NetworkManager;
import net.minecraft.server.v1_8_R3.PlayerConnection;

public class KickRoutePlayerConnection extends PlayerConnection {

	public KickRoutePlayerConnection(MinecraftServer minecraftserver, NetworkManager networkmanager, EntityPlayer entityplayer) {
		super(minecraftserver, networkmanager, entityplayer);
	}

	@Override
	public void a(IChatBaseComponent comp) {
		super.a(comp);
		networkManager.close(comp);
	}

}
