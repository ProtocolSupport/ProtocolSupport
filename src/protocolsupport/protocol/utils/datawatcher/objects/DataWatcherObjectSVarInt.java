package protocolsupport.protocol.utils.datawatcher.objects;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.utils.datawatcher.DataWatcherObject;

public class DataWatcherObjectSVarInt extends DataWatcherObject<Integer> {

	public DataWatcherObjectSVarInt() {
	}

	public DataWatcherObjectSVarInt(int value) {
		this.value = value;
	}

	@Override
	public void writeToStream(ByteBuf to, ProtocolVersion version, String locale) {
		VarNumberSerializer.writeSVarInt(to, value);
	}

}
