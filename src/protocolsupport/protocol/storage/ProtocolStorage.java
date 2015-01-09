package protocolsupport.protocol.storage;

import java.net.SocketAddress;
import java.util.HashMap;

public class ProtocolStorage {

	private final static HashMap<SocketAddress, ProtocolVersion> protocolVersions = new HashMap<SocketAddress, ProtocolVersion>();

	public static void clearData(SocketAddress address) {
		protocolVersions.remove(address);
	}

	public static void setVersion(SocketAddress address, ProtocolVersion version) {
		protocolVersions.put(address, version);
	}

	public static ProtocolVersion getVersion(SocketAddress address) {
		return protocolVersions.get(address);
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
