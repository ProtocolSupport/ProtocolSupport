package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_15_16;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleWorldParticle;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.typeremapper.particle.FlatteningParticleId;
import protocolsupport.protocol.typeremapper.particle.ParticleRemapper;
import protocolsupport.protocol.typeremapper.particle.ParticleRemapper.ParticleRemappingTable;
import protocolsupport.protocol.typeremapper.utils.MappingTable.ArrayBasedIntMappingTable;
import protocolsupport.protocol.types.particle.Particle;
import protocolsupport.protocol.types.particle.ParticleDataSerializer;
import protocolsupport.protocol.types.particle.ParticleRegistry;
import protocolsupport.protocol.utils.TypeSerializer;

public class WorldParticle extends MiddleWorldParticle {

	protected final ParticleRemappingTable remapper = ParticleRemapper.REGISTRY.getTable(version);
	protected final ArrayBasedIntMappingTable flatteningIdTable = FlatteningParticleId.REGISTRY.getTable(version);
	protected final TypeSerializer.Entry<Particle> dataSerializer = ParticleDataSerializer.INSTANCE.get(version);

	public WorldParticle(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	protected void writeToClient() {
		particle = remapper.getRemap(particle.getClass()).apply(particle);
		if (particle != null) {
			ClientBoundPacketData serializer = ClientBoundPacketData.create(PacketType.CLIENTBOUND_PLAY_WORLD_PARTICLES);
			serializer.writeInt(flatteningIdTable.get(ParticleRegistry.getId(particle)));
			serializer.writeBoolean(longdist);
			serializer.writeDouble(x);
			serializer.writeDouble(y);
			serializer.writeDouble(z);
			serializer.writeFloat(particle.getOffsetX());
			serializer.writeFloat(particle.getOffsetY());
			serializer.writeFloat(particle.getOffsetZ());
			serializer.writeFloat(particle.getData());
			serializer.writeInt(particle.getCount());
			dataSerializer.write(serializer, particle);
			codec.write(serializer);
		}
	}

}
