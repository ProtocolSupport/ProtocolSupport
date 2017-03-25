package protocolsupport.protocol.utils.datawatcher.objects;

import org.bukkit.util.Vector;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.utils.datawatcher.DataWatcherObject;

public class DataWatcherObjectVector3f extends DataWatcherObject<Vector> {

	@Override
	public void readFromStream(ByteBuf from, ProtocolVersion version) {
		value = new Vector(from.readFloat(), from.readFloat(), from.readFloat());
	}

	@Override
	public void writeToStream(ByteBuf to, ProtocolVersion version) {
		to.writeFloat((float) value.getX());
		to.writeFloat((float) value.getY());
		to.writeFloat((float) value.getZ());
	}

}
