package com.github.shevchik.protocolsupport.api;

import java.net.SocketAddress;

import org.bukkit.entity.Player;

import com.github.shevchik.protocolsupport.api.remapper.BlockRemapperControl;
import com.github.shevchik.protocolsupport.api.remapper.ItemRemapperControl;
import com.github.shevchik.protocolsupport.protocol.storage.ProtocolStorage;

public class ProtocolSupportAPI {

	public static ProtocolVersion getProtocolVersion(Player player) {
		return ProtocolStorage.getProtocolVersion(player.getAddress());
	}

	public static ProtocolVersion getProtocolVersion(SocketAddress address) {
		return ProtocolStorage.getProtocolVersion(address);
	}

	public static ItemRemapperControl getItemRemapper(ProtocolVersion version) {
		return new ItemRemapperControl(version);
	}

	public static BlockRemapperControl getBlockRemapper(ProtocolVersion version) {
		return new BlockRemapperControl(version);
	}

}
