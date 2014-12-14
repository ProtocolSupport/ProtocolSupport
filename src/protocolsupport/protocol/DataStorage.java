package protocolsupport.protocol;

import java.net.SocketAddress;
import java.util.HashMap;
import java.util.UUID;
import java.util.WeakHashMap;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class DataStorage {

	private final static WeakHashMap<SocketAddress, ChannelInfo> channelInfo = new WeakHashMap<SocketAddress, ChannelInfo>() {
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

	public static void setVersion(SocketAddress address, ProtocolVersion version) {
		channelInfo.get(address).version = version;
	}

	public static ProtocolVersion getVersion(SocketAddress address) {
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
		ProtocolVersion version = ProtocolVersion.UNKNOWN;
		Player player;
		HashMap<UUID, String> tablist = new HashMap<UUID, String>();
	}

	public static enum ProtocolVersion {
		MINECRAFT_1_8(47),
		MINECRAFT_1_7_10(5),
		MINECRAFT_1_7_5(4),
		MINECRAFT_1_6_4(78),
		MINECRAFT_1_6_2(74),
		UNKNOWN(-1);

		private int id;

		ProtocolVersion(int id) {
			this.id = id;
		}

		public int getId() {
			return id;
		}

		public static ProtocolVersion fromId(int id) {
			switch (id) {
				case 47: {
					return MINECRAFT_1_8;
				}
				case 5: {
					return MINECRAFT_1_7_10;
				}
				case 4: {
					return MINECRAFT_1_7_5;
				}
				case 78: {
					return MINECRAFT_1_6_4;
				}
				case 74: {
					return MINECRAFT_1_6_2;
				}
			}
			return UNKNOWN;
		}
	}

}
