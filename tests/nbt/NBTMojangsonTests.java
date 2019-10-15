package nbt;

import java.io.IOException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import protocolsupport.protocol.types.nbt.NBTByte;
import protocolsupport.protocol.types.nbt.NBTByteArray;
import protocolsupport.protocol.types.nbt.NBTCompound;
import protocolsupport.protocol.types.nbt.NBTDouble;
import protocolsupport.protocol.types.nbt.NBTFloat;
import protocolsupport.protocol.types.nbt.NBTInt;
import protocolsupport.protocol.types.nbt.NBTIntArray;
import protocolsupport.protocol.types.nbt.NBTList;
import protocolsupport.protocol.types.nbt.NBTLong;
import protocolsupport.protocol.types.nbt.NBTLongArray;
import protocolsupport.protocol.types.nbt.NBTShort;
import protocolsupport.protocol.types.nbt.NBTString;
import protocolsupport.protocol.types.nbt.NBTType;
import protocolsupport.protocol.types.nbt.mojangson.MojangsonParser;
import protocolsupport.protocol.types.nbt.mojangson.MojangsonSerializer;

public class NBTMojangsonTests {

	protected static final NBTCompound tag = new NBTCompound();
	static {
		tag.setTag("testbyte", new NBTByte((byte) 5));
		tag.setTag("testshort", new NBTShort((short) 78));
		tag.setTag("testint", new NBTInt(555));
		tag.setTag("testlong", new NBTLong(125));
		tag.setTag("testfloat", new NBTFloat(26.55F));
		tag.setTag("testdouble", new NBTDouble(125.111));
		tag.setTag("testqnstring", new NBTString("111"));
		tag.setTag("testuqstring", new NBTString("uc"));
		tag.setTag("testqwsstring", new NBTString("w s"));
		tag.setTag("testqestring", new NBTString("\\\""));
		tag.setTag("testba", new NBTByteArray(new byte[] {1, 6, 73, 67}));
		tag.setTag("testia", new NBTIntArray(new int[] {1, 6, 6}));
		tag.setTag("testla", new NBTLongArray(new long[] {6, 77, 888}));
		NBTList<NBTInt> ilist = new NBTList<>(NBTType.INT);
		ilist.addTag(new NBTInt(1));
		ilist.addTag(new NBTInt(10));
		tag.setTag("testilist", ilist);
		NBTList<NBTString> slist = new NBTList<>(NBTType.STRING);
		slist.addTag(new NBTString(";111"));
		slist.addTag(new NBTString("111"));
		slist.addTag(new NBTString("uc"));
		slist.addTag(new NBTString("w s"));
		tag.setTag("testslist", slist);
		NBTCompound compound = new NBTCompound();
		compound.setTag("test", new NBTShort((short) 1));
		tag.setTag("testcompound", compound);
	}

	@Test
	public void testSerialize() {
		Assertions.assertEquals(
			"{" +
			"\"testbyte\":5b" + "," +
			"\"testshort\":78s" + "," +
			"\"testint\":555" + "," +
			"\"testlong\":125l" + "," +
			"\"testfloat\":26.55f" + "," +
			"\"testdouble\":125.111d" + "," +
			"\"testqnstring\":\"111\"" + "," +
			"\"testuqstring\":\"uc\"" + "," +
			"\"testqwsstring\":\"w s\"" + "," +
			"\"testqestring\":\"\\\\\\\"\"" + "," +
			"\"testba\":[B;1b,6b,73b,67b]" + "," +
			"\"testia\":[I;1,6,6]" + "," +
			"\"testla\":[L;6l,77l,888l]" + "," +
			"\"testilist\":[1,10]" + "," +
			"\"testslist\":[\";111\",\"111\",\"uc\",\"w s\"]" + "," +
			"\"testcompound\":{\"test\":1s}" +
			"}",
			MojangsonSerializer.serialize(tag)
		);
	}

	@Test
	public void testParse() throws IOException {
		Assertions.assertEquals(tag, MojangsonParser.parse(
			"{" +
			"   testbyte   :   5b   " + "," +
			" testshort:78s   " + "," +
			"testint     :   555" + "," +
			"   testlong  :    125L  " + "," +
			"  testfloat   : 26.55f" + "," +
			"    testdouble:  125.111 " + "," +
			"   testqnstring :  \"111\"  " + "," +
			"   testuqstring : uc" + "," +
			"  testqwsstring: \"w s\"" + "," +
			"   testqestring  : \"\\\\\\\"\"   " + "," +
			"   testba: [B;  1b  , 6B,73b  ,   67B]   " + "," +
			"  testia: [I;   1,  6,  6] " + "," +
			"   testla: [L;   6l,  77L  , 888l  ]" + "," +
			"  testilist: [1 , 10  ] " + "," +
			"   testslist:  [\";111\"   ,  \"111\",   uc   ,\"w s\"  ] " + "," +
			"  testcompound  : {\"test\"  :  1s  }  " +
			"}"
		));
	}

}
