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

	@Override
	public synchronized void onPluginMessageReceived(String channel, Player player, byte[] message) {
		if (channel.equals("PS|ReqChunk")) {
			try {
				DataInputStream in = new DataInputStream(new ByteArrayInputStream(message));
				//System.out.println(in);
				int x = in.readInt();
				int z = in.readInt();
				Chunk toUpdate = player.getWorld().getChunkAt(x, z);
				System.out.println("Chunk requested: " + x + ", " + z);
				in.close();
				ProtocolSupportAPI.getConnection(player).sendPacket(ServerPlatform.get().getPacketFactory().createOutboundUpdateChunkPacket(toUpdate));
			} catch (IOException e) { }
		}
	}

}
