package protocolsupport.utils;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

public class CompressionUtils {

	public static ByteBuf uncompress(byte[] payload, int expecteduncompressedsize) throws DataFormatException {
		//TODO: actually decompress all
        byte[] decompressedPayload = new byte[expecteduncompressedsize];
        Inflater inflater = new Inflater();
        inflater.setInput(payload);
        int decompressedSize = inflater.inflate(decompressedPayload);
        inflater.end();
        return Unpooled.wrappedBuffer(decompressedPayload, 0, decompressedSize);
	}

	public static ByteBuf compress(ByteBuf input) {
		byte[] data = input.readBytes(input.readableBytes()).array();
		//TODO: actually calculate worst case array length
		byte[] compressed = new byte[data.length + 200];
		Deflater deflater = new Deflater();
		deflater.setInput(data);
		int compressedsize = deflater.deflate(compressed);
		deflater.end();
		return Unpooled.wrappedBuffer(compressed, 0, compressedsize);
	}

}
