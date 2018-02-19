package protocolsupport.listeners;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;

import org.bukkit.Chunk;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;

import protocolsupport.api.ProtocolSupportAPI;
import protocolsupport.zplatform.ServerPlatform;

public class PERequestListener implements PluginMessageListener {

	public static final String CHUNK_REQ = "PS|ReqChunk";

	@Override
	public synchronized void onPluginMessageReceived(String channel, Player player, byte[] message) {
		if (channel.equals(CHUNK_REQ)) {
			try {
				DataInputStream in = new DataInputStream(new ByteArrayInputStream(message));
				int x = in.readInt();
				int z = in.readInt();
				Chunk toUpdate = player.getWorld().getChunkAt(x, z);
				in.close();
				ProtocolSupportAPI.getConnection(player).sendPacket(ServerPlatform.get().getPacketFactory().createOutboundUpdateChunkPacket(toUpdate));
			} catch (IOException e) { }
		}
	}

}
