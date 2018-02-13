package protocolsupport.protocol.utils.datawatcher.objects;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.serializer.VarNumberSerializer;

public class DataWatcherObjectSVarInt extends ReadableDataWatcherObjectNumber<Integer> {

	public DataWatcherObjectSVarInt() {
	}

	public DataWatcherObjectSVarInt(int value) {
		this.value = value;
	}

	@Override
	public void readFromStream(ByteBuf from, ProtocolVersion version, String locale) {
		value = VarNumberSerializer.readSVarInt(from);
	}

	@Override
	public void writeToStream(ByteBuf to, ProtocolVersion version, String locale) {
		VarNumberSerializer.writeSVarInt(to, value);
	}

}
