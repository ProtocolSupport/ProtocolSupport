package protocolsupport.api;

import java.net.SocketAddress;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;

import protocolsupport.api.remapper.BlockRemapperControl;
import protocolsupport.api.remapper.ItemRemapperControl;
import protocolsupport.api.unsafe.Connection;
import protocolsupport.protocol.storage.ProtocolStorage;

public class ProtocolSupportAPI {

	public static ProtocolVersion getProtocolVersion(Player player) {
		return getProtocolVersion(player.getAddress());
	}

	public static ProtocolVersion getProtocolVersion(SocketAddress address) {
		return getConnection(address).getVersion();
	}

	public static List<Connection> getConnections() {
		return new ArrayList<Connection>(ProtocolStorage.getConnections());
	}

	public static Connection getConnection(Player player) {
		return ProtocolStorage.getConnection(player.getAddress());
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
