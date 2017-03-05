package api;

import org.junit.Assert;

import junit.framework.TestCase;
import protocolsupport.api.ProtocolVersion;

public class ProtocolVersionTests extends TestCase {

	public void testIsAfter() {
		assertTrue(ProtocolVersion.MINECRAFT_1_11_1.isAfter(ProtocolVersion.MINECRAFT_1_10));
		assertFalse(ProtocolVersion.MINECRAFT_1_6_2.isAfter(ProtocolVersion.MINECRAFT_1_6_2));
		assertFalse(ProtocolVersion.MINECRAFT_1_7_10.isAfter(ProtocolVersion.MINECRAFT_1_10));
	}

	public void testIsAfterOrEq() {
		assertTrue(ProtocolVersion.MINECRAFT_1_11_1.isAfterOrEq(ProtocolVersion.MINECRAFT_1_10));
		assertTrue(ProtocolVersion.MINECRAFT_1_6_2.isAfterOrEq(ProtocolVersion.MINECRAFT_1_6_2));
		assertFalse(ProtocolVersion.MINECRAFT_1_7_10.isAfterOrEq(ProtocolVersion.MINECRAFT_1_10));
	}

	public void testIsBefore() {
		assertFalse(ProtocolVersion.MINECRAFT_1_11_1.isBefore(ProtocolVersion.MINECRAFT_1_10));
		assertFalse(ProtocolVersion.MINECRAFT_1_6_2.isBefore(ProtocolVersion.MINECRAFT_1_6_2));
		assertTrue(ProtocolVersion.MINECRAFT_1_7_10.isBefore(ProtocolVersion.MINECRAFT_1_10));
	}

	public void testIsBeforeOrEq() {
		assertFalse(ProtocolVersion.MINECRAFT_1_11_1.isBeforeOrEq(ProtocolVersion.MINECRAFT_1_10));
		assertTrue(ProtocolVersion.MINECRAFT_1_6_2.isBeforeOrEq(ProtocolVersion.MINECRAFT_1_6_2));
		assertTrue(ProtocolVersion.MINECRAFT_1_7_10.isBeforeOrEq(ProtocolVersion.MINECRAFT_1_10));
	}

	public void testIsBetween() {
		assertTrue(ProtocolVersion.MINECRAFT_1_6_4.isBetween(ProtocolVersion.MINECRAFT_1_4_7, ProtocolVersion.MINECRAFT_1_7_10));
		assertTrue(ProtocolVersion.MINECRAFT_1_6_4.isBetween(ProtocolVersion.MINECRAFT_1_6_4, ProtocolVersion.MINECRAFT_1_7_10));
		assertTrue(ProtocolVersion.MINECRAFT_1_6_4.isBetween(ProtocolVersion.MINECRAFT_1_4_7, ProtocolVersion.MINECRAFT_1_6_4));
		assertFalse(ProtocolVersion.MINECRAFT_1_6_4.isBetween(ProtocolVersion.MINECRAFT_1_7_5, ProtocolVersion.MINECRAFT_1_9));
	}

	public void testNext() {
		assertTrue(ProtocolVersion.MINECRAFT_1_4_7.next() == ProtocolVersion.MINECRAFT_1_5_1);
		assertFalse(ProtocolVersion.MINECRAFT_1_6_2.next() == ProtocolVersion.MINECRAFT_1_7_10);
	}

	public void testPrevious() {
		assertTrue(ProtocolVersion.MINECRAFT_1_5_1.previous() == ProtocolVersion.MINECRAFT_1_4_7);
		assertFalse(ProtocolVersion.MINECRAFT_1_6_2.previous() == ProtocolVersion.MINECRAFT_1_7_10);
	}

	public void testGetAllBetween() {
		Assert.assertArrayEquals(ProtocolVersion.getAllBetween(ProtocolVersion.MINECRAFT_1_6_4, ProtocolVersion.MINECRAFT_1_7_10), new ProtocolVersion[] {
			ProtocolVersion.MINECRAFT_1_6_4, ProtocolVersion.MINECRAFT_1_7_5, ProtocolVersion.MINECRAFT_1_7_10
		});
	}

}
