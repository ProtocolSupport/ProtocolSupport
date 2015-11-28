package protocolsupport.utils;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.Unpooled;

import java.io.IOException;
import java.util.zip.Deflater;
import java.util.zip.DeflaterInputStream;
import java.util.zip.Inflater;
import java.util.zip.InflaterInputStream;

import org.apache.commons.io.IOUtils;

public class CompressionUtils {

	public static ByteBuf uncompress(ByteBuf payload) throws IOException {
		return Unpooled.wrappedBuffer(IOUtils.toByteArray(new InflaterInputStream(new ByteBufInputStream(payload), new Inflater(), 8192)));
	}

	public static ByteBuf compress(ByteBuf input) throws IOException {
		return Unpooled.wrappedBuffer(IOUtils.toByteArray(new DeflaterInputStream(new ByteBufInputStream(input), new Deflater(3), 8192)));
	}

}
