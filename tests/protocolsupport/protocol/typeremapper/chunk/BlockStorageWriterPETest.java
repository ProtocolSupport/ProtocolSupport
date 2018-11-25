package protocolsupport.protocol.typeremapper.chunk;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BlockStorageWriterPETest {

	@Test
	void testConstructor() {
		int bitsPerWord = 4 * 8;

		BlockStorageWriterPE small = new BlockStorageWriterPE(1, 16);
		assertEquals(1, small.getBlockData().length);

		BlockStorageWriterPE smallish = new BlockStorageWriterPE(2, 20);
		assertEquals(2, smallish.getBlockData().length);

		BlockStorageWriterPE medium = new BlockStorageWriterPE(1, 256);
		assertEquals(8, medium.getBlockData().length);

		BlockStorageWriterPE larger = new BlockStorageWriterPE(bitsPerWord, 1);
		assertEquals(1, larger.getBlockData().length);

		BlockStorageWriterPE large = new BlockStorageWriterPE(bitsPerWord, 256);
		assertEquals(256, large.getBlockData().length);
	}

	@Test
	void setBlockState() {
		BlockStorageWriterPE storageWriter = new BlockStorageWriterPE(2, 20);
		assertEquals(2, storageWriter.getBlockData().length);
		storageWriter.setBlockState(1, 0b11);
		int[] expected = {0b1100, 0};
		assertArrayEquals(expected, storageWriter.getBlockData());
		storageWriter.setBlockState(16, 0b11);
		expected[1] = 0b11;
		assertArrayEquals(expected, storageWriter.getBlockData());
	}
}