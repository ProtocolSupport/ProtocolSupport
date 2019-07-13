package protocolsupport.protocol.types.networkentity.metadata.objects;

import org.bukkit.util.Vector;

import io.netty.buffer.ByteBuf;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObject;

public class NetworkEntityMetadataObjectVector3fLe extends NetworkEntityMetadataObject<Vector> {

	public NetworkEntityMetadataObjectVector3fLe() {
		value = new Vector(0, 0, 0);
	}

	public NetworkEntityMetadataObjectVector3fLe(Vector position) {
		value = position;
	}

	@Override
	public void writeToStream(ByteBuf to, ProtocolVersion version, String locale) {
		to.writeFloatLE((float) value.getX());
		to.writeFloatLE((float) value.getY());
		to.writeFloatLE((float) value.getZ());
	}

}