package protocolsupport.protocol.types.networkentity.metadata.objects;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.typeremapper.particle.ParticleRemapper;
import protocolsupport.protocol.types.networkentity.metadata.ReadableNetworkEntityMetadataObject;
import protocolsupport.protocol.types.particle.Particle;
import protocolsupport.protocol.types.particle.ParticleRegistry;

public class NetworkEntityMetadataObjectParticle extends ReadableNetworkEntityMetadataObject<Particle> {

	@Override
	public void readFromStream(ByteBuf from) {
		value = ParticleRegistry.fromId(VarNumberSerializer.readVarInt(from));
		value.readData(from);
	}

	@Override
	public void writeToStream(ByteBuf to, ProtocolVersion version, String locale) {
		value = ParticleRemapper.REGISTRY.getTable(version).getRemap(value.getClass()).apply(value);
		VarNumberSerializer.writeVarInt(to, value.getId());
		value.writeData(to);
	}

}
