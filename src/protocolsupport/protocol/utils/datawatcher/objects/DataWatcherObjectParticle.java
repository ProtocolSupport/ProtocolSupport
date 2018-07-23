package protocolsupport.protocol.utils.datawatcher.objects;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.typeremapper.particle.ParticleRemapper;
import protocolsupport.protocol.utils.datawatcher.ReadableDataWatcherObject;
import protocolsupport.protocol.utils.types.particle.Particle;

public class DataWatcherObjectParticle extends ReadableDataWatcherObject<Particle> {

	public void remapParticle(ProtocolVersion version, String locale) {
		value = ParticleRemapper.remap(version, value);
		value.remap(version, locale);
	}

	@Override
	public void readFromStream(ByteBuf from, ProtocolVersion version, String locale) {
		value = Particle.fromId(VarNumberSerializer.readVarInt(from));
		value.readData(from);
	}

	@Override
	public void writeToStream(ByteBuf to, ProtocolVersion version, String locale) {
		remapParticle(version, locale);
		VarNumberSerializer.writeVarInt(to, value.getId());
		value.writeData(to);
	}

}
