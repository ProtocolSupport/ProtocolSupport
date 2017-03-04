package protocolsupport.protocol.utils.datawatcher.objects;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.utils.datawatcher.DataWatcherObject;

public class DataWatcherObjectFloat extends DataWatcherObject<Float> {

	@Override
	public int getTypeId(ProtocolVersion version) {
		return version.isAfter(ProtocolVersion.MINECRAFT_1_8) ? 2 : 3;
	}

	@Override
	public void readFromStream(ByteBuf from, ProtocolVersion version) {
		value = from.readFloat();
	}

	@Override
	public void writeToStream(ByteBuf to, ProtocolVersion version) {
		to.writeFloat(value);
	}

}
