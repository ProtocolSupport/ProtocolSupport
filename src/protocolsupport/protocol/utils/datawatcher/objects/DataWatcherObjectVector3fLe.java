package protocolsupport.protocol.utils.datawatcher.objects;

import org.bukkit.util.Vector;

import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.DecoderException;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.serializer.MiscSerializer;
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
		value.setX(MiscSerializer.readLFloat(from));
		value.setY(MiscSerializer.readLFloat(from));
		value.setZ(MiscSerializer.readLFloat(from));
	}

	@Override
	public void writeToStream(ByteBuf to, ProtocolVersion version, String locale) {
		MiscSerializer.writeLFloat(to, (float) value.getX());
		MiscSerializer.writeLFloat(to, (float) value.getY());
		MiscSerializer.writeLFloat(to, (float) value.getZ());
	}

}