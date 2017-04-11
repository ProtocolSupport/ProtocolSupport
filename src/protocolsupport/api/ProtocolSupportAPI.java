package protocolsupport.api;

import java.net.SocketAddress;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;

import protocolsupport.api.remapper.BlockRemapperControl;
import protocolsupport.api.remapper.ItemRemapperControl;
import protocolsupport.protocol.storage.ProtocolStorage;

public class ProtocolSupportAPI {

	/**
	 * Returns player protocol version
	 * Returns UNKNOWN if player is not online or is not a real player
	 * @param player player
	 * @return player protocol version or UNKNOWN
	 */
	public static ProtocolVersion getProtocolVersion(Player player) {
		Connection connection = getConnection(player);
		return connection != null ? connection.getVersion() : ProtocolVersion.UNKNOWN;
	}

	/**
	 * Returns protocol version of connection with specified address
	 * Returns UNKNOWN if there is no connection with specified address
	 * @param address address
	 * @return connection protocol version or UNKNOWN
	 */
	public static ProtocolVersion getProtocolVersion(SocketAddress address) {
		Connection connection = getConnection(address);
		return connection != null ? connection.getVersion() : ProtocolVersion.UNKNOWN;
	}

	/**
	 * Returns all currently active connections
	 * @return all currently active connections
	 */
	public static List<Connection> getConnections() {
		return new ArrayList<>(ProtocolStorage.getConnections());
	}

	/**
	 * Returns player {@link Connection}
	 * Returns null if player is not online or is not a real player
	 * @param player player
	 * @return player {@link Connection} or null
	 */
	public static Connection getConnection(Player player) {
		return getConnection(player.spigot().getRawAddress());
	}

	/**
	 * Returns connection with specified address
	 * Returns null if there is no connection with specified address
	 * @param address address
	 * @return {@link Connection} with specified address
	 */
	public static Connection getConnection(SocketAddress address) {
		return ProtocolStorage.getConnection(address);
	}

	/**
	 * @param version protocol version for you want to control remapping
	 * @return item remapper control
	 * @deprecated instance can be constructed directly
	 */
	@Deprecated
	public static ItemRemapperControl getItemRemapper(ProtocolVersion version) {
		return new ItemRemapperControl(version);
	}

	/**
	 * @param version protocol version for you want to control remapping
	 * @return block remapper control
	 * @deprecated instance can be constructed directly
	 */
	@Deprecated
	public static BlockRemapperControl getBlockRemapper(ProtocolVersion version) {
		return new BlockRemapperControl(version);
	}

}
