package protocolsupport.protocol.utils.datawatcher.objects;

import org.bukkit.util.Vector;

import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.DecoderException;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.utils.datawatcher.ReadableDataWatcherObject;

public class DataWatcherObjectVector3fLe extends ReadableDataWatcherObject<Vector> {

	public DataWatcherObjectVector3fLe() {
		value = new Vector(0, 0, 0);
	}

	public DataWatcherObjectVector3fLe(Vector position) {
		value = position;
	}

	@Override
	public void readFromStream(ByteBuf from, ProtocolVersion version, String locale) throws DecoderException {
		value.setX(from.readFloatLE());
		value.setY(from.readFloatLE());
		value.setZ(from.readFloatLE());
	}

	@Override
	public void writeToStream(ByteBuf to, ProtocolVersion version, String locale) {
		to.writeFloatLE((float) value.getX());
		to.writeFloatLE((float) value.getY());
		to.writeFloatLE((float) value.getZ());
	}

}