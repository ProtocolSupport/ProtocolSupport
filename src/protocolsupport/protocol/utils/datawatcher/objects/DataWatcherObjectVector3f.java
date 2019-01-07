package protocolsupport.protocol.utils.datawatcher.objects;

import org.bukkit.util.Vector;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.utils.datawatcher.ReadableDataWatcherObject;

public class DataWatcherObjectVector3f extends ReadableDataWatcherObject<Vector> {

	@Override
	public void readFromStream(ByteBuf from) {
		value = new Vector(from.readFloat(), from.readFloat(), from.readFloat());
	}

	@Override
	public void writeToStream(ByteBuf to, ProtocolVersion version, String locale) {
		to.writeFloat((float) value.getX());
		to.writeFloat((float) value.getY());
		to.writeFloat((float) value.getZ());
	}

}
