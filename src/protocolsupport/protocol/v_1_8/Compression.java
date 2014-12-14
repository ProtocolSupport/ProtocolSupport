package protocolsupport.protocol.v_1_8;

import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

import net.minecraft.server.v1_8_R1.MinecraftServer;
import net.minecraft.server.v1_8_R1.PacketDataSerializer;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.handler.codec.DecoderException;

public class Compression {

	private static final ThreadLocal<Deflater> deflater = new ThreadLocal<Deflater>() {
		@Override
		public Deflater initialValue() {
			return new Deflater();
		}
	};

	private static final ThreadLocal<Inflater> inflater = new ThreadLocal<Inflater>() {
		@Override
		public Inflater initialValue() {
			return new Inflater();
		}
	};


	public static ByteBuf compress(ByteBuf packetData) {
		final int length = packetData.readableBytes();
		final PacketDataSerializer serializer = new PacketDataSerializer(Unpooled.buffer(length));
		if (length < MinecraftServer.getServer().aI()) {
			serializer.b(0);
			serializer.writeBytes(packetData);
		} else {
			byte[] buffer = new byte[length];
			serializer.b(length);
			Deflater localDeflater = deflater.get();
			localDeflater.setInput(packetData.array(), 0, length);
			localDeflater.finish();
			while (!localDeflater.finished()) {
				serializer.writeBytes(buffer, 0, localDeflater.deflate(buffer));
			}
			localDeflater.reset();
		}
		return serializer;
	}

	public static ByteBuf decompress(ByteBuf compressedData) throws DataFormatException {
		final PacketDataSerializer serializer = new PacketDataSerializer(compressedData);
		final int uncompressedLength = serializer.e();
		if (uncompressedLength == 0) {
			return compressedData;
		} else {
			if (uncompressedLength < MinecraftServer.getServer().aI()) {
				throw new DecoderException("Badly compressed packet - size of " + uncompressedLength + " is below server threshold of " + MinecraftServer.getServer().aI());
			}
			if (uncompressedLength > 2097152) {
				throw new DecoderException("Badly compressed packet - size of " + uncompressedLength + " is larger than protocol maximum of " + 2097152);
			}
			Inflater localInflater = inflater.get();
			localInflater.setInput(serializer.array());
			final byte[] packetData = new byte[uncompressedLength];
			localInflater.inflate(packetData);
			localInflater.reset();
			return Unpooled.wrappedBuffer(packetData);
		}
	}

}
