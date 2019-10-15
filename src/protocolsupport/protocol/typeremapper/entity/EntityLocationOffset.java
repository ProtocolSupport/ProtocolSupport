package protocolsupport.protocol.typeremapper.entity;

import java.util.Arrays;
import java.util.Collection;
import java.util.EnumMap;
import java.util.Map;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.types.networkentity.NetworkEntityType;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;
import protocolsupportbuildprocessor.Preload;

@Preload
public class EntityLocationOffset {

	protected static final Map<ProtocolVersion, EntityLocationOffset> registry = new EnumMap<>(ProtocolVersion.class);
	static {
		for (ProtocolVersion version : ProtocolVersion.getAllSupported()) {
			registry.put(version, new EntityLocationOffset());
		}
	}

	public static EntityLocationOffset get(ProtocolVersion version) {
		return registry.get(version);
	}

	protected static void register(Collection<NetworkEntityType> types, Offset offset, ProtocolVersion... versions) {
		for (ProtocolVersion version : versions) {
			Map<NetworkEntityType, Offset> offsets = registry.get(version).offsets;
			for (NetworkEntityType type : types) {
				offsets.put(type, offset);
			}
		}
	}

	protected static void register(NetworkEntityType type, Offset offset, ProtocolVersion... versions) {
		for (ProtocolVersion version : versions) {
			registry.get(version).offsets.put(type, offset);
		}
	}

	static {
		Offset halfBlockUpY = new Offset(0D, 0.5, 0D, (byte) 0, (byte) 0);
		register(
			Arrays.asList(
				NetworkEntityType.MINECART, NetworkEntityType.MINECART_CHEST, NetworkEntityType.MINECART_FURNACE,
				NetworkEntityType.MINECART_TNT, NetworkEntityType.MINECART_MOB_SPAWNER, NetworkEntityType.MINECART_HOPPER,
				NetworkEntityType.MINECART_COMMAND
			),
			halfBlockUpY, ProtocolVersionsHelper.BEFORE_1_8
		);
		register(NetworkEntityType.BOAT, new Offset(0D, 0.3, 0D, (byte) 0, (byte) 0), ProtocolVersionsHelper.BEFORE_1_8);
		register(
			Arrays.asList(NetworkEntityType.TNT, NetworkEntityType.FALLING_OBJECT),
			halfBlockUpY, ProtocolVersion.getAllBetween(ProtocolVersion.MINECRAFT_1_7_5, ProtocolVersion.MINECRAFT_1_7_10)
		);
	}


	protected final Map<NetworkEntityType, Offset> offsets = new EnumMap<>(NetworkEntityType.class);

	public Offset get(NetworkEntityType type) {
		return offsets.get(type);
	}

	public static class Offset {
		protected final double x;
		protected final double y;
		protected final double z;
		protected byte yaw;
		protected byte pitch;
		protected Offset(double x, double y, double z, byte yaw, byte pitch) {
			this.x = x;
			this.y = y;
			this.z = z;
			this.yaw = yaw;
			this.pitch = pitch;
		}
		public double getX() {
			return x;
		}
		public double getY() {
			return y;
		}
		public double getZ() {
			return z;
		}
		public byte getYaw() {
			return yaw;
		}
		public byte getPitch() {
			return pitch;
		}
	}

}
