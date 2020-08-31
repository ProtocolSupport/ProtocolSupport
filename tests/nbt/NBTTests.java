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

		Assertions.assertNotNull(compound.getTagOrNull(testtagname));
		Assertions.assertNotNull(compound.getTagOfTypeOrNull(testtagname, NBTShort.class));
		Assertions.assertNull(compound.getTagOfTypeOrNull(testtagname, NBTInt.class));
		Assertions.assertNotNull(compound.getNumberTagOrNull(testtagname));

		Assertions.assertNotNull(compound.getTagOrNull(testlisttagname));
		Assertions.assertNotNull(compound.getTagOfTypeOrNull(testlisttagname, NBTList.class));
		Assertions.assertNull(compound.getTagOfTypeOrNull(testlisttagname, NBTDouble.class));
		Assertions.assertNotNull(compound.getTagListOfTypeOrNull(testlisttagname, NBTInt.class));
		Assertions.assertNull(compound.getTagListOfTypeOrNull(testlisttagname, NBTShort.class));
		Assertions.assertNotNull(compound.getNumberListTagOrNull(testlisttagname));
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
