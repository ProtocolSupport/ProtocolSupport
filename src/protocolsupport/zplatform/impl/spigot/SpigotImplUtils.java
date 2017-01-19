package protocolsupport.zplatform.impl.spigot;

import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_11_R1.CraftServer;

import net.minecraft.server.v1_11_R1.EnumProtocol;
import net.minecraft.server.v1_11_R1.MinecraftServer;
import protocolsupport.zplatform.network.NetworkListenerState;

public class SpigotImplUtils {

	public static NetworkListenerState netStateFromEnumProtocol(EnumProtocol state) {
		switch (state) {
			case HANDSHAKING: {
				return NetworkListenerState.HANDSHAKING;
			}
			case PLAY: {
				return NetworkListenerState.PLAY;
			}
			case LOGIN: {
				return NetworkListenerState.LOGIN;
			}
			case STATUS: {
				return NetworkListenerState.STATUS;
			}
			default: {
				throw new IllegalArgumentException("Unknown state " + state);
			}
		}
	}

	public static MinecraftServer getServer() {
		return ((CraftServer) Bukkit.getServer()).getServer();
	}

}
