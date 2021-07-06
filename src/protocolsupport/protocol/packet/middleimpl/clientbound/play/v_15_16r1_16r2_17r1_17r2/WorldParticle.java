package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_15_16r1_16r2_17r1_17r2;

import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleWorldParticle;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.typeremapper.particle.FlatteningNetworkParticleDataSerializer;
import protocolsupport.protocol.typeremapper.particle.FlatteningNetworkParticleIdRegistry;
import protocolsupport.protocol.typeremapper.particle.NetworkParticleLegacyData;
import protocolsupport.protocol.typeremapper.particle.NetworkParticleLegacyData.NetworkParticleLegacyDataTable;
import protocolsupport.protocol.typeremapper.utils.MappingTable.ArrayBasedIntMappingTable;
import protocolsupport.protocol.types.particle.NetworkParticle;
import protocolsupport.protocol.types.particle.NetworkParticleRegistry;
import protocolsupport.protocol.utils.TypeSerializer;

public class WorldParticle extends MiddleWorldParticle {

	protected final NetworkParticleLegacyDataTable remapper = NetworkParticleLegacyData.REGISTRY.getTable(version);
	protected final ArrayBasedIntMappingTable flatteningIdTable = FlatteningNetworkParticleIdRegistry.INSTANCE.getTable(version);
	protected final TypeSerializer.Entry<NetworkParticle> dataSerializer = FlatteningNetworkParticleDataSerializer.INSTANCE.get(version);

	public WorldParticle(MiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void write() {
		particle = remapper.get(particle.getClass()).apply(particle);
		if (particle != null) {
			ClientBoundPacketData serializer = ClientBoundPacketData.create(ClientBoundPacketType.PLAY_WORLD_PARTICLES);
			serializer.writeInt(flatteningIdTable.get(NetworkParticleRegistry.getId(particle)));
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
			codec.writeClientbound(serializer);
		}
	}

}
