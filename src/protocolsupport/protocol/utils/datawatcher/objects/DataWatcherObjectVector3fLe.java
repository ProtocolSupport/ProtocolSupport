package protocolsupport.protocol.utils.datawatcher.objects;

import org.bukkit.util.Vector;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.utils.datawatcher.DataWatcherObject;

public class DataWatcherObjectVector3fLe extends DataWatcherObject<Vector> {

	public DataWatcherObjectVector3fLe() {
		value = new Vector(0, 0, 0);
	}

	public DataWatcherObjectVector3fLe(Vector position) {
		value = position;
	}

	@Override
	public void writeToStream(ByteBuf to, ProtocolVersion version, String locale) {
		to.writeFloatLE((float) value.getX());
		to.writeFloatLE((float) value.getY());
		to.writeFloatLE((float) value.getZ());
	}

}