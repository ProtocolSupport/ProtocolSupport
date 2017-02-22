package protocolsupport.api;

import java.net.SocketAddress;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;

import protocolsupport.api.remapper.BlockRemapperControl;
import protocolsupport.api.remapper.ItemRemapperControl;
import protocolsupport.protocol.storage.ProtocolStorage;

public class ProtocolSupportAPI {

	public static ProtocolVersion getProtocolVersion(Player player) {
		Connection connection = getConnection(player);
		return connection != null ? connection.getVersion() : ProtocolVersion.UNKNOWN;
	}

	public static ProtocolVersion getProtocolVersion(SocketAddress address) {
		Connection connection = getConnection(address);
		return connection != null ? connection.getVersion() : ProtocolVersion.UNKNOWN;
	}

	public static List<Connection> getConnections() {
		return new ArrayList<>(ProtocolStorage.getConnections());
	}

	public static Connection getConnection(Player player) {
		return getConnection(player.spigot().getRawAddress());
	}

	public static Connection getConnection(SocketAddress address) {
		return ProtocolStorage.getConnection(address);
	}

	public static ItemRemapperControl getItemRemapper(ProtocolVersion version) {
		return new ItemRemapperControl(version);
	}

	public static BlockRemapperControl getBlockRemapper(ProtocolVersion version) {
		return new BlockRemapperControl(version);
	}

}
