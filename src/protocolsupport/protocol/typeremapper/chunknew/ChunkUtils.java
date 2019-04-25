package protocolsupport.protocol.typeremapper.chunknew;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.serializer.ArraySerializer;

public class ChunkUtils {

	public static void writeBBLight(ByteBuf to, byte[] light) {
		if (light != null) {
			to.writeBytes(light);
		} else {
			to.writeZero(2048);
		}
	}

	public static void writeBBEmptySection(ByteBuf to) {
		to.writeByte(4);
		ArraySerializer.writeVarIntVarIntArray(to, new int[] {0});
		ArraySerializer.writeVarIntLongArray(to, new long[256]);
	}

}
