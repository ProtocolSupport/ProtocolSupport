package nbt;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import protocolsupport.protocol.types.nbt.NBTByte;
import protocolsupport.protocol.types.nbt.NBTCompound;
import protocolsupport.protocol.types.nbt.NBTDouble;
import protocolsupport.protocol.types.nbt.NBTFloat;
import protocolsupport.protocol.types.nbt.NBTInt;
import protocolsupport.protocol.types.nbt.NBTList;
import protocolsupport.protocol.types.nbt.NBTLong;
import protocolsupport.protocol.types.nbt.NBTShort;
import protocolsupport.protocol.types.nbt.NBTString;
import protocolsupport.protocol.types.nbt.NBTType;

public class NBTTests {

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Test
	public void testList() {
		NBTList<NBTInt> list = new NBTList<>(NBTType.INT);

		list.addTag(new NBTInt(30));
		list.addTag(new NBTInt(30));

		Assertions.assertTrue(list.getTagsType() == NBTType.INT);
		Assertions.assertTrue(list.size() == 2);
		Assertions.assertThrows(IllegalArgumentException.class, () -> ((NBTList) list).addTag(new NBTShort((short) 0)));
	}

	@Test
	public void testCompound() {
		String testtagname = "test";
		NBTCompound compound = new NBTCompound();

		compound.setTag(testtagname, new NBTShort((short) 20));

		String testlisttagname = "testlist";
		NBTList<NBTInt> list = new NBTList<>(NBTType.INT);
		list.addTag(new NBTInt(30));
		compound.setTag(testlisttagname, list);

		Assertions.assertNotNull(compound.getTag(testtagname));
		Assertions.assertNotNull(compound.getTagOfType(testtagname, NBTType.SHORT));
		Assertions.assertNull(compound.getTagOfType(testtagname, NBTType.INT));
		Assertions.assertNotNull(compound.getNumberTag(testtagname));

		Assertions.assertNotNull(compound.getTag(testlisttagname));
		Assertions.assertNotNull(compound.getTagOfType(testlisttagname, NBTType.LIST));
		Assertions.assertNull(compound.getTagOfType(testlisttagname, NBTType.DOUBLE));
		Assertions.assertNotNull(compound.getTagListOfType(testlisttagname, NBTType.INT));
		Assertions.assertNull(compound.getTagListOfType(testlisttagname, NBTType.SHORT));
		Assertions.assertNotNull(compound.getNumberTagList(testlisttagname));
	}

	@Test
	public void testEq() {
		Assertions.assertEquals(new NBTByte((byte) 5), new NBTByte((byte) 5));
		Assertions.assertEquals(new NBTShort((short) 5), new NBTShort((short) 5));
		Assertions.assertEquals(new NBTInt(5), new NBTInt(5));
		Assertions.assertEquals(new NBTLong(5), new NBTLong(5));
		Assertions.assertEquals(new NBTFloat(5), new NBTFloat(5));
		Assertions.assertEquals(new NBTDouble(5), new NBTDouble(5));
		Assertions.assertEquals(new NBTString("5"), new NBTString("5"));
	}

}
