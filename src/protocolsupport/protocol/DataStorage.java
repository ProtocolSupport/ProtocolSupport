package protocolsupport.protocol;

import java.net.SocketAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.UUID;
import java.util.WeakHashMap;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.mojang.authlib.properties.Property;

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
		String name = channelInfo.get(address).tablist.get(uuid);
		if (name == null) {
			Player player = Bukkit.getPlayer(uuid);
			if (player != null) {
				return player.getName();
			}
		}
		return name;
	}

	public static void addPropertyData(SocketAddress address, UUID uuid, Property property) {
		ArrayList<Property> properties = channelInfo.get(address).skins.get(uuid);
		Iterator<Property> iterator = properties.iterator();
		while (iterator.hasNext()) {
			if (iterator.next().getName().equals(property.getName())) {
				iterator.remove();
			}
		}
		properties.add(property);
	}

	public static ArrayList<Property> getPropertyData(SocketAddress address, UUID uuid, boolean filterNonSigned) {
		ArrayList<Property> properties = channelInfo.get(address).skins.get(uuid);
		if (!filterNonSigned) {
			return properties;
		} else {
			Iterator<Property> iterator = properties.iterator();
			while (iterator.hasNext()) {
				if (!iterator.next().hasSignature()) {
					iterator.remove();
				}
			}
			return properties;
		}
	}

	private static class ChannelInfo {
		ProtocolVersion version = ProtocolVersion.UNKNOWN;
		Player player;
		HashMap<UUID, String> tablist = new HashMap<UUID, String>();
		@SuppressWarnings("serial")
		HashMap<UUID, ArrayList<Property>> skins = new HashMap<UUID, ArrayList<Property>>() {
			@Override
			public ArrayList<Property> get(Object uuid) {
				if (!super.containsKey(uuid)) {
					super.put((UUID) uuid, new ArrayList<Property>());
				}
				return super.get(uuid);
			}
		};
	}

	public static enum ProtocolVersion {
		MINECRAFT_1_8(47),
		MINECRAFT_1_7_10(5),
		MINECRAFT_1_7_5(4),
		MINECRAFT_1_6_4(78),
		MINECRAFT_1_6_2(74),
		MINECRAFT_1_5_2(61),
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
				case 61: {
					return MINECRAFT_1_5_2;
				}
			}
			return UNKNOWN;
		}
	}

}
