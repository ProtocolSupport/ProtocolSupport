package protocolsupport.protocol.transformer.utils.chunk;

import org.bukkit.craftbukkit.v1_9_R1.CraftWorld;

import net.minecraft.server.v1_9_R1.WorldServer;

public class ChunkUtils {

	public static boolean hasSkyLight(org.bukkit.World world) {
		WorldServer server = ((CraftWorld) world).getHandle();
		return !server.worldProvider.m();
	}

}
