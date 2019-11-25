package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_13_14r1_14r2;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleWorldParticle;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.typeremapper.particle.FlatteningParticleId;
import protocolsupport.protocol.typeremapper.particle.ParticleRemapper;
import protocolsupport.protocol.typeremapper.particle.ParticleRemapper.ParticleRemappingTable;
import protocolsupport.protocol.typeremapper.utils.RemappingTable.ArrayBasedIdRemappingTable;
import protocolsupport.protocol.types.particle.Particle;
import protocolsupport.protocol.types.particle.ParticleDataSerializer;
import protocolsupport.protocol.types.particle.ParticleRegistry;
import protocolsupport.protocol.utils.TypeSerializer;

public class WorldParticle extends MiddleWorldParticle {

	protected final ParticleRemappingTable remapper = ParticleRemapper.REGISTRY.getTable(version);
	protected final ArrayBasedIdRemappingTable flatteningIdTable = FlatteningParticleId.REGISTRY.getTable(version);
	protected final TypeSerializer.Entry<Particle> dataSerializer = ParticleDataSerializer.INSTANCE.get(version);

	public WorldParticle(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	public void writeToClient() {
		particle = remapper.getRemap(particle.getClass()).apply(particle);
		if (particle != null) {
			ClientBoundPacketData serializer = codec.allocClientBoundPacketData(PacketType.CLIENTBOUND_PLAY_WORLD_PARTICLES);
			serializer.writeInt(flatteningIdTable.getRemap(ParticleRegistry.getId(particle)));
			serializer.writeBoolean(longdist);
			serializer.writeFloat(x);
			serializer.writeFloat(y);
			serializer.writeFloat(z);
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
