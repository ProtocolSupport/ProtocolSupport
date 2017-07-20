package protocolsupport.protocol.utils.datawatcher.objects;

import org.bukkit.util.Vector;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.serializer.MiscSerializer;
import protocolsupport.protocol.utils.datawatcher.DataWatcherObject;

public class DataWatcherObjectVector3fLe extends DataWatcherObject<Vector> {

	public DataWatcherObjectVector3fLe() {
	}
	
	public DataWatcherObjectVector3fLe(Vector position) {
		value = position;
	}

	@Override
	public void readFromStream(ByteBuf from, ProtocolVersion version, String locale) {
		value = new Vector(MiscSerializer.readLFloat(from), MiscSerializer.readLFloat(from), MiscSerializer.readLFloat(from));
	}

	@Override
	public void writeToStream(ByteBuf to, ProtocolVersion version, String locale) {
		MiscSerializer.writeLFloat(to, (float) value.getX());
		MiscSerializer.writeLFloat(to, (float) value.getY());
		MiscSerializer.writeLFloat(to, (float) value.getZ());
	}

}