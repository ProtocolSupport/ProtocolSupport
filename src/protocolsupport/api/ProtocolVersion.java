package protocolsupport.api;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.EnumMap;

import org.apache.commons.lang3.Validate;

import gnu.trove.map.hash.TIntObjectHashMap;

public enum ProtocolVersion {

	MINECRAFT_FUTURE(-1, new OrderId(ProtocolType.PC, 17)),
	MINECRAFT_1_11_1(316, new OrderId(ProtocolType.PC, 16), "1.11.2"),
	MINECRAFT_1_11(315, new OrderId(ProtocolType.PC, 15), "1.11"),
	MINECRAFT_1_10(210, new OrderId(ProtocolType.PC, 14), "1.10"),
	MINECRAFT_1_9_4(110, new OrderId(ProtocolType.PC, 13), "1.9.4"),
	MINECRAFT_1_9_2(109, new OrderId(ProtocolType.PC, 12), "1.9.2"),
	MINECRAFT_1_9_1(108, new OrderId(ProtocolType.PC, 11), "1.9.1"),
	MINECRAFT_1_9(107, new OrderId(ProtocolType.PC, 10), "1.9"),
	MINECRAFT_1_8(47, new OrderId(ProtocolType.PC, 9), "1.8"),
	MINECRAFT_1_7_10(5, new OrderId(ProtocolType.PC, 8), "1.7.10"),
	MINECRAFT_1_7_5(4, new OrderId(ProtocolType.PC, 7), "1.7.5"),
	MINECRAFT_1_6_4(78, new OrderId(ProtocolType.PC, 6), "1.6.4"),
	MINECRAFT_1_6_2(74, new OrderId(ProtocolType.PC, 5), "1.6.2"),
	MINECRAFT_1_6_1(73, new OrderId(ProtocolType.PC, 4), "1.6.1"),
	MINECRAFT_1_5_2(61, new OrderId(ProtocolType.PC, 3), "1.5.2"),
	MINECRAFT_1_5_1(60, new OrderId(ProtocolType.PC, 2), "1.5.1"),
	MINECRAFT_1_4_7(51, new OrderId(ProtocolType.PC, 1), "1.4.7"),
	MINECRAFT_LEGACY(-1, new OrderId(ProtocolType.PC, 0)),
	MINECRAFT_PE(-1, new OrderId(ProtocolType.PE, 0), "pe"),
	UNKNOWN(-1, new OrderId(ProtocolType.UNKNOWN, 0));

	private final int id;
	private final OrderId orderId;
	private final String name;

	ProtocolVersion(int id, OrderId orderid) {
		this(id, orderid, null);
	}

	ProtocolVersion(int id, OrderId orderId, String name) {
		this.id = id;
		this.orderId = orderId;
		this.name = name;
	}

	/**
	 * Return protocol type of this protocol version
	 * @return {@link ProtocolType} of this protocol version
	 */
	public ProtocolType getProtocolType() {
		return orderId.type;
	}

	/**
	 * Returns the network version id of this protocol version
	 * @return network id of this protocol version
	 */
	public int getId() {
		return id;
	}

	/**
	 * Returns user friendly version name
	 * Notice: This name can change, so it shouldn't be used as a key anywhere
	 * @return user friendly version name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Returns if this version is supported as game version (i.e.: player can join and play on the server)
	 * @return true if this protocol version is supported
	 */
	public boolean isSupported() {
		return name != null;
	}

	/**
	 * Returns if the game version used by this protocol released after the game version used by another protocol version
	 * @param another another protocol version
	 * @return true if game version is released after the game version used by another protocol version
	 * @throws IllegalArgumentException if protocol versions use different protocol types
	 */
	public boolean isAfter(ProtocolVersion another) {
		return orderId.compareTo(another.orderId) > 0;
	}

	/**
	 * Returns if the game version used by this protocol released after (or is the same) the game version used by another protocol version
	 * @param another another protocol version
	 * @return true if game version is released after (or is the same) the game version used by another protocol version
	 * @throws IllegalArgumentException if protocol versions use different protocol types
	 */
	public boolean isAfterOrEq(ProtocolVersion another) {
		return orderId.compareTo(another.orderId) >= 0;
	}

	/**
	 * Returns if the game version used by this protocol released before the game version used by another protocol version
	 * @param another another protocol version
	 * @return true if game version is released before the game version used by another protocol version
	 * @throws IllegalArgumentException if protocol versions use different protocol types
	 */
	public boolean isBefore(ProtocolVersion another) {
		return orderId.compareTo(another.orderId) < 0;
	}

	/**
	 * Returns if the game version used by this protocol released before (or is the same) the game version used by another protocol version
	 * @param another another protocol version
	 * @return true if game version is released before (or is the same) the game version used by another protocol version
	 * @throws IllegalArgumentException if protocol versions use different protocol types
	 */
	public boolean isBeforeOrEq(ProtocolVersion another) {
		return orderId.compareTo(another.orderId) <= 0;
	}

	/**
	 * Returns if the game version used by this protocol released in between (or is the same) of other game versions used by others protocol versions
	 * @param one one protocol version
	 * @param another another protocol version
	 * @return true if game version is released before (or is the same) the game version used by another protocol version
	 * @throws IllegalArgumentException if protocol versions use different protocol types
	 */
	public boolean isBetween(ProtocolVersion one, ProtocolVersion another) {
		return (isAfterOrEq(one) && isBeforeOrEq(another)) || (isBeforeOrEq(one) && isAfterOrEq(another));
	}

	private static final TIntObjectHashMap<ProtocolVersion> byProtocolId = new TIntObjectHashMap<>();
	static {
		Arrays.stream(ProtocolVersion.values())
		.filter(ProtocolVersion::isSupported)
		.forEach(version -> byProtocolId.put(version.id, version));
	}

	/**
	 * Returns protocol version by network game id
	 * @param id network version id
	 * @return Returns protocol version by network game id or UNKNOWN if not found
	 * @deprecated network version ids may be the same for different protocol versions
	 */
	@Deprecated
	public static ProtocolVersion fromId(int id) {
		ProtocolVersion version = byProtocolId.get(id);
		return version != null ? version : UNKNOWN;
	}

	private static final EnumMap<ProtocolType, ProtocolVersion[]> byOrderId = new EnumMap<>(ProtocolType.class);
	static {
		for (ProtocolType type : ProtocolType.values()) {
			if (type != ProtocolType.UNKNOWN) {
				byOrderId.put(type,
					Arrays.stream(ProtocolVersion.values())
					.filter(version -> version.getProtocolType() == type)
					.sorted((o1, o2) -> o1.orderId.compareTo(o2.orderId))
					.toArray(size -> new ProtocolVersion[size])
				);
			}
		}
	}

	/**
	 * Returns protocol version that is used by the game version released after game version used by this protocol
	 * Returns null if next game version doesn't exist
	 * @return protocol version that is used by the game version released after game version used by this protocol
	 * @throws IllegalArgumentException if protocol type is UNKNOWN
	 */
	public ProtocolVersion next() {
		Validate.isTrue(getProtocolType() != ProtocolType.UNKNOWN, "Can't get next version for unknown protocol type");
		ProtocolVersion[] versions = byOrderId.get(getProtocolType());
		int nextVersionOrderId = orderId.id + 1;
		if (nextVersionOrderId < versions.length) {
			return versions[nextVersionOrderId];
		} else {
			return null;
		}
	}

	/**
	 * Returns protocol version that is used by the game version released before game version used by this protocol
	 * Returns null if previous game version doesn't exist
	 * @return protocol version that is used by the game version released before game version used by this protocol
	 * @throws IllegalArgumentException if protocol type is UNKNOWN
	 */
	public ProtocolVersion previous() {
		Validate.isTrue(getProtocolType() != ProtocolType.UNKNOWN, "Can't get next version for unknown protocol type");
		ProtocolVersion[] versions = byOrderId.get(getProtocolType());
		int previousVersionOrderId = orderId.id - 1;
		if (previousVersionOrderId >= 0) {
			return versions[previousVersionOrderId];
		} else {
			return null;
		}
	}

	/**
	 * Returns all protocol versions that are between specified ones (inclusive)
	 * Throws {@link IllegalArgumentException} if protocol versions types are not the same or one of the types is UNKNOWN
	 * @param one one protocol version
	 * @param another one protocol version
	 * @return all protocol versions that are between specified ones (inclusive)
	 */
	public static ProtocolVersion[] getAllBetween(ProtocolVersion one, ProtocolVersion another) {
		ProtocolType type = one.getProtocolType();
		Validate.isTrue(type == another.getProtocolType(), "Can't get versions between different protocol types");
		Validate.isTrue(type != ProtocolType.UNKNOWN, "Can't get versions for unknown protocol type");
		ProtocolVersion[] versions = byOrderId.get(type);
		int startId = Math.min(one.orderId.id, another.orderId.id);
		int endId = Math.max(one.orderId.id, another.orderId.id);
		ProtocolVersion[] between = new ProtocolVersion[(endId - startId) + 1];
		for (int i = startId; i <= endId; i++) {
			between[i - startId] = versions[i];
		}
		return between;
	}

	/**
	 * Returns latest supported protocol version for specified protocol type
	 * @param type protocol type
	 * @return latest supported protocol version for specified protocol type
	 * @throws IllegalArgumentException if protocol type has not supported protocol versions
	 */
	public static ProtocolVersion getLatest(ProtocolType type) {
		switch (type) {
			case PC: {
				return MINECRAFT_1_11_1;
			}
			case PE: {
				return MINECRAFT_PE;
			}
			default: {
				throw new IllegalArgumentException(MessageFormat.format("No supported versions for protocol type {0}", type));
			}
		}
	}

	/**
	 * Returns oldest supported protocol version for specified protocol type
	 * @param type protocol type
	 * @return oldest supported protocol version for specified protocol type
	 * @throws IllegalArgumentException if protocol type has not supported protocol versions
	 */
	public static ProtocolVersion getOldest(ProtocolType type) {
		switch (type) {
			case PC: {
				return MINECRAFT_1_4_7;
			}
			case PE: {
				return MINECRAFT_PE;
			}
			default: {
				throw new IllegalArgumentException(MessageFormat.format("No supported versions for protocol type {0}", type));
			}
		}
	}

	/**
	 * Returns all protocol versions that are after specified one (inclusive)
	 * @param version protocol version
	 * @return all protocol versions that are after specified one  (inclusive)
	 * @throws IllegalArgumentException  if getAllBetween(version, getLatest(version.getType())) throws one
	 * @deprecated non intuitive behavior
	 */
	@Deprecated
	public static ProtocolVersion[] getAllAfter(ProtocolVersion version) {
		return getAllBetween(version, getLatest(version.getProtocolType()));
	}

	/**
	 * Returns all protocol versions that are before specified one (inclusive)
	 * @param version protocol version
	 * @return all protocol versions that are before specified one
	 * @throws IllegalArgumentException if getAllBetween(getOldest(version.getType()), version) throws one
	 * @deprecated non intuitive behavior
	 */
	@Deprecated
	public static ProtocolVersion[] getAllBefore(ProtocolVersion version) {
		return getAllBetween(getOldest(version.getProtocolType()), version);
	}

	/**
	 * Returns latest supported protocol version for {@link ProtocolType} PC
	 * @return latest supported protocol version for {@link ProtocolType} PC
	 * @deprecated only returns latest version for {@link ProtocolType} PC
	 */
	@Deprecated
	public static ProtocolVersion getLatest() {
		return getLatest(ProtocolType.PC);
	}

	/**
	 * Returns oldest supported protocol version for {@link ProtocolType} PC
	 * @return oldest supported protocol version for {@link ProtocolType} PC
	 * @deprecated only returns oldest version for {@link ProtocolType} PC
	 */
	@Deprecated
	public static ProtocolVersion getOldest() {
		return getOldest(ProtocolType.PC);
	}

	private static class OrderId implements Comparable<OrderId> {

		private final ProtocolType type;
		private final int id;

		public OrderId(ProtocolType type, int id) {
			this.type = type;
			this.id = id;
		}

		@Override
		public int compareTo(OrderId o) {
			Validate.isTrue(this.type != ProtocolType.UNKNOWN, "Can't compare unknown protocol type");
			Validate.isTrue(o.type != ProtocolType.UNKNOWN, "Can't compare with unknown protocol type");
			Validate.isTrue(this.type == o.type, MessageFormat.format("Can't compare order from different types: this - {0}, other - {1}", type, o.type));
			return Integer.compare(id, o.id);
		}

	}

}