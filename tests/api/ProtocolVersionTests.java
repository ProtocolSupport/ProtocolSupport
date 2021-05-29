package api;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import protocolsupport.api.ProtocolType;
import protocolsupport.api.ProtocolVersion;

class ProtocolVersionTests {

	@Test
	void testIsAfter() {
		Assertions.assertTrue(ProtocolVersion.MINECRAFT_1_11_1.isAfter(ProtocolVersion.MINECRAFT_1_10));
		Assertions.assertFalse(ProtocolVersion.MINECRAFT_1_6_2.isAfter(ProtocolVersion.MINECRAFT_1_6_2));
		Assertions.assertFalse(ProtocolVersion.MINECRAFT_1_7_10.isAfter(ProtocolVersion.MINECRAFT_1_10));
	}

	@Test
	void testIsAfterOrEq() {
		Assertions.assertTrue(ProtocolVersion.MINECRAFT_1_11_1.isAfterOrEq(ProtocolVersion.MINECRAFT_1_10));
		Assertions.assertTrue(ProtocolVersion.MINECRAFT_1_6_2.isAfterOrEq(ProtocolVersion.MINECRAFT_1_6_2));
		Assertions.assertFalse(ProtocolVersion.MINECRAFT_1_7_10.isAfterOrEq(ProtocolVersion.MINECRAFT_1_10));
	}

	@Test
	void testIsBefore() {
		Assertions.assertFalse(ProtocolVersion.MINECRAFT_1_11_1.isBefore(ProtocolVersion.MINECRAFT_1_10));
		Assertions.assertFalse(ProtocolVersion.MINECRAFT_1_6_2.isBefore(ProtocolVersion.MINECRAFT_1_6_2));
		Assertions.assertTrue(ProtocolVersion.MINECRAFT_1_7_10.isBefore(ProtocolVersion.MINECRAFT_1_10));
	}

	@Test
	void testIsBeforeOrEq() {
		Assertions.assertFalse(ProtocolVersion.MINECRAFT_1_11_1.isBeforeOrEq(ProtocolVersion.MINECRAFT_1_10));
		Assertions.assertTrue(ProtocolVersion.MINECRAFT_1_6_2.isBeforeOrEq(ProtocolVersion.MINECRAFT_1_6_2));
		Assertions.assertTrue(ProtocolVersion.MINECRAFT_1_7_10.isBeforeOrEq(ProtocolVersion.MINECRAFT_1_10));
	}

	@Test
	void testIsBetween() {
		Assertions.assertTrue(ProtocolVersion.MINECRAFT_1_6_4.isBetween(ProtocolVersion.MINECRAFT_1_4_7, ProtocolVersion.MINECRAFT_1_7_10));
		Assertions.assertTrue(ProtocolVersion.MINECRAFT_1_6_4.isBetween(ProtocolVersion.MINECRAFT_1_6_4, ProtocolVersion.MINECRAFT_1_7_10));
		Assertions.assertTrue(ProtocolVersion.MINECRAFT_1_6_4.isBetween(ProtocolVersion.MINECRAFT_1_4_7, ProtocolVersion.MINECRAFT_1_6_4));
		Assertions.assertFalse(ProtocolVersion.MINECRAFT_1_6_4.isBetween(ProtocolVersion.MINECRAFT_1_7_5, ProtocolVersion.MINECRAFT_1_9));
	}

	@Test
	void testNext() {
		Assertions.assertEquals(ProtocolVersion.MINECRAFT_1_5_1, ProtocolVersion.MINECRAFT_1_4_7.next());
		Assertions.assertNotEquals(ProtocolVersion.MINECRAFT_1_7_10, ProtocolVersion.MINECRAFT_1_6_2.next());
	}

	@Test
	void testPrevious() {
		Assertions.assertEquals(ProtocolVersion.MINECRAFT_1_4_7, ProtocolVersion.MINECRAFT_1_5_1.previous());
		Assertions.assertNotEquals(ProtocolVersion.MINECRAFT_1_7_10, ProtocolVersion.MINECRAFT_1_6_2.previous());
	}

	@Test
	void testGetAllBetween() {
		Assertions.assertArrayEquals(
			new ProtocolVersion[] {ProtocolVersion.MINECRAFT_1_6_4, ProtocolVersion.MINECRAFT_1_7_5, ProtocolVersion.MINECRAFT_1_7_10},
			ProtocolVersion.getAllBetween(ProtocolVersion.MINECRAFT_1_6_4, ProtocolVersion.MINECRAFT_1_7_10)
		);
		Assertions.assertArrayEquals(
			new ProtocolVersion[] {ProtocolVersion.MINECRAFT_1_6_4, ProtocolVersion.MINECRAFT_1_7_5, ProtocolVersion.MINECRAFT_1_7_10},
			ProtocolVersion.getAllBetween(ProtocolVersion.MINECRAFT_1_7_10, ProtocolVersion.MINECRAFT_1_6_4)
		);
	}

	@Test
	void testGetAllAfterI() {
		Assertions.assertArrayEquals(
			ProtocolVersion.getAllAfterI(ProtocolVersion.MINECRAFT_1_11),
			ProtocolVersion.getAllBetween(ProtocolVersion.MINECRAFT_1_11, ProtocolVersion.getLatest(ProtocolType.PC))
		);
	}

	@Test
	void testGetAllAfterE() {
		Assertions.assertArrayEquals(
			ProtocolVersion.getAllAfterE(ProtocolVersion.MINECRAFT_1_11),
			ProtocolVersion.getAllBetween(ProtocolVersion.MINECRAFT_1_11_1, ProtocolVersion.getLatest(ProtocolType.PC))
		);
		Assertions.assertArrayEquals(new ProtocolVersion[0], ProtocolVersion.getAllAfterE(ProtocolVersion.getLatest(ProtocolType.PC)));
	}

	@Test
	void testGetAllBeforeI() {
		Assertions.assertArrayEquals(
			ProtocolVersion.getAllBeforeI(ProtocolVersion.MINECRAFT_1_11),
			ProtocolVersion.getAllBetween(ProtocolVersion.MINECRAFT_1_11, ProtocolVersion.getOldest(ProtocolType.PC))
		);
	}

	@Test
	void testGetAllBeforeE() {
		Assertions.assertArrayEquals(
			ProtocolVersion.getAllBeforeE(ProtocolVersion.MINECRAFT_1_11),
			ProtocolVersion.getAllBetween(ProtocolVersion.MINECRAFT_1_10, ProtocolVersion.getOldest(ProtocolType.PC))
		);
		Assertions.assertArrayEquals(new ProtocolVersion[0], ProtocolVersion.getAllBeforeE(ProtocolVersion.getOldest(ProtocolType.PC)));
	}

}
