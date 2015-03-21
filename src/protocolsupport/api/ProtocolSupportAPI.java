package protocolsupport.api;

import org.bukkit.entity.Player;

import protocolsupport.protocol.storage.ProtocolStorage;

public class ProtocolSupportAPI {

	public static ProtocolVersion getProtocolVersion(Player player) {
		return ProtocolStorage.getProtocolVersion(player.getAddress());
	}
}
