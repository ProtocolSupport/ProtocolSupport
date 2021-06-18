package protocolsupport.protocol.types.networkentity.metadata.objects;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.codec.VarNumberCodec;
import protocolsupport.protocol.typeremapper.particle.FlatteningNetworkParticleDataSerializer;
import protocolsupport.protocol.typeremapper.particle.FlatteningNetworkParticleIdRegistry;
import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObject;
import protocolsupport.protocol.types.particle.NetworkParticle;
import protocolsupport.protocol.types.particle.NetworkParticleRegistry;

public class NetworkEntityMetadataObjectParticle extends NetworkEntityMetadataObject<NetworkParticle> {

	public NetworkEntityMetadataObjectParticle(NetworkParticle particle) {
		this.value = particle;
	}

	@Override
	public void writeToStream(ByteBuf to, ProtocolVersion version, String locale) {
		VarNumberCodec.writeVarInt(to, FlatteningNetworkParticleIdRegistry.INSTANCE.getTable(version).get(NetworkParticleRegistry.getId(value)));
		FlatteningNetworkParticleDataSerializer.INSTANCE.get(version).write(to, value);
	}

}
