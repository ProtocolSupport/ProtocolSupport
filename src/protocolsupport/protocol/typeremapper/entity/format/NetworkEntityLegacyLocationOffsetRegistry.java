package protocolsupport.protocol.typeremapper.entity.format;

import java.util.Arrays;
import java.util.Collection;
import java.util.EnumMap;
import java.util.Map;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.typeremapper.entity.format.NetworkEntityLegacyLocationOffsetRegistry.NetworkEntityLegacyLocationOffsetTable;
import protocolsupport.protocol.typeremapper.utils.MappingRegistry;
import protocolsupport.protocol.typeremapper.utils.MappingTable;
import protocolsupport.protocol.types.networkentity.NetworkEntityType;
import protocolsupport.protocol.utils.PrimitiveTypeUtils;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;
import protocolsupportbuildprocessor.Preload;

@Preload
public class NetworkEntityLegacyLocationOffsetRegistry extends MappingRegistry<NetworkEntityLegacyLocationOffsetTable> {

	public static final NetworkEntityLegacyLocationOffsetRegistry INSTANCE = new NetworkEntityLegacyLocationOffsetRegistry();

	protected NetworkEntityLegacyLocationOffsetRegistry() {
		LocationOffset halfBlockUpY = new LocationOffset(0D, 0.5, 0D, (byte) 0, (byte) 0);
		register(
			Arrays.asList(
				NetworkEntityType.MINECART, NetworkEntityType.MINECART_CHEST, NetworkEntityType.MINECART_FURNACE,
				NetworkEntityType.MINECART_TNT, NetworkEntityType.MINECART_MOB_SPAWNER, NetworkEntityType.MINECART_HOPPER,
				NetworkEntityType.MINECART_COMMAND
			),
			halfBlockUpY, ProtocolVersionsHelper.DOWN_1_7_10
		);
		register(NetworkEntityType.BOAT, new LocationOffset(0D, 0.35D, 0D, PrimitiveTypeUtils.toAngleB(-90F), (byte) 0), ProtocolVersion.MINECRAFT_1_8);
		register(NetworkEntityType.BOAT, new LocationOffset(0D, 0.35D, 0D, PrimitiveTypeUtils.toAngleB(-90F), (byte) 0), ProtocolVersionsHelper.DOWN_1_7_10);
		register(
			Arrays.asList(NetworkEntityType.TNT, NetworkEntityType.FALLING_OBJECT),
			halfBlockUpY, ProtocolVersion.getAllBetween(ProtocolVersion.MINECRAFT_1_7_5, ProtocolVersion.MINECRAFT_1_7_10)
		);
	}

	protected void register(Collection<NetworkEntityType> types, LocationOffset offset, ProtocolVersion... versions) {
		for (ProtocolVersion version : versions) {
			NetworkEntityLegacyLocationOffsetTable table = getTable(version);
			for (NetworkEntityType type : types) {
				table.set(type, offset);
			}
		}
	}

	protected void register(NetworkEntityType type, LocationOffset offset, ProtocolVersion... versions) {
		for (ProtocolVersion version : versions) {
			getTable(version).set(type, offset);
		}
	}

	@Override
	protected NetworkEntityLegacyLocationOffsetTable createTable() {
		return new NetworkEntityLegacyLocationOffsetTable();
	}


	public static class NetworkEntityLegacyLocationOffsetTable extends MappingTable {

		protected final Map<NetworkEntityType, LocationOffset> table = new EnumMap<>(NetworkEntityType.class);

		public void set(@Nonnull NetworkEntityType type, @Nonnull LocationOffset offset) {
			table.put(type, offset);
		}

		public @Nullable LocationOffset get(@Nonnull NetworkEntityType type) {
			return table.get(type);
		}

	}

	public static class LocationOffset {

		protected final double x;
		protected final double y;
		protected final double z;
		protected byte yaw;
		protected byte pitch;

		protected LocationOffset(double x, double y, double z, byte yaw, byte pitch) {
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
