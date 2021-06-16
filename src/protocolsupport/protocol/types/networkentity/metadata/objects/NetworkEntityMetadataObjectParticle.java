package protocolsupport.protocol.types.networkentity.metadata.objects;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.typeremapper.particle.FlatteningNetworkParticleDataSerializer;
import protocolsupport.protocol.typeremapper.particle.FlatteningNetworkParticleIdRegistry;
import protocolsupport.protocol.types.networkentity.metadata.ReadableNetworkEntityMetadataObject;
import protocolsupport.protocol.types.particle.NetworkParticle;
import protocolsupport.protocol.types.particle.NetworkParticleRegistry;

public class NetworkEntityMetadataObjectParticle extends ReadableNetworkEntityMetadataObject<NetworkParticle> {

	public NetworkEntityMetadataObjectParticle() {
	}

	public NetworkEntityMetadataObjectParticle(NetworkParticle value) {
		this.value = value;
	}

	@Override
	public void readFromStream(ByteBuf from) {
		value = NetworkParticleRegistry.fromId(VarNumberSerializer.readVarInt(from));
		value.readData(from);
	}

	@Override
	public void writeToStream(ByteBuf to, ProtocolVersion version, String locale) {
		VarNumberSerializer.writeVarInt(to, FlatteningNetworkParticleIdRegistry.INSTANCE.getTable(version).get(NetworkParticleRegistry.getId(value)));
		FlatteningNetworkParticleDataSerializer.INSTANCE.get(version).write(to, value);
	}

}
