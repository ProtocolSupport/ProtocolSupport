package nbt;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.ByteBufOutputStream;
import io.netty.buffer.Unpooled;
import protocolsupport.protocol.serializer.MiscSerializer;
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
import protocolsupport.protocol.types.nbt.serializer.DefaultNBTSerializer;

public class NBTSerializersTests {

	protected static final byte[] serializedTagData = new byte[] {
		10, 0, 0, 1, 0, 8, 116, 101, 115, 116, 98, 121, 116, 101, 5, 2, 0, 9, 116, 101, 115, 116, 115, 104, 111, 114, 116, 0, 78, 3, 0,
		7, 116, 101, 115, 116, 105, 110, 116, 0, 0, 2, 43, 4, 0, 8, 116, 101, 115, 116, 108, 111, 110, 103, 0, 0, 0, 0, 0, 0, 0, 125, 5,
		0, 9, 116, 101, 115, 116, 102, 108, 111, 97, 116, 65, -44, 102, 102, 6, 0, 10, 116, 101, 115, 116, 100, 111, 117, 98, 108, 101,
		64, 95, 71, 26, -97, -66, 118, -55, 8, 0, 10, 116, 101, 115, 116, 115, 116, 114, 105, 110, 103, 0, 4, 49, 49, 53, 52, 7, 0, 6,
		116, 101, 115, 116, 98, 97, 0, 0, 0, 4, 1, 6, 73, 67, 11, 0, 6, 116, 101, 115, 116, 105, 97, 0, 0, 0, 3, 0, 0, 0, 1, 0, 0, 0,
		6, 0, 0, 0, 6, 12, 0, 6, 116, 101, 115, 116, 108, 97, 0, 0, 0, 3, 0, 0, 0, 0, 0, 0, 0, 6, 0, 0, 0, 0, 0, 0, 0, 77, 0, 0, 0, 0,
		0, 0, 3, 120, 10, 0, 12, 116, 101, 115, 116, 99, 111, 109, 112, 111, 117, 110, 100, 2, 0, 4, 116, 101, 115, 116, 0, 1, 0, 9, 0,
		8, 116, 101, 115, 116, 108, 105, 115, 116, 3, 0, 0, 0, 2, 0, 0, 0, 1, 0, 0, 0, 10, 0
	};

	protected static final NBTCompound tag = new NBTCompound();
	static {
		tag.setTag("testbyte", new NBTByte((byte) 5));
		tag.setTag("testshort", new NBTShort((short) 78));
		tag.setTag("testint", new NBTInt(555));
		tag.setTag("testlong", new NBTLong(125));
		tag.setTag("testfloat", new NBTFloat(26.55F));
		tag.setTag("testdouble", new NBTDouble(125.111));
		tag.setTag("teststring", new NBTString("1154"));
		tag.setTag("testba", new NBTByteArray(new byte[] {1, 6, 73, 67}));
		tag.setTag("testia", new NBTIntArray(new int[] {1, 6, 6}));
		tag.setTag("testla", new NBTLongArray(new long[] {6, 77, 888}));
		NBTCompound compound = new NBTCompound();
		compound.setTag("test", new NBTShort((short) 1));
		tag.setTag("testcompound", compound);
		NBTList<NBTInt> list = new NBTList<>(NBTType.INT);
		list.addTag(new NBTInt(1));
		list.addTag(new NBTInt(10));
		tag.setTag("testlist", list);
	}

	@Test
	public void testSerialize() throws Exception {
		ByteBufOutputStream stream = new ByteBufOutputStream(Unpooled.buffer());
		DefaultNBTSerializer.INSTANCE.serializeTag(stream, tag);
		Assertions.assertArrayEquals(serializedTagData, MiscSerializer.readAllBytes(stream.buffer()));
	}

	@Test
	public void testDeserialize() throws Exception {
		Assertions.assertEquals(tag, DefaultNBTSerializer.INSTANCE.deserializeTag(new ByteBufInputStream(Unpooled.wrappedBuffer(serializedTagData))));
	}

}
