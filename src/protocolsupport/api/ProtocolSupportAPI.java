package protocolsupport.api;

import java.net.SocketAddress;

import org.bukkit.entity.Player;

import protocolsupport.protocol.storage.ProtocolStorage;

public class ProtocolSupportAPI {

	public static ProtocolVersion getProtocolVersion(Player player) {
		return ProtocolStorage.getProtocolVersion(player.getAddress());
	}

	public static ProtocolVersion getProtocolVersion(SocketAddress address) {
		return ProtocolStorage.getProtocolVersion(address);
	}

}
