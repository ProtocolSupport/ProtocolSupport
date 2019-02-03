package protocolsupport.protocol.utils.datawatcher.objects;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.typeremapper.particle.ParticleRemapper;
import protocolsupport.protocol.utils.datawatcher.ReadableDataWatcherObject;
import protocolsupport.protocol.utils.types.particle.Particle;
import protocolsupport.protocol.utils.types.particle.ParticleRegistry;

public class DataWatcherObjectParticle extends ReadableDataWatcherObject<Particle> {

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
