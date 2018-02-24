package protocolsupport.protocol.utils.datawatcher.objects;

import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.DecoderException;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.serializer.VarNumberSerializer;

public class DataWatcherObjectSVarLong extends ReadableDataWatcherObjectNumber<Long> {

	public DataWatcherObjectSVarLong() {
	}

	public DataWatcherObjectSVarLong(long value) {
		this.value = value;
	}

	public DataWatcherObjectSVarLong(int value) {
		this.value = (long) value;
	}

	@Override
	public void readFromStream(ByteBuf from, ProtocolVersion version, String locale) throws DecoderException {
		value = VarNumberSerializer.readSVarLong(from);
	}

	@Override
	public void writeToStream(ByteBuf to, ProtocolVersion version, String locale) {
		VarNumberSerializer.writeSVarLong(to, value);
	}

}
