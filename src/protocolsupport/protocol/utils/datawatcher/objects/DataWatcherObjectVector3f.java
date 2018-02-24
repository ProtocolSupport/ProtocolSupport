package protocolsupport.protocol.utils.datawatcher.objects;

import org.bukkit.util.Vector;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.utils.datawatcher.ReadableDataWatcherObject;

public class DataWatcherObjectVector3f extends ReadableDataWatcherObject<Vector> {

	public DataWatcherObjectVector3f() {
		value = new Vector(0, 0, 0);
	}

	public DataWatcherObjectVector3f(Vector position) {
		value = position;
	}

	@Override
	public void readFromStream(ByteBuf from, ProtocolVersion version, String locale) {
		value.setX(from.readFloat());
		value.setY(from.readFloat());
		value.setZ(from.readFloat());
	}

	@Override
	public void writeToStream(ByteBuf to, ProtocolVersion version, String locale) {
		to.writeFloat((float) value.getX());
		to.writeFloat((float) value.getY());
		to.writeFloat((float) value.getZ());
	}

}
