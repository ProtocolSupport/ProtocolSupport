package protocolsupport.api;

import java.net.InetSocketAddress;

import org.bukkit.entity.Player;

import protocolsupport.protocol.storage.ProtocolStorage;

public class ProtocolSupportAPI {

	public static ProtocolVersion getProtocolVersion(Player player) {
		return getProtocolVersion(player.getAddress());
	}

	public static ProtocolVersion getProtocolVersion(InetSocketAddress address) {
		return ProtocolStorage.getProtocolVersion(address);
	}

}
