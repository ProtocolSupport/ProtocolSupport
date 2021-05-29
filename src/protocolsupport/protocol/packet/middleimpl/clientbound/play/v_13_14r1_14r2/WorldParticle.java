package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_13_14r1_14r2;

import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleWorldParticle;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.typeremapper.particle.FlatteningParticleIdRegistry;
import protocolsupport.protocol.typeremapper.particle.LegacyParticleData;
import protocolsupport.protocol.typeremapper.particle.LegacyParticleData.LegacyParticleDataTable;
import protocolsupport.protocol.typeremapper.utils.MappingTable.ArrayBasedIntMappingTable;
import protocolsupport.protocol.types.particle.Particle;
import protocolsupport.protocol.types.particle.ParticleDataSerializer;
import protocolsupport.protocol.types.particle.ParticleRegistry;
import protocolsupport.protocol.utils.TypeSerializer;

public class WorldParticle extends MiddleWorldParticle {

	protected final LegacyParticleDataTable remapper = LegacyParticleData.REGISTRY.getTable(version);
	protected final ArrayBasedIntMappingTable flatteningIdTable = FlatteningParticleIdRegistry.INSTANCE.getTable(version);
	protected final TypeSerializer.Entry<Particle> dataSerializer = ParticleDataSerializer.INSTANCE.get(version);

	public WorldParticle(MiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void write() {
		particle = remapper.get(particle.getClass()).apply(particle);
		if (particle != null) {
			ClientBoundPacketData serializer = ClientBoundPacketData.create(PacketType.CLIENTBOUND_PLAY_WORLD_PARTICLES);
			serializer.writeInt(flatteningIdTable.get(ParticleRegistry.getId(particle)));
			serializer.writeBoolean(longdist);
			serializer.writeFloat((float) x);
			serializer.writeFloat((float) y);
			serializer.writeFloat((float) z);
			serializer.writeFloat(particle.getOffsetX());
			serializer.writeFloat(particle.getOffsetY());
			serializer.writeFloat(particle.getOffsetZ());
			serializer.writeFloat(particle.getData());
			serializer.writeInt(particle.getCount());
			dataSerializer.write(serializer, particle);
			codec.writeClientbound(serializer);
		}
	}

}
