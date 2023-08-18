package protocolsupport.api;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Comparator;
import java.util.EnumMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.apache.commons.lang3.Validate;

import protocolsupport.utils.CollectionsUtils;
import protocolsupportbuildprocessor.Preload;

@Preload
public enum ProtocolVersion {

	MINECRAFT_FUTURE(-1, ProtocolType.PC),
	MINECRAFT_1_20(763, ProtocolType.PC, "1.20-1.20.1"),
	MINECRAFT_1_19_4(762, ProtocolType.PC, "1.19.4"),
	MINECRAFT_1_19_3(761, ProtocolType.PC, "1.19.3"),
	MINECRAFT_1_19_2(760, ProtocolType.PC, "1.19.1-1.19.2"),
	MINECRAFT_1_19(759, ProtocolType.PC, "1.19"),
	MINECRAFT_1_18_2(758, ProtocolType.PC, "1.18.2"),
	MINECRAFT_1_18(757, ProtocolType.PC, "1.18-1.18.1"),
	MINECRAFT_1_17_1(756, ProtocolType.PC, "1.17.1"),
	MINECRAFT_1_17(755, ProtocolType.PC, "1.17"),
	MINECRAFT_1_16_4(754, ProtocolType.PC, "1.16.4-1.16.5"),
	MINECRAFT_1_16_3(753, ProtocolType.PC, "1.16.3"),
	MINECRAFT_1_16_2(751, ProtocolType.PC, "1.16.2"),
	MINECRAFT_1_16_1(736, ProtocolType.PC, "1.16.1"),
	MINECRAFT_1_16(735, ProtocolType.PC, "1.16"),
	MINECRAFT_1_15_2(578, ProtocolType.PC, "1.15.2"),
	MINECRAFT_1_15_1(575, ProtocolType.PC, "1.15.1"),
	MINECRAFT_1_15(573, ProtocolType.PC, "1.15"),
	MINECRAFT_1_14_4(498, ProtocolType.PC, "1.14.4"),
	MINECRAFT_1_14_3(490, ProtocolType.PC, "1.14.3"),
	MINECRAFT_1_14_2(485, ProtocolType.PC, "1.14.2"),
	MINECRAFT_1_14_1(480, ProtocolType.PC, "1.14.1"),
	MINECRAFT_1_14(477, ProtocolType.PC, "1.14"),
	MINECRAFT_1_13_2(404, ProtocolType.PC, "1.13.2"),
	MINECRAFT_1_13_1(401, ProtocolType.PC, "1.13.1"),
	MINECRAFT_1_13(393, ProtocolType.PC, "1.13"),
	MINECRAFT_1_12_2(340, ProtocolType.PC, "1.12.2"),
	MINECRAFT_1_12_1(338, ProtocolType.PC, "1.12.1"),
	MINECRAFT_1_12(335, ProtocolType.PC, "1.12"),
	MINECRAFT_1_11_1(316, ProtocolType.PC, "1.11.2"),
	MINECRAFT_1_11(315, ProtocolType.PC, "1.11-1.11.1"),
	MINECRAFT_1_10(210, ProtocolType.PC, "1.10-1.10.2"),
	MINECRAFT_1_9_4(110, ProtocolType.PC, "1.9.4"),
	MINECRAFT_1_9_2(109, ProtocolType.PC, "1.9.2"),
	MINECRAFT_1_9_1(108, ProtocolType.PC, "1.9.1"),
	MINECRAFT_1_9(107, ProtocolType.PC, "1.9"),
	MINECRAFT_1_8(47, ProtocolType.PC, "1.8-1.8.9"),
	MINECRAFT_1_7_10(5, ProtocolType.PC, "1.7.6-1.7.10"),
	MINECRAFT_1_7_5(4, ProtocolType.PC, "1.7-1.7.5"),
	MINECRAFT_1_6_4(78, ProtocolType.PC, "1.6.4"),
	MINECRAFT_1_6_2(74, ProtocolType.PC, "1.6.2-1.6.3"),
	MINECRAFT_1_6_1(73, ProtocolType.PC, "1.6-1.6.1"),
	MINECRAFT_1_5_2(61, ProtocolType.PC, "1.5.2"),
	MINECRAFT_1_5_1(60, ProtocolType.PC, "1.5-1.5.1"),
	MINECRAFT_1_4_7(51, ProtocolType.PC, "1.4.7"),
	MINECRAFT_LEGACY(-1, ProtocolType.PC),
	UNKNOWN(-1, ProtocolType.UNKNOWN);

	private final int id;
	private final ProtocolType type;
	private final String name;
	private final int orderId;

	ProtocolVersion(int id, ProtocolType type) {
		this(id, type, null);
	}

	ProtocolVersion(int id, ProtocolType type, String name) {
		this.id = id;
		this.type = type;
		this.name = name;
		this.orderId = OrderIdGenerator.nextOrderId(type);
	}

	private static final ProtocolVersion[] allSupported = Arrays.stream(ProtocolVersion.values())
	.filter(ProtocolVersion::isSupported)
	.toArray(ProtocolVersion[]::new);

	private static final EnumMap<ProtocolType, ProtocolVersion[]> byOrderId = new EnumMap<>(ProtocolType.class);
	static {
		for (ProtocolType type : ProtocolType.values()) {
			if (type != ProtocolType.UNKNOWN) {
				byOrderId.put(type,
					Arrays.stream(ProtocolVersion.values())
					.filter(version -> version.getProtocolType() == type)
					.sorted(Comparator.comparing(o1 -> o1.orderId))
					.toArray(size -> new ProtocolVersion[size])
				);
			}
		}
	}

	/**
	 * Return protocol type of this protocol version
	 * @return {@link ProtocolType} of this protocol version
	 */
	public @Nonnull ProtocolType getProtocolType() {
		return type;
	}

	/**
	 * Returns the network version id of this protocol version
	 * @return network id of this protocol version
	 */
	public int getId() {
		return id;
	}

	/**
	 * Returns user friendly version name <br>
	 * This name can change, so it shouldn't be used as a key anywhere
	 * @return user friendly version name
	 */
	public @Nullable String getName() {
		return name;
	}

	/**
	 * Returns if this version is supported as game version (i.e.: player can possibly join and play on the server)
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
	public boolean isAfter(@Nonnull ProtocolVersion another) {
		validateCanCompare(type, another.type);
		return orderId > another.orderId;
	}

	/**
	 * Returns if the game version used by this protocol released after (or is the same) the game version used by another protocol version
	 * @param another another protocol version
	 * @return true if game version is released after (or is the same) the game version used by another protocol version
	 * @throws IllegalArgumentException if protocol versions use different protocol types
	 */
	public boolean isAfterOrEq(@Nonnull ProtocolVersion another) {
		validateCanCompare(type, another.type);
		return orderId >= another.orderId;
	}

	/**
	 * Returns if the game version used by this protocol released before the game version used by another protocol version
	 * @param another another protocol version
	 * @return true if game version is released before the game version used by another protocol version
	 * @throws IllegalArgumentException if protocol versions use different protocol types
	 */
	public boolean isBefore(@Nonnull ProtocolVersion another) {
		validateCanCompare(type, another.type);
		return orderId < another.orderId;
	}

	/**
	 * Returns if the game version used by this protocol released before (or is the same) the game version used by another protocol version
	 * @param another another protocol version
	 * @return true if game version is released before (or is the same) the game version used by another protocol version
	 * @throws IllegalArgumentException if protocol versions use different protocol types
	 */
	public boolean isBeforeOrEq(@Nonnull ProtocolVersion another) {
		validateCanCompare(type, another.type);
		return orderId <= another.orderId;
	}

	/**
	 * Returns if the game version used by this protocol released in between (or is the same) of other game versions used by others protocol versions
	 * @param one one protocol version
	 * @param another another protocol version
	 * @return true if the game version used by this protocol released in between (or is the same) of other game versions used by others protocol versions
	 * @throws IllegalArgumentException if protocol versions use different protocol types
	 */
	public boolean isBetween(@Nonnull ProtocolVersion one, @Nonnull ProtocolVersion another) {
		return (isAfterOrEq(one) && isBeforeOrEq(another)) || (isBeforeOrEq(one) && isAfterOrEq(another));
	}

	/**
	 * Returns protocol version that is used by the game version released after game version used by this protocol <br>
	 * Returns null if next game version doesn't exist
	 * @return protocol version that is used by the game version released after game version used by this protocol
	 * @throws IllegalArgumentException if protocol type is {@link ProtocolType#UNKNOWN}
	 */
	public @Nullable ProtocolVersion next() {
		Validate.isTrue(getProtocolType() != ProtocolType.UNKNOWN, "Can't get next version for unknown protocol type");
		ProtocolVersion[] versions = byOrderId.get(type);
		return CollectionsUtils.getFromArrayOrNull(versions, (orderId - versions[0].orderId) + 1);
	}

	/**
	 * Returns protocol version that is used by the game version released before game version used by this protocol <br>
	 * Returns null if previous game version doesn't exist
	 * @return protocol version that is used by the game version released before game version used by this protocol
	 * @throws IllegalArgumentException if protocol type is {@link ProtocolType#UNKNOWN}
	 */
	public @Nullable ProtocolVersion previous() {
		Validate.isTrue(getProtocolType() != ProtocolType.UNKNOWN, "Can't get next version for unknown protocol type");
		ProtocolVersion[] versions = byOrderId.get(type);
		return CollectionsUtils.getFromArrayOrNull(versions, (orderId - versions[0].orderId) - 1);
	}

	/**
	 * Returns all protocol versions that are between specified ones (inclusive) <br>
	 * Throws {@link IllegalArgumentException} if protocol versions types are not the same or one of the types is {@link ProtocolType#UNKNOWN}
	 * @param one one protocol version
	 * @param another one protocol version
	 * @return all protocol versions that are between specified ones (inclusive)
	 * @throws IllegalArgumentException if protocol types are different, or one of the protocol types is {@link ProtocolType#UNKNOWN}
	 */
	public static @Nonnull ProtocolVersion[] getAllBetween(ProtocolVersion one, ProtocolVersion another) {
		ProtocolType type = one.getProtocolType();
		Validate.isTrue(type == another.getProtocolType(), "Can't get versions between different protocol types");
		Validate.isTrue(type != ProtocolType.UNKNOWN, "Can't get versions for unknown protocol type");
		int startId = Math.min(one.orderId, another.orderId);
		int endId = Math.max(one.orderId, another.orderId);
		ProtocolVersion[] versions = byOrderId.get(type);
		int offset = startId - versions[0].orderId;
		ProtocolVersion[] between = new ProtocolVersion[(endId - startId) + 1];
		for (int i = 0; i < between.length; i++) {
			between[i] = versions[offset + i];
		}
		return between;
	}

	/**
	 * Returns all protocol versions that are after specified one (inclusive) <br>
	 * @param version protocol version
	 * @return all protocol versions that are after specified one (including this one)
	 * @throws IllegalArgumentException if {@link ProtocolVersion#getAllBetween(ProtocolVersion, ProtocolVersion)} throws one
	 */
	public static @Nonnull ProtocolVersion[] getAllAfterI(ProtocolVersion version) {
		return getAllBetween(version, getLatest(version.getProtocolType()));
	}

	/**
	 * Returns all protocol versions that are after specified one (exclusive)
	 * @param version protocol version
	 * @return all protocol versions that are after specified one  (exclusive) or empty array if no protocol versions exist after this one
	 * @throws IllegalArgumentException if {@link ProtocolVersion#getAllBetween(ProtocolVersion, ProtocolVersion)} throws one
	 */
	public static @Nonnull ProtocolVersion[] getAllAfterE(ProtocolVersion version) {
		ProtocolVersion next = version.next();
		if ((next == null) || !next.isSupported()) {
			return new ProtocolVersion[0];
		} else {
			return getAllAfterI(next);
		}
	}

	/**
	 * Returns all protocol versions that are before specified one (inclusive)
	 * @param version protocol version
	 * @return all protocol versions that are before specified one (including this one)
	 * @throws IllegalArgumentException if {@link ProtocolVersion#getAllBetween(ProtocolVersion, ProtocolVersion)} throws one
	 */
	public static @Nonnull ProtocolVersion[] getAllBeforeI(ProtocolVersion version) {
		return getAllBetween(getOldest(version.getProtocolType()), version);
	}

	/**
	 * Returns all protocol versions that are before specified one (exclusive)
	 * @param version protocol version
	 * @return all protocol versions that are before specified one  (exclusive) or empty array if no protocol versions exist after this one
	 * @throws IllegalArgumentException if {@link ProtocolVersion#getAllBetween(ProtocolVersion, ProtocolVersion)} throws one
	 */
	public static @Nonnull ProtocolVersion[] getAllBeforeE(ProtocolVersion version) {
		ProtocolVersion prev = version.previous();
		if ((prev == null) || !prev.isSupported()) {
			return new ProtocolVersion[0];
		} else {
			return getAllBeforeI(prev);
		}
	}

	/**
	 * Returns latest supported protocol version for specified protocol type
	 * @param type protocol type
	 * @return latest supported protocol version for specified protocol type
	 * @throws IllegalArgumentException if protocol type has no supported protocol versions
	 */
	public static @Nonnull ProtocolVersion getLatest(ProtocolType type) {
		return switch (type) {
			case PC -> MINECRAFT_1_20;
			default -> throw new IllegalArgumentException(MessageFormat.format("No supported versions for protocol type {0}", type));
		};
	}

	/**
	 * Returns oldest supported protocol version for specified protocol type
	 * @param type protocol type
	 * @return oldest supported protocol version for specified protocol type
	 * @throws IllegalArgumentException if protocol type has no supported protocol versions
	 */
	public static @Nonnull ProtocolVersion getOldest(ProtocolType type) {
		return switch (type) {
			case PC -> MINECRAFT_1_4_7;
			default -> throw new IllegalArgumentException(MessageFormat.format("No supported versions for protocol type {0}", type));
		};
	}

	/**
	 * Returns all supported protocol versions
	 * @return all supported protocol versions
	 */
	public static @Nonnull ProtocolVersion[] getAllSupported() {
		return allSupported.clone();
	}


	private static void validateCanCompare(ProtocolType one, ProtocolType another) {
		Validate.isTrue(one != ProtocolType.UNKNOWN, "Can't compare unknown protocol type");
		Validate.isTrue(another != ProtocolType.UNKNOWN, "Can't compare with unknown protocol type");
		Validate.isTrue(one == another, "Cant compare order from different types");
	}

	private static class OrderIdGenerator {
		private static final Map<ProtocolType, AtomicInteger> lastOrderId = new EnumMap<>(ProtocolType.class);
		public static int nextOrderId(ProtocolType type) {
			return lastOrderId.computeIfAbsent(type, k -> new AtomicInteger()).decrementAndGet();
		}
	}

}
