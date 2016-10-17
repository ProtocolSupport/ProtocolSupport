package protocolsupport.utils;

import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_10_R1.CraftServer;

import net.minecraft.server.v1_10_R1.MinecraftServer;
import net.minecraft.server.v1_10_R1.PacketPlayInCloseWindow;

public class ServerPlatformUtils {

	public static Object createInboundInventoryClosePacket() {
		return new PacketPlayInCloseWindow();
	}

	public static MinecraftServer getServer() {
		return ((CraftServer) Bukkit.getServer()).getServer();
	}

}
