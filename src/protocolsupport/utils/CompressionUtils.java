package protocolsupport.utils;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

public class CompressionUtils {

	public static ByteBuf uncompress(ByteBuf payload, int expecteduncompressedsize) throws DataFormatException {
		byte[] data = Utils.toArray(payload);
		//TODO: actually decompress all
        byte[] decompressedPayload = new byte[expecteduncompressedsize];
        Inflater inflater = new Inflater();
        inflater.setInput(data);
        int decompressedSize = inflater.inflate(decompressedPayload);
        inflater.end();
        return Unpooled.wrappedBuffer(decompressedPayload, 0, decompressedSize);
	}

	public static ByteBuf compress(ByteBuf input) {
		byte[] data = Utils.toArray(input);
		//TODO: actually calculate worst case array length
		byte[] compressed = new byte[data.length + 200];
		Deflater deflater = new Deflater();
		deflater.setInput(data);
		deflater.finish();
		int compressedsize = deflater.deflate(compressed);
		deflater.end();
		return Unpooled.wrappedBuffer(compressed, 0, compressedsize);
	}

}
