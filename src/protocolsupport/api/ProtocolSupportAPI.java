package protocolsupport.api;

import java.net.SocketAddress;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.craftbukkit.v1_10_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

import net.minecraft.server.v1_10_R1.EntityPlayer;
import protocolsupport.api.remapper.BlockRemapperControl;
import protocolsupport.api.remapper.ItemRemapperControl;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.storage.ProtocolStorage;
import protocolsupport.utils.Utils;
import protocolsupport.utils.Utils.Converter;

public class ProtocolSupportAPI {

	private static final boolean getconnectioncheckall = Utils.getJavaPropertyValue("api.getconnection.checkall", false, Converter.STRING_TO_BOOLEAN);

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
		if (player instanceof CraftPlayer) {
			EntityPlayer nmsplayer = ((CraftPlayer) player).getHandle();
			if (nmsplayer.playerConnection != null) {
				return ConnectionImpl.getFromChannel(nmsplayer.playerConnection.networkManager.channel);
			}
		}
		return ProtocolStorage.getConnection(player.getAddress());
	}

	public static Connection getConnection(SocketAddress address) {
		Connection connection = ProtocolStorage.getConnection(address);
		if (connection != null) {
			return connection;
		}
		if (getconnectioncheckall) {
			for (Connection econnection : getConnections()) {
				if (econnection.getAddress().equals(address)) {
					return econnection;
				}
			}
		}
		return null;
	}

	public static ItemRemapperControl getItemRemapper(ProtocolVersion version) {
		return new ItemRemapperControl(version);
	}

	public static BlockRemapperControl getBlockRemapper(ProtocolVersion version) {
		return new BlockRemapperControl(version);
	}

}
