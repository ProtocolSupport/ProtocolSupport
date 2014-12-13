package protocolsupport.protocol;

import java.net.SocketAddress;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class DataStorage {

	@SuppressWarnings("serial")
	private final static HashMap<SocketAddress, ChannelInfo> channelInfo = new HashMap<SocketAddress, ChannelInfo>() {
		@Override
		public ChannelInfo get(Object address) {
			if (!super.containsKey(address)) {
				super.put((SocketAddress) address, new ChannelInfo());
			}
			return super.get(address);
		}
	};

	public static void clearData(SocketAddress address) {
		channelInfo.remove(address);
	}

	public static final int CLIENT_1_8_PROTOCOL_VERSION = 47;

	public static void setVersion(SocketAddress address, int version) {
		channelInfo.get(address).version = version;
	}

	public static int getVersion(SocketAddress address) {
		return channelInfo.get(address).version;
	}

	public static void setPlayer(SocketAddress address, Player player) {
		channelInfo.get(address).player = player;
	}

	@SuppressWarnings("deprecation")
	public static Player getPlayer(SocketAddress address) {
		Player player = channelInfo.get(address).player;
		if (player == null) {
			for (Player oplayer : Bukkit.getOnlinePlayers()) {
				if (oplayer.getAddress().equals(address)) {
					return oplayer;
				}
			}
		}
		return player;
	}

	public static void addTabName(SocketAddress address, UUID uuid, String name) {
		channelInfo.get(address).tablist.put(uuid, name);
	}

	public static String getTabName(SocketAddress address, UUID uuid) {
		return channelInfo.get(address).tablist.get(uuid);
	}

	private static class ChannelInfo {
		int version = 47;
		Player player;
		HashMap<UUID, String> tablist = new HashMap<UUID, String>();
	}

}
